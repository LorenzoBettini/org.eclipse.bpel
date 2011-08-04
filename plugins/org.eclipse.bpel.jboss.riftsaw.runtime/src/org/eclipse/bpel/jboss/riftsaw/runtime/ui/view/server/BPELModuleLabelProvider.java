package org.eclipse.bpel.jboss.riftsaw.runtime.ui.view.server;

import org.eclipse.bpel.jboss.riftsaw.runtime.IRuntimesUIConstants;
import org.eclipse.bpel.jboss.riftsaw.runtime.RuntimesPlugin;
import org.eclipse.bpel.jboss.riftsaw.runtime.ui.view.server.BPELModuleContentProvider.BPELVersionDeployment;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class BPELModuleLabelProvider extends LabelProvider {
	public Image getImage(Object element) {
		
		return RuntimesPlugin.getPlugin().getImage(IRuntimesUIConstants.ICON_BPEL_FACET);
	}

	public String getText(Object element) {
		if( element instanceof BPELVersionDeployment ) {
			return new Path(((BPELVersionDeployment)element).getPath()).lastSegment();
		}
		return null;
	}
}
