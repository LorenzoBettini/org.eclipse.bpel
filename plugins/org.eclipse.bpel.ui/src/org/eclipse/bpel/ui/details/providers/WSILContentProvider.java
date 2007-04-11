package org.eclipse.bpel.ui.details.providers;

import java.util.ArrayList;

import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.wsil.model.inspection.WSILDocument;
import org.eclipse.bpel.wsil.model.inspection.Inspection;
import org.eclipse.bpel.wsil.model.inspection.Service;
import org.eclipse.bpel.wsil.model.inspection.Link;
import org.eclipse.bpel.wsil.model.inspection.Name;
import org.eclipse.bpel.wsil.model.inspection.TypeOfAbstract;
import org.eclipse.bpel.wsil.model.inspection.util.InspectionResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;


/**
 * WSIIL Content Provider
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @author Edward Gee
 * @date Apr 5, 2007
 *
 */
public class WSILContentProvider implements ITreeContentProvider, ILabelProvider  {

	// constants
	public static final String LABEL_WSILDOCUMENT = "WSIL Document"; //$NON-NLS-1$
	public static final String LABEL_INSPECTION = "Inspection"; //$NON-NLS-1$

	
	String fBasePath;
	
	/**
	 * WSIL content provider instance
	 * @param path
	 */
	
	public WSILContentProvider(String path) {
		fBasePath = path;
	}
	
	/** (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	
	@SuppressWarnings("unchecked")
	public Object[] getChildren (Object arg0) {
		
		ArrayList<Object> items = new ArrayList<Object>();		
		
		// TODO Auto-generated method stub
		if (arg0 instanceof WSILDocument) {
			items.add(((WSILDocument)arg0).getInspection());
		}
		
		if (arg0 instanceof Inspection) {
			Inspection insp = (Inspection) arg0;
			items.addAll( insp.getServices() );
			items.addAll( insp.getLinks() );			
		}
		
		if (arg0 instanceof Link) {
			Link link = (Link)arg0;
			ResourceSet resourceSet = new ResourceSetImpl();	
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("wsil", new InspectionResourceFactoryImpl()); //$NON-NLS-1$
			Resource resource = resourceSet.createResource(URI.createURI("*.wsil")); //$NON-NLS-1$

			URI linkURI = URI.createURI(link.getLocation());
			if (linkURI.isRelative()) {
				// construct absolute path
				String path = fBasePath + link.getLocation();
				linkURI = URI.createURI(path);
			}
			
			resource.setURI(linkURI);
			try {
				resource.load(resourceSet.getLoadOptions());
			}
			catch (Exception ex) {
				System.out.println(ex.toString());
			}
			
			WSILDocument doc = (WSILDocument)resource.getContents().get(0);
			
			items.addAll( doc.getInspection().getServices() );				
		}
		
		return items.toArray();
	}

	/** (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	
	public Object getParent(Object arg0) {
		
		if (arg0 instanceof EObject) {
			EObject eObj = (EObject) arg0;
			return eObj.eContainer();
		}
		return null;		
	}

	
	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	
	public boolean hasChildren(Object arg0) {
		// TODO Auto-generated method stub
		if (arg0 instanceof WSILDocument) {
			return true;
		}
		
		if (arg0 instanceof Inspection) {
			return true;
		}

		if (arg0 instanceof Service) {
			return false;
		}
		
		if (arg0 instanceof Link) {
			return true;
		}
		
		return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object arg0) {
		return getChildren(arg0);
	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}

	/** (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	
	public Image getImage(Object element) {			
		if (element instanceof Inspection) {
			return BPELUIPlugin.getDefault().getImage(IBPELUIConstants.ICON_WSIL );			
		}
		
		if (element instanceof Service) {
			return BPELUIPlugin.getDefault().getImage(IBPELUIConstants.ICON_WSIL_SERVICE );			
		}
		
		if (element instanceof Link) {
			return BPELUIPlugin.getDefault().getImage(IBPELUIConstants.ICON_WSIL_LINK );			
		}
		
		return null;
	}

	
	/** (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {

		if (element instanceof WSILDocument) {
			return LABEL_WSILDOCUMENT;
		}
		
		if (element instanceof Inspection) {
			return LABEL_INSPECTION;
		}

		if (element instanceof Service) {
			String label = ""; //$NON-NLS-1$
			Service serv = (Service)element;
			Name name = null;
			TypeOfAbstract abs = null;
			if (serv.getName().size() > 0) {
				name = (Name)serv.getName().get(0);
				label += name.getValue();
				label += " - "; //$NON-NLS-1$
			}
			
			if  (serv.getAbstract().size() > 0) {
				abs = (TypeOfAbstract)serv.getAbstract().get(0);
				label += abs.getValue();
			}
			
			return (label);
		}
		
		if (element instanceof Link) {
			return (((Link)element).getLocation());
		}
		return element.toString();
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

}
