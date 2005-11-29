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
package org.eclipse.bpel.common.ui.details.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.common.ui.CommonUIPlugin;
import org.eclipse.bpel.common.ui.ICommonUIConstants;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;

public class StatusLabel2 {

	final private int MAX_MESSAGES = 5;
	final private String STATUS_MESSAGE_ID = "STATUS_MESSAGE"; //$NON-NLS-1$
	final private String EMPTY_STRING = ""; //$NON-NLS-1$
	final private String NEW_LINE = "\n"; //$NON-NLS-1$
	final private String ETCETERA = "..."; //$NON-NLS-1$

	protected CLabel label;
	protected List statusMessageList;

	protected static Image blankImage, infoImage, warnImage, errorImage;

	/**
	 * Inner class: StatusMessage which holds one message and its dedicated severity
	 * 
	 */
	protected class StatusMessage {

		protected String id;       //* e.g name of originator of message   
		protected int severity;
		protected String message;

		/**
		 * constructor
		 * 
		 */
		protected StatusMessage(String messageId, int severity, String message) {
			setSeverity(messageId, severity, message);
		}

		/**
		 * constructor
		 * 
		 */
		protected StatusMessage() {
			setStatus(null);
		}

		/**
		 * Sets the severity and (optional) message.
		 * 
		 * @param sev One of IStatus.OK, IStatus.INFO, IStatus.WARNING, IStatus.ERROR
		 * @param message A text message describing the condition (or null).
		 * 
		 */
		protected void setSeverity(String messageId, int severity, String message) {
			setId(messageId);
			setSeverity(severity);
			setMessage(message);
		}

		/**
		 * Convenience method taking an IStatus as input.
		 * 
		 * @param IStatus
		 */
		protected void setStatus(IStatus status) {
			if (status == null) {
				setSeverity(STATUS_MESSAGE_ID, IStatus.OK, null);
			} else {
				setSeverity(STATUS_MESSAGE_ID, status.getSeverity(), status.getMessage());
			}
		}

		/**
		 * @return int the severity
		 */
		protected int getSeverity() {
			return severity;
		}

		/**
		 * @param int the severity
		 */
		protected void setSeverity(int i) {
			severity = i;
		}

		/**
		 * @return String the message
		 */
		protected String getMessage() {
			return message;
		}

		/**
		 * @param String the message
		 */
		protected void setMessage(String string) {
			message = string;
		}
		/**
		 * @return String id of status message
		 */
		protected String getId() {
			return id;
		}

		/**
		 * @param string id of status message
		 */
		protected void setId(String string) {
			id = string;
		}

	}

	/**
	 * constructor
	 * 
	 * @param CLabel to be wrapped
	 * 
	 */
	public StatusLabel2(CLabel label) {
		this.label = label;

		if (blankImage == null) {
			initSharedImages();
		}

		setStatusMessageList(new ArrayList());
		
		clear();
	}

	/**
	 * Initializes the images
	 * 
	 */
	protected static void initSharedImages() {
		ImageRegistry registry = CommonUIPlugin.getDefault().getImageRegistry();
		blankImage = registry.get(ICommonUIConstants.ICON_SM_BLANK);
		infoImage = registry.get(ICommonUIConstants.ICON_SM_INFO);
		warnImage = registry.get(ICommonUIConstants.ICON_SM_WARN);
		errorImage = registry.get(ICommonUIConstants.ICON_SM_ERROR);
	}

	/**
	 * Returns the CLabel.
	 * 
	 * @return CLabel
	 * 
	 */
	public CLabel getLabel() {
		return label;
	}

	/**
	 * Returns the primary control (e.g. maybe a composite).  In this implementation, the CLabel.
	 * 
	 * @return Control
	 * 
	 */
	public Control getControl() {
		return label;
	}

	/**
	 * Returns the CLabel text
	 * 
	 * @return String
	 * 
	 */
	public String getText() {
		return label.getText();
	}

