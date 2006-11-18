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
package org.eclipse.bpel.ui.editors.xpath;

import org.eclipse.bpel.ui.contentassist.ExpressionSourceViewerConfiguration;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;

/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Oct 25, 2006
 *
 */
public class XPathSourceViewerConfiguration extends ExpressionSourceViewerConfiguration {
	
	private XPathSourceDoubleClickStrategy doubleClickStrategy;	
	private XPathSourceScanner scanner;
	private ColorManager colorManager;

	public XPathSourceViewerConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE 
		};
//			BPELSourcePartitionScanner.BPELSource_COMMENT,
//			BPELSourcePartitionScanner.BPELSource_TAG };
	}
	
	
	public ITextDoubleClickStrategy getDoubleClickStrategy(
		ISourceViewer sourceViewer,
		String contentType) {
		
		if (doubleClickStrategy == null) {
			doubleClickStrategy = new XPathSourceDoubleClickStrategy();
		}
		return doubleClickStrategy;
	}

	
	protected XPathSourceScanner getXPathSourceScanner() {
		
		if (scanner == null) {
			scanner = new XPathSourceScanner(colorManager);			
		}
		return scanner;
	}
	
	
//	protected BPELSourceTagScanner getBPELSourceTagScanner() {
//		if (tagScanner == null) {
//			tagScanner = new BPELSourceTagScanner(colorManager);
//			tagScanner.setDefaultReturnToken(
//				new Token(
//					new TextAttribute(
//						colorManager.getColor(IBPELSourceColorConstants.TAG))));
//		}
//		return tagScanner;
//	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

//		DefaultDamagerRepairer dr =
//			new DefaultDamagerRepairer(getBPELSourceTagScanner());
//		reconciler.setDamager(dr, BPELSourcePartitionScanner.BPELSource_TAG);
//		reconciler.setRepairer(dr, BPELSourcePartitionScanner.BPELSource_TAG);
//
		
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getXPathSourceScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

//		NonRuleBasedDamagerRepairer ndr =
//			new NonRuleBasedDamagerRepairer(
//				new TextAttribute(
//					colorManager.getColor(IBPELSourceColorConstants.COMMENT)
//                    ));
//		
//		reconciler.setDamager(ndr, BPELSourcePartitionScanner.BPELSource_COMMENT);
//		reconciler.setRepairer(ndr, BPELSourcePartitionScanner.BPELSource_COMMENT);

		return reconciler;
	}
}
