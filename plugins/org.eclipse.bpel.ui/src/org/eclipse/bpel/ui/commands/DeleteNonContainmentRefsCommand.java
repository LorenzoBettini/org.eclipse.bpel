/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.ui.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.bpel.ui.commands.util.AutoUndoCommand;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;


/**
 * This command clears references from model objects outside deletedSet to any objects inside
 * deletedSet.  All objects reachable by eTreeIterator() from an object in the modelRootSet are
 * considered.
 * 
 * NOTE: This command clears non-containment references ONLY!
 * 
 * This command is used in various cases where we delete model objects, to ensure that no dangling
 * references are left behind.
 */
public class DeleteNonContainmentRefsCommand extends AutoUndoCommand {

	private Set deletingSet, modelRootSet;
	
	public DeleteNonContainmentRefsCommand(Set deletingSet, Set modelRootSet) {
		super(new ArrayList());
		this.deletingSet = deletingSet;
		this.modelRootSet = modelRootSet;
		for (Iterator it = modelRootSet.iterator(); it.hasNext(); ) {
			addModelRoot(it.next());
		}
	}

	public void doExecute() {
		if (!canExecute())  throw new IllegalStateException();
		
		// Build the set of "all model objects" and subtract.
		HashSet notDeletingSet = new HashSet();
		
		for (Iterator it = modelRootSet.iterator(); it.hasNext(); ) {
			Object root = it.next();
			for (TreeIterator it2 = ModelHelper.getAllContents(root); it2.hasNext(); ) {
				notDeletingSet.add(it2.next());
			}
		}
		notDeletingSet.removeAll(deletingSet);

		// Now iterate over the not-deleted objects, and remove any references they
		// have to the deleted objects.
		for (Iterator it = notDeletingSet.iterator(); it.hasNext(); ) {
			BPELUtil.deleteNonContainmentRefs((EObject)it.next(), deletingSet);
		}
	}
}