	/**
	 * Sets the CLabel text
	 * 
	 * @param String
	 * 
	 */
	public void setText(String s) {
		label.setText(s);
	}

	/**
	 * Returns the CLabel LayoutData
	 * 
	 * @return Object LayoutData
	 * 
	 */
	public Object getLayoutData() {
		return label.getLayoutData();
	}

	/**
	 * Sets the CLabel text
	 * 
	 * @return Object LayoutData
	 * 
	 */
	public void setLayoutData(Object layoutData) {
		label.setLayoutData(layoutData);
	}

	/**
	 * clears (removes) all status messages
	 * 
	 */
	public void clear() {
		getStatusMessageList().clear();
		updateLabel() ;
	}

	/**
	 * clears all status message with a id equals to the passed messageId
	 * 
	 * @param String id of status message to be removed
	 * 
	 */
	public void clear(String messageId) {

		if (messageId == null) {
			clear();

		} else {
			List removeList = new ArrayList();

			// get all status messages to be removed
			for (int i = 0; i < getStatusMessageList().size(); i++) {
				StatusMessage statusMessage = (StatusMessage) getStatusMessageList().get(i);

				if (messageId.equals(statusMessage.getId())) {
					removeList.add(statusMessage);
				}
			}

			// remove all just now collected status messages
			for (int i = 0; i < removeList.size(); i++) {
				getStatusMessageList().remove(removeList.get(i));
			}
			updateLabel() ;
		}
	}

	/**
	 * Sets the severity and (optional) message.
	 * 
	 * @param String id of status message
	 * @param int severity. One of IStatus.OK, IStatus.INFO, IStatus.WARNING, IStatus.ERROR
	 * @param String message. A text message describing the condition (or null).
	 * 
	 */
	public void setSeverity(String messageId, int severity, String message) {

		StatusMessage statusMessage = new StatusMessage(messageId, severity, message);

		getStatusMessageList().clear();
		getStatusMessageList().add(statusMessage);

		updateLabel();
	}

	/**
	 * Sets the severity and (optional) message. The status message Id will be set to default
	 * 
	 * @param sev One of IStatus.OK, IStatus.INFO, IStatus.WARNING, IStatus.ERROR
	 * @param message A text message describing the condition (or null).
	 * 
	 */
	public void setSeverity(int severity, String message) {
		setSeverity(STATUS_MESSAGE_ID, severity, message);
	}

	/**
	 * Convenience method taking an IStatus as input.
	 * 
	 * @param IStatus
	 * 
	 */
	public void setStatus(IStatus status) {
		if (status == null) {
			setSeverity(STATUS_MESSAGE_ID, IStatus.OK, null);
		} else {
			setSeverity(STATUS_MESSAGE_ID, status.getSeverity(), status.getMessage());
		}
	}

	/**
	 * Sets the severity and (optional) message.
	 *
	 * @param String id of status message  
	 * @param sev One of IStatus.OK, IStatus.INFO, IStatus.WARNING, IStatus.ERROR
	 * @param message A text message describing the condition (or null).
	 * 
	 */
	public void addSeverity(String messageId, int severity, String message) {

		StatusMessage statusMessage = new StatusMessage(messageId, severity, message);

		getStatusMessageList().add(statusMessage);

		updateLabel();
	}

	/**
	 * Sets the severity and (optional) message. The status message Id will be set to default
	 * 
	 * @param sev One of IStatus.OK, IStatus.INFO, IStatus.WARNING, IStatus.ERROR
	 * @param message A text message describing the condition (or null).
	 * 
	 */
	public void addSeverity(int severity, String message) {
		addSeverity(STATUS_MESSAGE_ID, severity, message);
	}

	/**
	 * Convenience method taking an IStatus as input.
	 * 
	 * @param IStatus
	 */
	public void addStatus(IStatus status) {
		if (status == null) {
			addSeverity(IStatus.OK, null);
		} else {
			addSeverity(status.getSeverity(), status.getMessage());
		}
	}

