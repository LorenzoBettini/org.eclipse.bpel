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
package org.eclipse.bpel.ui.adapters.delegates;

import java.util.Collections;
import java.util.List;

import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.uiextensionmodel.ActivityExtension;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.ecore.EReference;


/**
 * An IContainer implementation for containers of a single Activity (such as While and
 * ElseIf and RepeatUntil).  This container will actually allow multiple children, and will manage the
 * necessary implicit sequence without directly exposing it to the code above.
 */
public class ImplicitSequenceContainer extends ReferenceContainer {

	public ImplicitSequenceContainer(EReference feature) {
		super(feature);
	}
	
	protected boolean isMany() { return true; }
	
	public List getChildList(Object object) {
		Object child = getSingleChild(object);
		if (child != null && isImplicitSequence(child)) {
			return ((Sequence)child).getActivities();
		}
		return null;
	}
	
	protected static boolean isImplicitSequence(Object child) {
		if (!(child instanceof Sequence)) return false;
		// TODO: this only works in the editor...
		ActivityExtension extension = (ActivityExtension)ModelHelper.getExtension((Sequence)child);
		if (extension == null) return false;
		return extension.isImplicit();
	}
	
	/* IContainer */

	public List getChildren(Object object) {
		Object child = getSingleChild(object);
		if (child != null && isImplicitSequence(child)) {
			return super.getChildren(object);
		}

		// if we got here, we don't have an implicit sequence inside.
		if (child == null) return Collections.EMPTY_LIST;
		// one child
		return Collections.singletonList(child);
	}
	
	public boolean addChild(Object object, Object newChild, Object insertBefore) {
		Object currentChild = getSingleChild(object);
		if (currentChild != null && isImplicitSequence(currentChild)) {
			return super.addChild(object, newChild, insertBefore);
		}

		// no implicit sequence inside.
		if (currentChild == null) {
			// adding first child.
			// For a single child container, insertBefore can't be valid
			setSingleChild(object, newChild);
			return true;
		}
		
		// already have one child, adding a 2nd one.  poof up implicit sequence.
		BPELEditor bpelEditor = ModelHelper.getBPELEditor(object);
		Sequence implicitSequence = BPELUtil.createImplicitSequence(
			bpelEditor.getProcess(), bpelEditor.getExtensionMap());

		Object originalChild = getSingleChild(object);
		
		// NOTE: its important that the implicit sequence be added to the model
		// *before* we insert the other children in it.  Otherwise Undo/Redo
		// will not be able to correctly handle changes to the parentage of
		// those children.
		setSingleChild(object, implicitSequence);
		if (insertBefore==originalChild) {
			implicitSequence.getActivities().add(newChild);
		}
		implicitSequence.getActivities().add(originalChild);
		if (insertBefore!=originalChild) {
			implicitSequence.getActivities().add(newChild);
		}
		return true;
	}
	
	public boolean removeChild(Object object, Object child) {
		Object currentChild = getSingleChild(object);
		if (currentChild == null)  return false;
		if (!isImplicitSequence(currentChild)) {
			// we have a child, but not an implicit sequence.  just remove it.
			// TODO: should we check that child is actually the correct object??
			setSingleChild(object, null);
			return true;
		}
		
		// we have an implicit sequence.  Remove the child from it.
		List list = getChildList(object); 
		if (list.size() <= 2) {
			// do super action then remove the implicit sequence entirely
			Sequence implicitSequence = (Sequence)getSingleChild(object);
			
			super.removeChild(object, child);
			Object otherChild = list.isEmpty()? null : list.get(0);
			
			implicitSequence.getActivities().clear();
			setSingleChild(object, otherChild);
			return true;
		}
		// else, we will still need the implicit sequence after removal. 
		return super.removeChild(object, child);
	}

	public boolean replaceChild(Object object, Object oldChild, Object newChild) {
		if (getChildList(object) != null) {
			return super.replaceChild(object, oldChild, newChild);
		}
		// handle single child.
		if (getSingleChild(object) != oldChild)  return false;
		
		setSingleChild(object, newChild);
		return true;
	}
}
