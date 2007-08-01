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
	
	protected List<Object> fChanges = null;
	
	boolean fReadyToUndo = false;
	boolean fReadyToRedo = false;
	
	IAutoUndoRecorder fRecorder;
	
	List<Object> fModelRoots;

	/**
	 * Brand new shiny AutoUndoCommand ...
	 * @param modelRoot
	 */
	
	@SuppressWarnings("nls")
	public AutoUndoCommand (EObject modelRoot) {
		super();
		if (modelRoot == null) {
			throw new IllegalArgumentException("modelRoot cannot be null here.");
		}
		fModelRoots = Collections.singletonList((Object) modelRoot);
	}
	
	/**
	 * Brand new shiny AutoUndo Command.
	 * @param modelRoots
	 */
	public AutoUndoCommand(List<Object> modelRoots) {
		super();
		this.fModelRoots = modelRoots;
	}
	
	/**
	 * 
	 * @param label
	 * @param modelRoot
	 */
	@SuppressWarnings("nls")
	public AutoUndoCommand (String label, EObject modelRoot) {
		super(label);		
		if (modelRoot == null) {
			throw new IllegalArgumentException("modelRoot cannot be null here");
		}
		fModelRoots = Collections.singletonList((Object) modelRoot);
	}

	/**
	 * 
	 * @param label
	 * @param modelRoots
	 */
	public AutoUndoCommand(String label, List<Object> modelRoots) {
		super(label);
		this.fModelRoots = modelRoots;
	}

	// only call this method if you provided a modifiable List for modelRoots.
	@SuppressWarnings("nls")
	protected void addModelRoot(Object modelRoot) {
		if (modelRoot == null) {
			throw new IllegalStateException("modelRoot cannot be null here");
		}
		fModelRoots.add(modelRoot);
	}
	
	// should this helper be somewhere else?
	protected Resource getResource (Object modelRoot) {
		if (modelRoot instanceof EObject) return ((EObject)modelRoot).eResource();
		if (modelRoot instanceof Resource) return (Resource)modelRoot;
		throw new IllegalArgumentException();
	}
	
	/**
	 * Default implementation.  Assume that any resource containing a modelRoot
	 * will be modified by the command.
	 */
	@Override
	public Resource[] getResources() {
		if (fModelRoots.size() < 2) {
			if (fModelRoots.isEmpty()) {
				// This should never happen. ?
				throw new IllegalStateException();
				// return new Resource[0]
			}
			Resource resource = getResource(fModelRoots.get(0));
			if (resource != null) {
				return new Resource[] { resource };
			}
			return EMPTY_RESOURCE_ARRAY;
		}
		Set<Resource> resultSet = new HashSet<Resource>(fModelRoots.size());
		for (Object next : fModelRoots) {
			Resource resource = getResource(next);
			if (resource != null) {
				resultSet.add(resource);
			}
		}
		return resultSet.toArray( EMPTY_RESOURCE_ARRAY );
	}
	
	/**
	 * Default implementation: assume that all modelRoots resources were modified.
	 * 
	 * TODO: comment out this method and check each place where a red X appears, to
	 * see if it should implement the method.
	 * 
	 * TODO: maybe a better way is to just query the recorder for affected resources...!
	 */
	
	@Override
	public final Resource[] getModifiedResources() {
		return getResources(); 
	}
	
	// Don't override this, except as a hack when we're really stuck.  :)
	protected ModelAutoUndoRecorder getRecorder() {
		BPELEditor bpelEditor = ModelHelper.getBPELEditor(fModelRoots.get(0));
		return bpelEditor.getModelAutoUndoRecorder();
	}
	
	protected final void initRecorder() {
		if (fRecorder == null) {
			fRecorder = getRecorder();
		}
		if (fRecorder == null) {
			if (Policy.DEBUG) System.err.println("Warning: couldn't get IAutoUndoRecorder for "+this.getClass().getName()+"!"); //$NON-NLS-1$ //$NON-NLS-2$
			new Exception().printStackTrace(System.err);
		}
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public final void undo() {
		if (fChanges == null || !fReadyToUndo) {
			throw new IllegalStateException();
		}
		fReadyToUndo = false;
		// note: there is deliberately no try-finally here
		fRecorder.undo(fChanges);
		fReadyToRedo = true;
	}
	
	
	/**
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public final void redo() {
		if (fChanges == null || !fReadyToRedo) throw new IllegalStateException();
		fReadyToRedo = false;
		// note: there is deliberately no try-finally here
		fRecorder.redo(fChanges);
		fReadyToUndo = true;
	}

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public final void execute() {
		
		if (fChanges != null) {
			throw new IllegalStateException();
		}
		
		initRecorder();
		if (fRecorder.isRecordingChanges()) {
			if (Policy.DEBUG) System.out.println("executing nested auto "+getClass().getName()); //$NON-NLS-1$
			try {
				fRecorder.addModelRoots(fModelRoots);
				doExecute();
				fReadyToUndo = true;
				// TODO: in the event of an error, roll back whatever was recorded??
				// How does that work for the nested case?  (maybe set a flag in the
				// recorder to indicate that rollback is necessary?)
			} finally {
				fChanges = Collections.emptyList();
			}
			return;
		}
		fRecorder.startChanges(fModelRoots);
		if (Policy.DEBUG) System.out.println("executing auto "+getClass().getName()); //$NON-NLS-1$
		try {
			doExecute();
			fReadyToUndo = true;
			// TODO: in the event of an error, roll back whatever was recorded??
		} catch (RuntimeException e) {
			BPELUIPlugin.log(e);
			throw e;
		} finally {
			fChanges = fRecorder.finishChanges();
		}
	}

	/**
	 * @return true if we can execute this, false otherwise.
	 */
	
	public boolean canDoExecute() { 
		return true; 
	}

	/**
	 * Subclasses should override this method.
	 */
	public void doExecute() { 
		
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public final boolean canUndo() { 
		return fReadyToUndo; 
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public final boolean canExecute() {
		if (fChanges != null) return fReadyToRedo;
		return canDoExecute();
	}
}