	protected void updateLabel() {

		label.setToolTipText(getToolTipText());

		switch (getHighestSeverity()) {
			case IStatus.OK :
				label.setImage(blankImage);
				break;
			case IStatus.INFO :
				label.setImage(infoImage);
				break;
			case IStatus.WARNING :
				label.setImage(warnImage);
				break;
			default :
				label.setImage(errorImage);
				break;
		}
	}

	/**
	 * returns the tool tip text. Only a maximum of messages will be appended to the tool tip text. The different
	 * messages are seperated with a new line charater. If there are more messages than the maximum number, than three
	 * dots will be appended to the tool tip text.
	 * 
	 * @return the tool tip text from the status message list
	 * 
	 */
	protected String getToolTipText() {

		String toolTipText = EMPTY_STRING;
		List toolTipTextList = new ArrayList();

		toolTipTextList = appendToolTipTextList(toolTipTextList, IStatus.ERROR, MAX_MESSAGES);
		toolTipTextList = appendToolTipTextList(toolTipTextList, IStatus.WARNING, MAX_MESSAGES);
		toolTipTextList = appendToolTipTextList(toolTipTextList, IStatus.INFO, MAX_MESSAGES);
		toolTipTextList = appendToolTipTextList(toolTipTextList, IStatus.OK, MAX_MESSAGES);

		for (int i = 0; i < toolTipTextList.size(); i++) {
			if (toolTipText != null && toolTipText.length() > 0) {
				toolTipText += NEW_LINE;
			}
			toolTipText += toolTipTextList.get(i);
		}

		if (getStatusMessageList().size() > toolTipTextList.size()) {
			if (toolTipText != null && toolTipText.length() > 0) {
				toolTipText += NEW_LINE;
			}
			toolTipText += ETCETERA;
		}

		return toolTipText;
	}

	/**
	 * reads all messaged according to the passed severity from the status message list and appends them to
	 * the passed toolTipText
	 * 
	 * @param String ToolTipText where message must be appended to
	 * @param int severity of messages to be appended
	 * @param int maximum number of messages to be appended
	 * 
	 * @return String toolTipText
	 */
	protected List appendToolTipTextList(List toolTipTextList, int severity, int maxMessages) {

		List newToolTipTextList = toolTipTextList;

		if (newToolTipTextList.size() < maxMessages) {
			List messageList = getMessageList(severity);

			//
			// get all messages
			//
			for (int i = 0;(i < messageList.size()) && (newToolTipTextList.size() < maxMessages); i++) {
				if (newToolTipTextList != null) {
					newToolTipTextList.add(messageList.get(i));
				}
			}
		}

		return newToolTipTextList;
	}

	/**
	 * returns messages according to the passed severity
	 * 
	 * @param int severity of messages to be returned
	 * 
	 * @return List of messages according to the passed severity
	 * 
	 */
	protected List getMessageList(int severity) {
		List messageList = new ArrayList();

		for (int i = 0; i < getStatusMessageList().size(); i++) {
			StatusMessage statusMessage = (StatusMessage) getStatusMessageList().get(i);

			if (statusMessage.getSeverity() == severity) {
				messageList.add(statusMessage.getMessage());
			}
		}

		return messageList;
	}

	/**
	 * @return the highest severity in the status message list
	 * 
	 */
	protected int getHighestSeverity() {
		int severity = IStatus.OK;

		for (int i = 0; i < getStatusMessageList().size(); i++) {
			StatusMessage statusMessage = (StatusMessage) getStatusMessageList().get(i);
			if (statusMessage.getSeverity() > severity) {
				severity = statusMessage.getSeverity();
			}
		}

		return severity;
	}

	/**
	 * @return List of status messages
	 */
	protected List getStatusMessageList() {
		return statusMessageList;
	}

	/**
	 * @param List of status messages
	 */
	protected void setStatusMessageList(List list) {
		statusMessageList = list;
	}
}
