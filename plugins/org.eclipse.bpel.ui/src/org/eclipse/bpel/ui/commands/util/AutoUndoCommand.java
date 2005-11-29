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
package org.eclipse.bpel.ui.commands.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.bpel.common.ui.editmodel.AbstractEditModelCommand;
import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.Policy;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;


/**
 * A base class for BPEL editor commands.  Since the BPEL editor will have
 * automatic EMF-based Undo/Redo support, editor commands should not implement
 * the undo() or redo() methods (and the framework should not call them!).
 */
public abstract class AutoUndoCommand extends AbstractEditModelCommand {

	protected static Resource[] EMPTY_RESOURCE_ARRAY = new Resource[0];
	
	protected List changes = null;
	boolean readyToUndo = false;
	boolean readyToRedo = false;
	IAutoUndoRecorder recorder;
	List modelRoots;

	public AutoUndoCommand(EObject modelRoot) {
		super();
		if (modelRoot == null) throw new IllegalArgumentException();
		modelRoots = Collections.singletonList(modelRoot);
	}
	
	public AutoUndoCommand(List modelRoots) {
		super();
		this.modelRoots = modelRoots;
	}
	
	public AutoUndoCommand(String label, EObject modelRoot) {
		super(label);
		if (modelRoot == null) throw new IllegalArgumentException();
		modelRoots = Collections.singletonList(modelRoot);
	}

	public AutoUndoCommand(String label, List modelRoots) {
		super(label);
		this.modelRoots = modelRoots;
	}

	// only call this method if you provided a modifiable List for modelRoots.
	protected void addModelRoot(Object modelRoot) {
		if (modelRoot == null) throw new IllegalStateException();
		modelRoots.add(modelRoot);
	}
	
	// should this helper be somewhere else?
	protected Resource getResource(Object modelRoot) {
		if (modelRoot instanceof EObject) return ((EObject)modelRoot).eResource();
		if (modelRoot instanceof Resource) return (Resource)modelRoot;
		throw new IllegalArgumentException();
	}
	
	/**
	 * Default implementation.  Assume that any resource containing a modelRoot
	 * will be modified by the command.
	 */
	public Resource[] getResources() {
		if (modelRoots.size() < 2) {
			if (modelRoots.isEmpty()) {
				// This should never happen. ?
				throw new IllegalStateException();
				// return new Resource[0]
			}
			Resource resource = getResource(modelRoots.get(0));
			if (resource != null) return new Resource[] { resource };
			return EMPTY_RESOURCE_ARRAY;
		}
		Set resultSet = new HashSet(modelRoots.size());
		for (Iterator it = modelRoots.iterator(); it.hasNext(); ) {
			Resource resource = getResource(it.next());
			if (resource != null) resultSet.add(resource);
		}
		return (Resource[])resultSet.toArray(new Resource[resultSet.size()]);
	}
	
	/**
	 * Default implementation: assume that all modelRoots resources were modified.
	 * 
	 * TODO: comment out this method and check each place where a red X appears, to
	 * see if it should implement the method.
	 * 
	 * TODO: maybe a better way is to just query the recorder for affected resources...!
	 */
	public final Resource[] getModifiedResources() { return getResources(); }
	
	// Don't override this, except as a hack when we're really stuck.  :)
	protected ModelAutoUndoRecorder getRecorder() {
		BPELEditor bpelEditor = ModelHelper.getBPELEditor(modelRoots.get(0));
		return bpelEditor.getModelAutoUndoRecorder();
	}
	
	protected final void initRecorder() {
		if (recorder == null) recorder = getRecorder();
		if (recorder == null) {
			if (Policy.DEBUG) System.err.println("Warning: couldn't get IAutoUndoRecorder for "+this.getClass().getName()+"!"); //$NON-NLS-1$ //$NON-NLS-2$
			new Exception().printStackTrace(System.err);
		}
	}
	
	public final void undo() {
		if (changes == null || !readyToUndo) throw new IllegalStateException();
		readyToUndo = false;
		// note: there is deliberately no try-finally here
		recorder.undo(changes);
		readyToRedo = true;
	}
	public final void redo() {
		if (changes == null || !readyToRedo) throw new IllegalStateException();
		readyToRedo = false;
		// note: there is deliberately no try-finally here
		recorder.redo(changes);
		readyToUndo = true;
	}

	public final void execute() {
		if (changes != null) throw new IllegalStateException();
		initRecorder();
		if (recorder.isRecordingChanges()) {
			if (Policy.DEBUG) System.out.println("executing nested auto "+getClass().getName()); //$NON-NLS-1$
			try {
				recorder.addModelRoots(modelRoots);
				doExecute();
				readyToUndo = true;
				// TODO: in the event of an error, roll back whatever was recorded??
				// How does that work for the nested case?  (maybe set a flag in the
				// recorder to indicate that rollback is necessary?)
			} finally {
				changes = Collections.EMPTY_LIST;
			}
			return;
		}
		recorder.startChanges(modelRoots);
		if (Policy.DEBUG) System.out.println("executing auto "+getClass().getName()); //$NON-NLS-1$
		try {
			doExecute();
			readyToUndo = true;
			// TODO: in the event of an error, roll back whatever was recorded??
		} catch (RuntimeException e) {
			BPELUIPlugin.log(e);
			throw e;
		} finally {
			changes = recorder.finishChanges();
		}
	}

	public boolean canDoExecute() { return true; }

	/**
	 * Subclasses should override this method.
	 */
	public void doExecute() { }
	
	public final boolean canUndo() { return readyToUndo; }
	
	public final boolean canExecute() {
		if (changes != null) return readyToRedo;
		return canDoExecute();
	}
}
