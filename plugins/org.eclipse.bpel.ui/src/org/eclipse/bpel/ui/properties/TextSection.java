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
package org.eclipse.bpel.ui.properties;

import org.eclipse.bpel.common.ui.details.IOngoingChange;
import org.eclipse.bpel.common.ui.editmodel.AbstractEditModelCommand;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Policy;
import org.eclipse.bpel.ui.expressions.IExpressionEditor;
import org.eclipse.bpel.ui.util.BatchedMultiObjectAdapter;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.util.Assert;
import org.eclipse.swt.widgets.Composite;


public abstract class TextSection extends BPELPropertySection implements IGetExpressionEditor {

	protected IExpressionEditor editor;
	protected boolean isExecutingStoreCommand = false;
	protected boolean updating = false;
	protected IOngoingChange change;
	
	public boolean shouldUseExtraSpace() {
		return true;
	}
	
	protected void disposeEditor() {
		if (editor != null) {
			editor.dispose();
			editor = null;
		}
	}
	
	protected MultiObjectAdapter[] createAdapters() {
		MultiObjectAdapter adapter = new BatchedMultiObjectAdapter() {
			boolean needRefresh = false;
			public void notify(Notification n) {
				if (!isExecutingStoreCommand) {
					if (isBodyAffected(n)) needRefresh = true;
				}
				refreshAdapters();
			}
			public void finish() {
				if (needRefresh) updateWidgets();
				needRefresh = false;
			}
		};
		return new MultiObjectAdapter[] { adapter };
	}

	protected String getCommandLabel() {
		return IBPELUIConstants.CMD_EDIT_EXPRESSION;
	}
	
	protected void createChangeHelper() {
		change = new IOngoingChange() {
			public String getLabel() {
				return getCommandLabel();
			}
			public Command createApplyCommand() {
				if (editor == null) return null;
				Object contents = editor.getBody();
				Command command = newStoreToModelCommand(contents);
				if (command == null) return null;
				CompoundCommand result = new CompoundCommand() {
				    public void execute() {
				        isExecutingStoreCommand = true;
				        try {
				            super.execute();
				        } finally {
				            isExecutingStoreCommand = false;
				        }
                    }
				};
				// refresh the editor
				result.add(new AbstractEditModelCommand() {
					public void execute() { if (editor != null) editor.markAsClean(); }
					// TODO: is this correct?
					public Resource[] getResources() { return EMPTY_RESOURCE_ARRAY; }
					public Resource[] getModifiedResources() { return EMPTY_RESOURCE_ARRAY; }
				});
				
				result.add(wrapInShowContextCommand(command));
				return result;
			}
			public void restoreOldState() {
				updateWidgets();
			}
		};
	}
	
	protected void createClient(Composite parent) {
		createEditor(parent);
		createChangeHelper();
	}

	protected abstract void createEditor(Composite parent);
	
	protected void notifyEditorChanged() {
		// TODO: why is this method being called before createClient() has finished
		// when a BuiltInExpressionEditor was selected in the model?
		if (change != null) {
			getCommandFramework().notifyChangeInProgress(change);
		}
	}
	
	protected abstract void updateEditor();

	public void aboutToBeHidden() {
		if (Policy.DEBUG) System.out.println("exprdetails.aboutToBeHidden() - "+this); //$NON-NLS-1$
		super.aboutToBeHidden();
		if (change != null) {
			getCommandFramework().notifyChangeDone(change);
		}
		if (editor != null) editor.aboutToBeHidden();
	}

	public void aboutToBeShown() {
		if (Policy.DEBUG) System.out.println("exprdetails.aboutToBeShown() - "+this); //$NON-NLS-1$
		// TODO: can't remove the following line because we rely on this event
		// in some places.
		updateEditor();
		super.aboutToBeShown();
		if (editor != null) editor.aboutToBeShown();
//		if (Platform.getWS().equals(Platform.WS_GTK)) {
//			((DetailsArea)detailsArea).getDetailsAreaComposite().getScrolledComposite().pack(true);
//			((DetailsArea)detailsArea).getDetailsAreaComposite().getScrolledComposite().pack(true);
//		}
	}

	public void dispose() {
	    disposeEditor();
	}

	protected void updateWidgets() {
		Assert.isNotNull(getInput());

		if (!updating) {
			updating = true;
			try {
				updateEditor();
			} finally {
				updating = false;
			}
		}
	}

	public void refresh() {
		super.refresh();
		updateEditor();
		updateWidgets();
	}

    public Object getUserContext() {
    	return (editor==null)? null : editor.getUserContext();
    }

    public void restoreUserContext(Object userContext) {
    	if (editor != null) editor.restoreUserContext(userContext);
    }
    
	public IExpressionEditor getExpressionEditor() {
		return editor;
	}
	
	protected abstract Command newStoreToModelCommand(Object body);
	protected abstract boolean isBodyAffected(Notification n);

	public void gotoMarker(IMarker marker) {
		// TODO: Look up the use type.
		String useType = null;
		getExpressionEditor().gotoTextMarker(marker, useType, modelObject);
	}
	
	public boolean isExecutingStoreCommand() {
		return isExecutingStoreCommand;
	}
}
