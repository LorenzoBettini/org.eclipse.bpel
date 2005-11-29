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

import org.eclipse.bpel.common.ui.command.ICommandFramework;
import org.eclipse.bpel.common.ui.details.IOngoingChange;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;


/**
 * Tracks changes on controls and execute commands to report them.
 */
public class ChangeTracker {
	
	protected IOngoingChange change;
	protected ICommandFramework framework;
	protected boolean tracking;

	public ChangeTracker(Control target, IOngoingChange change, ICommandFramework framework) {
		this(new Control[]{target}, change, framework);
	}

	public ChangeTracker(Control[] targets, IOngoingChange change, ICommandFramework framework) {
		this.change = change;
		this.framework = framework;
		tracking = true;
		for (int i = 0; i < targets.length; i++) {
			addListeners(targets[i]);
		}
	}

	public void startTracking() {
		tracking = true;
	}
	
	public void stopTracking() {
		tracking = false;
	}
	
	protected void addListeners(Control target) {
		if (target instanceof Text) {
			trackModify(target);
			trackFocus(target);
			trackEnterKey(target);
		} else if (target instanceof Button) {
			trackSelection(target);
		} else if (target instanceof Composite) {
			trackFocus(target);
			trackSelection(target);
		} else if (target instanceof CCombo) {
			trackFocus(target);
			trackSelection(target);
			trackEnterKey(target);
		}
	}

	protected void trackSelection(Control target) {
		target.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (tracking) {
					framework.notifyChangeInProgress(change);
					framework.notifyChangeDone(change);
				}
			}
		});
	}
	
	protected void trackFocus(Control target) {
		target.addListener(SWT.FocusOut, new Listener() {
			public void handleEvent(Event event) {
				if (tracking) {
					framework.notifyChangeDone(change);
				}
			}
		});
	}
	
	protected void trackEnterKey(Control target) {
		target.addListener(SWT.KeyDown, new Listener() {
			public void handleEvent(Event event) {
				if (tracking && event.keyCode == SWT.CR) {
					framework.notifyChangeDone(change);
				}
			}
		});
	}

	protected void trackModify(Control target) {
		target.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				if (tracking) {
					framework.notifyChangeInProgress(change);
				}
			}
		});
	}
}
