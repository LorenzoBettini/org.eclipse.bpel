/*******************************************************************************
 * Copyright (c) 2008, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.extensionssample.ui.adapters;

import org.eclipse.bpel.extensionsample.model.ModelPackage;
import org.eclipse.bpel.ui.adapters.ContainerActivityAdapter;
import org.eclipse.bpel.ui.adapters.IContainer;
import org.eclipse.bpel.ui.adapters.delegates.ActivityContainer;
import org.eclipse.bpel.ui.editparts.SequenceEditPart;
import org.eclipse.gef.EditPart;

public class SampleStructuredActivityAdapter extends ContainerActivityAdapter {

	@Override
	protected IContainer createContainerDelegate() {
		return new ActivityContainer(ModelPackage.eINSTANCE.getSampleStructuredActivity_Activity());
	}
	
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart result = new SequenceEditPart();
		result.setModel(model);
		return result;
	}

}
