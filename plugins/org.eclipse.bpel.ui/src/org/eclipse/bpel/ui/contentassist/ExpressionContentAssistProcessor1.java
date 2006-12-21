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

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.ArrayList;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.expressions.Functions;
import org.eclipse.bpel.ui.expressions.Function;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
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

public class ExpressionContentAssistProcessor1 extends TemplateCompletionProcessor {
	
	private Object theModel = null;
	
	static final String XPATH_FUNCTIONS = "xpath.functions"; //$NON-NLS-1$
	
	/** Template context type */
	TemplateContextType fTemplateContextType;

	/** Build the templates */
	private Template[] fTemplates = {};
	
	
	/**
	 * @param model
	 */
	public void setModelObject(Object model) {
		theModel = model;
		
	}
	
	/** (non-Javadoc)
	 * @see org.eclipse.jface.text.templates.TemplateCompletionProcessor#computeCompletionProposals(org.eclipse.jface.text.ITextViewer, int)
	 */
	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,	int offset) {
		String beginsWith = startOfFunction(viewer, offset);
		
		if (beginsWith == null) {			
			
			if ((beginsWith = startOfVariable(viewer, offset)) == null) {
				ICompletionProposal[] prop = new ICompletionProposal[1];
				prop[0] = new CompletionProposal("", offset, 0,
						0, null, "No proposals",
						null, null);
				return prop;
			}
			else {
				return generateVariableProposals(beginsWith, offset);
			}
		}
		return super.computeCompletionProposals(viewer, offset);
		// return generateFunctionProposals(beginsWith, offset);		
	}

	/** (non-Javadoc)
	 * @see org.eclipse.jface.text.templates.TemplateCompletionProcessor#computeContextInformation(org.eclipse.jface.text.ITextViewer, int)
	 */
	
	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		// do nothing for now
		IContextInformation[] result= new IContextInformation[5];
		for (int i=0; i<5;i++) {
			result[i] = new ContextInformation("foo " + i, "test" + i);
		}
		return result;
	}

	/**
	 * 
	 * @see org.eclipse.jface.text.templates.TemplateCompletionProcessor#getCompletionProposalAutoActivationCharacters()
	 */
	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		// for variables
		return new char[] { '$' };
	}

	/**
	 *  (non-Javadoc)
	 * @see org.eclipse.jface.text.templates.TemplateCompletionProcessor#getContextInformationAutoActivationCharacters()
	 */
	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		// for variables
		return new char[] { '#' };
	}
	
	/**
	 * 
	 * @see org.eclipse.jface.text.templates.TemplateCompletionProcessor#getContextInformationValidator()
	 */
	@Override
	public IContextInformationValidator getContextInformationValidator() {
		// do nothing for now
		return null;
	}

	
	public String getErrorMessage() {
		// do nothing for now
		return "Error";
	}
	

	/*
	 * From model, determine list of functions the user may want to choose from.
	 */
	private ICompletionProposal[] generateFunctionProposals(String context, int offset) {
		
		Functions list = Functions.getInstance();
		TreeMap numList = list.getFunctions();
		Iterator iter = numList.values().iterator();
		
		ArrayList results = new ArrayList();
		CompletionProposal prop = null;
		
		Image img = BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_EXPR_FUNCTION);
		while (iter.hasNext()) {
			Function func = (Function)iter.next();
			String replace = func.getReplacementString(null);
			
			if (func.getName().startsWith(context)) {								
				prop = new CompletionProposal(replace, offset-context.length(), context.length(), 
						replace.indexOf('(')+1, 
						img, func.getDisplayString(), null, func.getHelp()+func.getComments());
				
				results.add(prop);
			}
		}
		ICompletionProposal[] proposals = null;
		if (results.size() < 1) {
			proposals = new ICompletionProposal[1];
			proposals[0] = new CompletionProposal("", offset, 0,
					0, null, "No proposals",
					null, null);
		}
		else {
			proposals = new ICompletionProposal[results.size()];
			for (int i=0; i<results.size(); i++) {
				proposals[i] = (CompletionProposal)results.get(i);
			}
		}
		return proposals;
	}
	
	/*
	 * From model, determine list of variables the user may want to choose from.
	 */
	private ICompletionProposal[] generateVariableProposals(String context, int offset) {
		Variable[] variables = BPELUtil.getVisibleVariables((EObject)theModel);
		ArrayList results = new ArrayList();
		CompletionProposal prop = null;
		String name;
		Image img = BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_VARIABLE_16);
		for (int i=0; i<variables.length; i++) {
			name = variables[i].getName();
			
			if (name.startsWith(context)) {
				prop = new CompletionProposal(name, offset-context.length(), context.length(),
						name.length(), img, name + "   " ,
						null, null);
				results.add(prop);
			}

		}

		ICompletionProposal[] proposals = null;
		if (results.size() < 1) {
			proposals = new ICompletionProposal[1];
			proposals[0] = new CompletionProposal("", offset, 0,
					0, null, "No proposals",
					null, null);			
		}
		else {
			proposals = new ICompletionProposal[results.size()];
			for (int i=0; i<results.size(); i++) {
				proposals[i] = (CompletionProposal)results.get(i);
			}
		}
		return proposals;
	}
	
	private String startOfVariable(ITextViewer viewer, int offset) {
		int startPosition = offset-1;
		char currChar;
		String context = "";
		IDocument document = viewer.getDocument();

		try {
			while (startPosition >= 0) {
				currChar = document.getChar(startPosition);
				
				if (currChar == '$')
					return context;
				
				if (!Character.isLetterOrDigit(currChar))
					return null;
				
				context = currChar + context;
				startPosition--;
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}
	
	private String startOfFunction(ITextViewer viewer, int offset) {
		int startPosition = offset-1;
		char currChar;
		String context = "";
		IDocument document = viewer.getDocument();

		try {
			while (startPosition >= 0) {
				if (Character.isWhitespace(currChar = document.getChar(startPosition)) ||
						isReservedOperatorCharacter(currChar))
					return context;
				
				if (isSpecialCharacter(currChar))
					return null;
				
				context = currChar + context;
				startPosition--;
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		return context;
	}
	
	
	
	
	private boolean isReservedOperatorCharacter(char c) {
		final String RESERVED_CHARS = "+-=<>*!";
		if (RESERVED_CHARS.indexOf(c) > -1)
			return true;
		return false;
	}
	
	private boolean isSpecialCharacter(char c) {
		final String SPECIAL_CHARS = "()[].@,:/*|$";
		if (SPECIAL_CHARS.indexOf(c) > -1)
			return true;
		return false;
	}

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
											func.getReplacementString(null) 
											,true)) ;
				}
				fTemplates = list.toArray(fTemplates);
			}
		}
		return fTemplates;
	}		
}
