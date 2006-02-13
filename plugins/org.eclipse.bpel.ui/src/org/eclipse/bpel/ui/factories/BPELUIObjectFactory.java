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
package org.eclipse.bpel.ui.factories;

import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Case;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.OnMessage;
import org.eclipse.bpel.model.Pick;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Switch;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.Policy;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;


/**
 * Factory which knows how to create a BPEL model object (and knows where to find the
 * associated icons, etc).
 * 
 * Each instance is constructed with one specific BPEL model object type and represents
 * the way to get general UI info about (and create instances of) that model object type.
 */
public class BPELUIObjectFactory extends AbstractUIObjectFactory {

	protected static final String OBJ16 = "obj16/"; //$NON-NLS-1$
	protected static final String OBJ20 = "obj20/"; //$NON-NLS-1$
	protected static final String GIF = ".gif"; //$NON-NLS-1$
	protected static final String PNG = ".png"; //$NON-NLS-1$

	// we should not include actions here (invoke, etc...)
	public static EClass[] classArray = {
		//BPELPackage.eINSTANCE.getProcess(),
		BPELPackage.eINSTANCE.getVariable(),
		BPELPackage.eINSTANCE.getCorrelationSet(),
		BPELPackage.eINSTANCE.getPartnerLink(),
		BPELPackage.eINSTANCE.getSequence(),
		BPELPackage.eINSTANCE.getFlow(),
		BPELPackage.eINSTANCE.getPick(),
		BPELPackage.eINSTANCE.getScope(),
		BPELPackage.eINSTANCE.getSwitch(),
		BPELPackage.eINSTANCE.getExit(),
		BPELPackage.eINSTANCE.getThrow(),
		BPELPackage.eINSTANCE.getRethrow(),
		BPELPackage.eINSTANCE.getCompensate(),
		BPELPackage.eINSTANCE.getWait(),
		BPELPackage.eINSTANCE.getWhile(),
		BPELPackage.eINSTANCE.getRepeatUntil(),
		BPELPackage.eINSTANCE.getLink(),
		BPELPackage.eINSTANCE.getCase(),
		BPELPackage.eINSTANCE.getOtherwise(),
		BPELPackage.eINSTANCE.getOnMessage(),
		BPELPackage.eINSTANCE.getOnAlarm(),
		BPELPackage.eINSTANCE.getOtherwise(),
		BPELPackage.eINSTANCE.getFaultHandler(),
		BPELPackage.eINSTANCE.getCatch(),
		BPELPackage.eINSTANCE.getCatchAll(),
		BPELPackage.eINSTANCE.getCompensationHandler(),
		BPELPackage.eINSTANCE.getEventHandler(),
	    BPELPackage.eINSTANCE.getOnEvent(),
	    BPELPackage.eINSTANCE.getValidate()
	};

	protected EClass modelType;
	
	public BPELUIObjectFactory(EClass modelType) {
		super();
		this.modelType = modelType;
		if (modelType.getEPackage() != BPELPackage.eINSTANCE) {
			if (Policy.DEBUG) System.out.println("WARNING: constructing BPELUIObjectFactory("+modelType.getName()+") with non-BPELPackage EClass"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

    public EClass getModelType() {
        return modelType;
    }

    protected static String baseImageName(EClass modelObject) {
		// TODO: this is a hack and shouldn't be necessary
		if (modelObject == BPELPackage.eINSTANCE.getVariable()) {
			return "variable"; //$NON-NLS-1$
		}
		if (modelObject == BPELPackage.eINSTANCE.getPartnerLink()) {
			return "partner"; //$NON-NLS-1$
		}
		return modelObject.getName().toLowerCase();
	}
	
	public ImageDescriptor getSmallImageDescriptor() {
		return getSmallImageDescriptor(getModelType());
	}
	public ImageDescriptor getLargeImageDescriptor() {
		return getLargeImageDescriptor(getModelType());
	}
	
	public static ImageDescriptor getSmallImageDescriptor(EClass modelObject) {		
		return BPELUIPlugin.getPlugin().getImageDescriptor(OBJ16+baseImageName(modelObject)+GIF);
	}
	public static ImageDescriptor getLargeImageDescriptor(EClass modelObject) {
		return BPELUIPlugin.getPlugin().getImageDescriptor(OBJ20+baseImageName(modelObject)+PNG);
	}
	
	public Image getSmallImage() {
		return BPELUIPlugin.getPlugin().getImage(OBJ16+baseImageName(getModelType())+GIF);
	}
	public Image getLargeImage() {
		return BPELUIPlugin.getPlugin().getImage(OBJ20+baseImageName(getModelType())+PNG);
	}

	public String getTypeLabel() {
		// TODO: new story for externalizing this ?  We used to use BPELCreateFactory.ClassNiceName.* keys
		return getModelType().getName();
	}
	
	public EObject createInstance() {
		EObject result = super.createInstance();
		if (result instanceof Scope) {
			// we need to have the variables model object in order to have the variables
			// category appear on the tray
			((Scope)result).setVariables(BPELFactory.eINSTANCE.createVariables());
		}
		if (result instanceof Switch) {
			// create a free Case inside the Switch.
			Case _case = (Case)UIObjectFactoryProvider.getInstance().getFactoryFor(
				BPELPackage.eINSTANCE.getCase()).createInstance();
			((Switch)result).getCases().add(_case);
		}
		if (result instanceof Pick) {
			// create a free OnMessage inside the Pick.
			OnMessage onMessage = (OnMessage)UIObjectFactoryProvider.getInstance().getFactoryFor(
				BPELPackage.eINSTANCE.getOnMessage()).createInstance();
			((Pick)result).getMessages().add(onMessage);
		}
		if (result instanceof Assign) {
			// create a free Copy inside the Assign.
			Copy copy = BPELFactory.eINSTANCE.createCopy();
			((Assign)result).getCopy().add(copy);
		}
		return result;
	}
}
