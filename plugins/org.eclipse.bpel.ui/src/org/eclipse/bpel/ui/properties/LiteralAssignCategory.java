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

import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.model.resource.BPELResource;
import org.eclipse.bpel.model.util.BPELUtils;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.details.providers.AbstractContentProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.xsd.XSDFactory;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.util.XSDConstants;


/**
 * An AssignCategory where the user can type in a literal value (note: NOT an expression).
 */
public class LiteralAssignCategory extends AssignCategoryBase {

	Text literalText;
	//CComboViewer typeViewer;
	
	static class XSDContentProvider extends AbstractContentProvider  {
	
		protected static Object[] builtins;
		// TODO: Yet another simple type declaration
		protected static final String[] xsdBuiltinTypes = {
			"string", /*"integer",*/ "boolean", "float", "double", "base64Binary", "hexBinary", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			"long", "int", "short", "decimal", "byte", "QName", "date", "time", "unsignedInt", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
			"unsignedShort", "unsignedByte", "anySimpleType", "anyURI" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		};
		
		static {
			builtins = new Object[xsdBuiltinTypes.length];
			for (int i = 0; i<xsdBuiltinTypes.length; i++) {
				XSDSimpleTypeDefinition st = XSDFactory.eINSTANCE.createXSDSimpleTypeDefinition();
				st.setName(xsdBuiltinTypes[i]);
				st.setTargetNamespace(XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001);
				builtins[i] = st;
			}
		}
		
		public Object[] getElements(Object input)  {
			return builtins;
		}

	}
	
	protected LiteralAssignCategory(boolean isFrom, BPELPropertySection ownerSection) {
		super(isFrom, ownerSection);
	}

	public String getName() { return Messages.LiteralAssignCategory_Fixed_Value_1; } 

	protected String getLabelFlatFormatString() {
		return IBPELUIConstants.FORMAT_CMD_EDIT;
	}

	protected void createClient2(Composite parent) {
		FlatFormData data;

		//Composite typeComposite = createFlatFormComposite(parent);

//		Label label = wf.createLabel(typeComposite, Messages.getString("LiteralAssignCategory.25")); //$NON-NLS-1$

//		CCombo combo = wf.createCCombo(typeComposite);
//		this.typeViewer = new CComboViewer(combo);
//		typeViewer.setContentProvider(new XSDContentProvider());
//		typeViewer.setLabelProvider(new ModelLabelProvider());
//		typeViewer.setSorter(ModelViewerSorter.getInstance());
//		typeViewer.setInput(new Object());

//		data = new FlatFormData();
//		data.left = new FlatFormAttachment(0, 0);
//		data.right = new FlatFormAttachment(combo, 0);
//		data.top = new FlatFormAttachment(0, 0);
//		label.setLayoutData(data);

//		data = new FlatFormData();
//		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(label, STANDARD_LABEL_WIDTH_SM));
//		data.right = new FlatFormAttachment(100, 0);
//		data.top = new FlatFormAttachment(0, 0);
//		combo.setLayoutData(data);
		
//		getChangeHelper().startListeningTo(combo);
		
		literalText = wf.createText(parent, "", SWT.V_SCROLL | SWT.MULTI); //$NON-NLS-1$
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		//data.top = new FlatFormAttachment(typeComposite, IDetailsAreaConstants.VSPACE);
		data.top = new FlatFormAttachment(0,0);
		data.bottom = new FlatFormAttachment(100, 0);
		literalText.setLayoutData(data);
		
		getChangeHelper().startListeningTo(literalText);
	}

	public boolean isCategoryForModel(To toOrFrom) {
		if (!isFrom || toOrFrom == null)  return false;
		From from = (From)toOrFrom;
		if (from.getLiteral() != null)  return true;
		//Literal literal = (Literal)ModelHelper.getExtensibilityElement(from, Literal.class);
		//if (literal != null) return true;
		return false;
	}
	protected void loadToOrFrom(To toOrFrom) {
		if (!isFrom)  return;
		From from = (From)toOrFrom;

		getChangeHelper().startNonUserChange();
		try {
			String fromString = ""; //$NON-NLS-1$
//			XSDSimpleTypeDefinition def = null;
//			Literal literal = null;
			if (from != null) {
				fromString = from.getLiteral();

//				literal = (Literal)ModelHelper.getExtensibilityElement(from, Literal.class);
//				if (literal != null) {
//					fromString = literal.getValue();
//					def = (XSDSimpleTypeDefinition)literal.getType();
//				}
			}
			if (fromString == null) fromString = ""; //$NON-NLS-1$
			if (!fromString.equals(literalText.getText())) literalText.setText(fromString);
//			if (def == null) {
				// Default to xsd:string
//				def = XSDFactory.eINSTANCE.createXSDSimpleTypeDefinition();
//				def.setName("string"); //$NON-NLS-1$
//				def.setTargetNamespace(XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001);
//			}
			// We can't set this selection into the combo, because the actual instances
			// may differ. Find the right one in the content provider.
//			Object[] elements = ((XSDContentProvider)typeViewer.getContentProvider()).getElements(new Object());
//			for (int i = 0; i < elements.length; i++) {
//				XSDSimpleTypeDefinition candidate = (XSDSimpleTypeDefinition)elements[i];
//				if (candidate.getName().equals(def.getName()) && candidate.getTargetNamespace().equals(def.getTargetNamespace())) {
//					typeViewer.setSelection(new StructuredSelection(candidate));
//					break;
//				}
//			}
		} finally {
			getChangeHelper().finishNonUserChange();
		}
	}
	protected void storeToOrFrom(To toOrFrom) {
		if (!isFrom)  return;
		From from = (From)toOrFrom;
		
		String expr = literalText.getText();
		if ("".equals(expr)) expr = null;  //$NON-NLS-1$

		from.setLiteral(expr);

		if (expr == null) {
			from.setUnsafeLiteral(Boolean.FALSE);
		} else {
			// test if the unsafe literal can be converted into an element and serialized safely 
			if (BPELUtils.convertStringToNode(expr, (BPELResource)getBPELEditor().getResource()) != null) {
				from.setUnsafeLiteral(Boolean.TRUE);
			} else {
				from.setUnsafeLiteral(Boolean.FALSE);
				MessageDialog.openWarning(literalText.getShell(),
					Messages.LiteralAssignCategory_Warning_1, 
					Messages.LiteralAssignCategory_Literal_not_XML_2); 
			}
		}
		
//		StructuredSelection selection = (StructuredSelection)typeViewer.getSelection();
//		XSDSimpleTypeDefinition def = (XSDSimpleTypeDefinition)selection.getFirstElement();
//		literal.setType(def);
		//from.getEExtensibilityElements().add(literal);
	}

	public Object getUserContext() {
		return null;
	}
	public void restoreUserContext(Object userContext) {
		literalText.setFocus();
	}

}
