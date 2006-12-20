package org.eclipse.bpel.ui.details.providers;

import java.util.List;
import java.util.Iterator;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.Links;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.Target;
import org.eclipse.bpel.model.Targets;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.EList;


/**
 * Content provider for links.
 * 
 * Provides all the links visible in a given context.
 */

public class LinkContentProvider extends AbstractContentProvider  {

	public void collectElements(Object input, List list)  {
		if (input == null)
			return;
		
		if (input instanceof Flow) {
			Flow flow = (Flow)input;
			Links links = flow.getLinks();
			
			if (links != null) {
				EList elist = links.getChildren();
				if (elist != null) {
					Iterator iter = elist.iterator();
					while (iter.hasNext()) {
						Link lnk = (Link)iter.next();
						list.add(lnk.getName());
					}
				}
			}
		}
		else if (input instanceof Activity) {
			collectElements(((Activity)input).eContainer(), list);
		}
	}
	/*
	private void test(Object input) {
		if (input == null) {
			return;
		}
		
		if (input instanceof Flow) {
			Flow flow = (Flow) input;
			Links links = flow.getLinks();
			
			EList elist = links.getChildren();
			if (elist != null) {
				Iterator iter = elist.iterator();
				while (iter.hasNext()) {
					Link lnk = (Link)iter.next();
					String name = lnk.getName();
					int i = 1;
				}
			}

		}
		
		if (input instanceof Activity) {
			Activity act = (Activity)input;
			if (act.getTargets() != null) {
				EList elist = act.getTargets().getChildren();
				if (elist != null) {
					Iterator iter = elist.iterator();
					while (iter.hasNext()) {
						Target tgt = (Target)iter.next();
						if (tgt.getLink() != null) {
							String name = tgt.getLink().getName();
							int i = 1;
						}
					}
				}
			}
			
			test(act.eContainer());
		}
	}
	*/
}
