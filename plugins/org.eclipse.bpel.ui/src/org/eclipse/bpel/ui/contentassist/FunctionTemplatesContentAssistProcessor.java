/*******************************************************************************
 * Copyright (c) 2006 Oracle Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.ui.contentassist;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.expressions.Function;
import org.eclipse.bpel.ui.expressions.Functions;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Oct 27, 2006
 *
 */

public class FunctionTemplatesContentAssistProcessor extends TemplateCompletionProcessor {
		
	static final String XPATH_FUNCTIONS = "xpath.functions"; //$NON-NLS-1$
	
	/** Template context type */
	TemplateContextType fTemplateContextType;

	/** Build the templates */
	private Template[] fTemplates = {};
		
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.templates.TemplateCompletionProcessor#getContextType(org.eclipse.jface.text.ITextViewer, org.eclipse.jface.text.IRegion)
	 */
	@Override
	protected TemplateContextType getContextType(ITextViewer viewer, IRegion region) {
		if (fTemplateContextType == null) {
			fTemplateContextType = new TemplateContextType (XPATH_FUNCTIONS,"XPath functions");  //$NON-NLS-1$
		}
		return fTemplateContextType;		
	}

	/**
	 * 
	 * @see org.eclipse.jface.text.templates.TemplateCompletionProcessor#getImage(org.eclipse.jface.text.templates.Template)
	 */
	@Override
	protected Image getImage(Template template) {
		return BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_EXPR_FUNCTION);		
	}

	/**
	 * Compute the templates that we use as the completion proposals for our
	 * functions.  
	 * 
	 * @see org.eclipse.jface.text.templates.TemplateCompletionProcessor#getTemplates(java.lang.String)
	 */
	@Override
	protected Template[] getTemplates (String contextTypeId) {
		
		if ( XPATH_FUNCTIONS.equals ( contextTypeId )) {
			
			if (fTemplates.length == 0) {
				
				List<Template> list = new LinkedList<Template>();
				
				Iterator<Function> iter = Functions.getInstance().getFunctions().values().iterator();				
				while (iter.hasNext()) {
					Function func = iter.next();				
					list.add(new Template (func.getName(),
											func.getHelp(),
											contextTypeId, 
											func.getReplacementString() 
											,true)) ;
				}
				fTemplates = list.toArray(fTemplates);
			}
		}
		return fTemplates;
	}		
}
