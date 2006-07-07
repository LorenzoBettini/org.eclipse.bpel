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
package org.eclipse.bpel.ui.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.namespace.QName;

import org.eclipse.bpel.common.extension.model.ExtensionMap;
import org.eclipse.bpel.common.ui.ImageUtils;
import org.eclipse.bpel.common.ui.details.viewers.ComboViewer;
import org.eclipse.bpel.common.ui.markers.ModelMarkerUtil;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Catch;
import org.eclipse.bpel.model.Compensate;
import org.eclipse.bpel.model.CorrelationSet;
import org.eclipse.bpel.model.CorrelationSets;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.ForEach;
import org.eclipse.bpel.model.Invoke;
import org.eclipse.bpel.model.OnEvent;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.PartnerLinks;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.Variables;
import org.eclipse.bpel.model.messageproperties.MessagepropertiesPackage;
import org.eclipse.bpel.model.messageproperties.PropertyAlias;
import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypePackage;
import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.Policy;
import org.eclipse.bpel.ui.adapters.BPELUIAdapterFactory;
import org.eclipse.bpel.ui.adapters.BPELUIExtensionAdapterFactory;
import org.eclipse.bpel.ui.adapters.BPELUIMessagePropertiesAdapterFactory;
import org.eclipse.bpel.ui.adapters.BPELUIPartnerLinkTypeAdapterFactory;
import org.eclipse.bpel.ui.adapters.BPELUIWSDLAdapterFactory;
import org.eclipse.bpel.ui.adapters.BPELUIXSDAdapterFactory;
import org.eclipse.bpel.ui.adapters.IContainer;
import org.eclipse.bpel.ui.adapters.ILabeledElement;
import org.eclipse.bpel.ui.adapters.IMarkerHolder;
import org.eclipse.bpel.ui.adapters.INamedElement;
import org.eclipse.bpel.ui.bpelactions.AbstractBPELAction;
import org.eclipse.bpel.ui.editparts.BPELEditPart;
import org.eclipse.bpel.ui.editparts.FlowEditPart;
import org.eclipse.bpel.ui.editparts.InvokeEditPart;
import org.eclipse.bpel.ui.editparts.ScopeEditPart;
import org.eclipse.bpel.ui.editparts.StartNodeEditPart;
import org.eclipse.bpel.ui.editparts.borders.GradientBorder;
import org.eclipse.bpel.ui.editparts.util.OverlayCompositeImageDescriptor;
import org.eclipse.bpel.ui.extensions.ActionDescriptor;
import org.eclipse.bpel.ui.extensions.BPELUIRegistry;
import org.eclipse.bpel.ui.uiextensionmodel.ActivityExtension;
import org.eclipse.bpel.ui.uiextensionmodel.UiextensionmodelPackage;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Tool;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wst.common.ui.properties.internal.provisional.TabbedPropertySheetWidgetFactory;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Fault;
import org.eclipse.wst.wsdl.Input;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Output;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.WSDLPackage;
import org.eclipse.wst.wsdl.util.WSDLResourceImpl;
import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDPackage;
import org.eclipse.bpel.ui.adapters.AbstractAdapter;


/**
 * BPELUtil is a place to put *static* helper methods for the BPEL editor.
 * 
 * Note that helpers which have specifically to do with accessing model objects are
 * usually found in the ModelHelper class.
 */
public class BPELUtil {

	private static final Variable[] EMPTY_VARIABLE_ARRAY = new Variable[0];
	private static final PartnerLink[] EMPTY_PARTNERLINK_ARRAY = new PartnerLink[0];
	private static final CorrelationSet[] EMPTY_CORRELATIONSET_ARRAY = new CorrelationSet[0];

	/**
	 * This global variable stores the path of the last WSDL file selected with
	 * a WorkbenchFileSelectionDialog.
	 */
	public static IPath lastWSDLFilePath = null;

	/**
	 * Global variable storing the path of the last BPEL file selected
	 */
	public static IPath lastBPELFilePath = null;
	
	/**
	 * Global variable storing the path of the last XSD file selected
	 */
	public static IPath lastXSDFilePath;
	
	private static Map keyToAdapterFactory;
	
	
	static {
	    keyToAdapterFactory = new HashMap();
	    keyToAdapterFactory.put(BPELPackage.eINSTANCE, BPELUIAdapterFactory.getInstance());
	    keyToAdapterFactory.put(WSDLPackage.eINSTANCE, BPELUIWSDLAdapterFactory.getInstance());
	    keyToAdapterFactory.put(PartnerlinktypePackage.eINSTANCE, BPELUIPartnerLinkTypeAdapterFactory.getInstance());
	    keyToAdapterFactory.put(XSDPackage.eINSTANCE, BPELUIXSDAdapterFactory.getInstance());
	    keyToAdapterFactory.put(MessagepropertiesPackage.eINSTANCE, BPELUIMessagePropertiesAdapterFactory.getInstance());
	    keyToAdapterFactory.put(UiextensionmodelPackage.eINSTANCE, BPELUIExtensionAdapterFactory.getInstance());
	}

	public static void registerAdapterFactory(EClass key, AdapterFactory factory) {
	    keyToAdapterFactory.put(key, factory);
	}
	
	/**
	 * This method tries the registered adapter factories one by one, returning
	 * the first non-null result it gets.  If none of the factories can adapt
	 * the result, it returns null.
	 */
	public static Object adapt(Object target, Object type) {
	    AdapterFactory factory = null;
	    EClass effectiveClass = getEClassFor(target);
	    if (effectiveClass != null) {
		    factory = (AdapterFactory)keyToAdapterFactory.get(effectiveClass);
		    if (factory == null) {
		        factory = (AdapterFactory)keyToAdapterFactory.get(effectiveClass.getEPackage());
		    }
	    }
	    if (factory == null) {
	        factory = BPELUIAdapterFactory.getInstance();
	    }
	    return factory.adapt(target, type);
	}
	
	
	/**
	 * Create an adapter for the given target of the given type. 
	 * In addition, pass a context object to the adapter(s) of the target. 
	 * 
	 * The idea is that some adapters can be stateful and depend not only 
	 * on the objects that they wrap, but also on some other context that is needed
	 * to completely and correctly implement the interface for which the adaptor is
	 * needed.
	 * 
	 * Adapters that are statless, should ignore any notifications sent to them.
	 *  
	 * @param target the target object
	 * @param type the type it wants to adapt to
	 * @param context the context object
	 * 
	 * @return
	 */
	public static Object adapt (Object target, Object type, Object context) {
		
		Object adapter = adapt (target,type);
		if (adapter == null) {
			return adapter;
		}
		
		if ((target instanceof EObject) == false) {
			return adapter;
		}
		
		EObject eObject = (EObject) target;		
		Notification n = new NotificationImpl(AbstractAdapter.CONTEXT_UPDATE_EVENT_TYPE, null, context);		
		eObject.eNotify(n);
		
		return adapter;
	}
		

	/**
	 * Returns the effective EClass for a custom activity (action).
	 */
	public static EClass getEClassFor(Object target) {
	    if (target instanceof Invoke) {
	        ActionDescriptor[] descriptors = BPELUIRegistry.getInstance().getActionDescriptors();
	        for (int i = 0; i < descriptors.length; i++) {
	            AbstractBPELAction action = descriptors[i].getAction();
                if (action.isInstanceOf(target)) {
                    return action.getModelType();
                }
            }
	    }
	    if (!(target instanceof EObject)) {
	        return null;
	    }
	    return ((EObject)target).eClass();
	}
	
	public static boolean isCustomActivity(Object target) {
        if (target instanceof Invoke) {
	        ActionDescriptor[] descriptors = BPELUIRegistry.getInstance().getActionDescriptors();
	        for (int i = 0; i < descriptors.length; i++) {
	            AbstractBPELAction action = descriptors[i].getAction();
	            if (action.getModelType() == BPELPackage.eINSTANCE.getInvoke()) continue;
                if (action.isInstanceOf(target)) {
                    return true;
                }
            }
        }
		return false;
	}
	
	public static boolean isBPELAction(EClass target) {
		ActionDescriptor[] descriptors = BPELUIRegistry.getInstance().getActionDescriptors();
        for (int i = 0; i < descriptors.length; i++) {
            AbstractBPELAction action = descriptors[i].getAction();
            if (action.getModelType() == target) {
                return true;
            }
        }
		return false;
	}

	/**
	 * Creates a new instance of clazz using the EFactory of the EPackage clazz belongs to. 
	 */
	public static EObject createEObject(EClass clazz) {
		return clazz.getEPackage().getEFactoryInstance().create(clazz);
	}
	
	// This is a hack to bundle the result of a cloneSubtree with enough state to undo/redo
	// the extension map changes it caused. 
	public static class CloneResult {
		public EObject targetRoot;
		Map targetMap;
		Map targetMapAdditions = new HashMap();
		public void undo() {
			for (Iterator it = targetMapAdditions.keySet().iterator(); it.hasNext(); ) {
				targetMap.remove(it.next());
			}
		}
		public void redo() {
			for (Iterator it = targetMapAdditions.keySet().iterator(); it.hasNext(); ) {
				Object key = it.next();
				targetMap.put(key, targetMapAdditions.get(key));
			}
		}
	}
	
	// This helper is used by the cloneSubtree() method.
	protected static void cloneSubtreeHelper(EObject source, Map sourceMap, Map targetMap,
		Map copyMap, CloneResult result)
	{
		EObject targetObject = createEObject(source.eClass());
		copyMap.put(source, targetObject);
		if (sourceMap != null && sourceMap.containsKey(source)) {
			EObject sourceExtension = (EObject)sourceMap.get(source);
			EObject targetExtension = createEObject(sourceExtension.eClass());
			copyMap.put(sourceExtension, targetExtension);
			for (TreeIterator it2 = sourceExtension.eAllContents(); it2.hasNext(); ) {
				EObject source2 = (EObject)it2.next();
				EObject target2 = createEObject(source2.eClass());
				copyMap.put(source2, target2);
			}
			targetMap.put(targetObject, targetExtension);
			result.targetMapAdditions.put(targetObject, targetExtension);
		}
	}
	
	/**
	 * Clones an EObject and all EObjects contained directly or indirectly within it.  All
	 * cloned objects possessing an extension in the sourceMap will also have their extensions
	 * cloned into the targetMap.  Containment references and other references to any of the
	 * cloned object(s) will be fixed up to point into the target objects.  Any references to
	 * non-cloned objects will be copied as-is in the cloned objects.
	 * 
	 * NOTE: This method relies on BPELUtil.createEObject() knowing how to create new instances
	 * of the EClasses of all copied objects (i.e. objectFactories must contain the necessary
	 * EFactory instances for everything copied by this method).
	 * 
	 * @param source The root of the source subtree to clone.
	 * @param sourceMap The extension map containing source extensions of cloned objects.
	 * @param targetMap The extension map in which cloned extensions should be recorded.
	 * @return a CloneResult containing the root of the target subtree, which can be used
	 * for undo/redo.
	 */
	public static CloneResult cloneSubtree(EObject source, Map sourceMap, Map targetMap) {
		HashMap copyMap = new HashMap();
		CloneResult result = new CloneResult();
		result.targetMap = targetMap;
		
		// (1) Create target objects for each EObject in the containment subtree of source.
		// If the source object has an extension in sourceMap, create copies of the extension
		// and its containment tree as well.
		// NOTE: we can NOT just recursively call cloneSubtree for the extension, it wouldn't
		// work with fixing up references.  We have to iterate its eAllContents also here.

		cloneSubtreeHelper(source, sourceMap, targetMap, copyMap, result);
		for (TreeIterator it = source.eAllContents(); it.hasNext(); ) {
			EObject sourceObject = (EObject)it.next();
			cloneSubtreeHelper(sourceObject, sourceMap, targetMap, copyMap, result);
		}
		
		// (2) Copy the features from each cloned source object to the corresponding target
		// object.  As we copy, we replace any references to cloned source objects with
		// references to the corresponding target objects--but references to non-cloned
		// objects are copied as-is.

		for (Iterator it = copyMap.keySet().iterator(); it.hasNext(); ) {
			EObject sourceObject = (EObject)it.next();
			EObject targetObject = (EObject)copyMap.get(sourceObject);
			if (sourceObject.eClass() != targetObject.eClass()) throw new IllegalStateException();
			if (Policy.DEBUG) System.out.println("copying a "+sourceObject.eClass().getName()); //$NON-NLS-1$
			for (Iterator it2 = sourceObject.eClass().getEAllStructuralFeatures().iterator(); it2.hasNext(); ) {
				EStructuralFeature feature = (EStructuralFeature)it2.next();
				// special cases first.
				if (!feature.isChangeable()) {
					if (Policy.DEBUG) System.out.println("  *** skipping unchangeable feature "+feature); //$NON-NLS-1$
					continue;
				} 
				if (feature.isUnsettable() && !targetObject.eIsSet(feature)) {
					if (Policy.DEBUG) System.out.println("  unsetting feature "+feature.getName()); //$NON-NLS-1$
					targetObject.eUnset(feature); continue;
				}
				
				Object value = sourceObject.eGet(feature);
				boolean treatAsReference = (feature instanceof EReference);

				if (treatAsReference) {
					if (feature.isMany()) {
						// list of references.
						EList newValues = new BasicEList();
						if (Policy.DEBUG) System.out.println("  copying multi-reference feature "+feature.getName()+":"); //$NON-NLS-1$ //$NON-NLS-2$
						for (Iterator it3 = ((Collection)value).iterator(); it3.hasNext(); ) {
							Object oldValue = it3.next();
							Object newValue = (oldValue==null? null : copyMap.get(oldValue));
							if (newValue == null)  newValue = oldValue;
							if (Policy.DEBUG) System.out.println("+ (oldValue="+oldValue+" newValue="+newValue+")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							newValues.add(newValue); 
						}
						targetObject.eSet(feature, newValues);
					} else {
						// single reference.
						Object newValue = (value==null? null : copyMap.get(value));
						if (newValue == null)  newValue = value;
						if (Policy.DEBUG) System.out.println("  copying reference feature "+feature.getName() //$NON-NLS-1$
							+" (value="+value+" newValue="+newValue+")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						targetObject.eSet(feature, newValue); 
					}
				} else {
					// non-reference attribute.  just copy the value
					if (Policy.DEBUG) System.out.println("  copying attr feature "+feature.getName()+" (value="+value+")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					targetObject.eSet(feature, value);
				}
			}
		}
		
		result.targetRoot = (EObject)copyMap.get(source);
		return result;
	}

	
	public static EObject getIContainerParent(EObject modelObject) {
		if (modelObject instanceof Process) return null;
		EObject parent = modelObject.eContainer();
		if (parent instanceof Sequence) {
			ActivityExtension extension = (ActivityExtension)ModelHelper.getExtension(parent);
			if (extension != null && extension.isImplicit()) {
				// the "real" parent is the container of the implicit sequence.
				parent = parent.eContainer();
			}
		}
		return parent;
	}

	/**
	 * Convenience formatting methods.
	 */
	public static String formatString(String format, String arg1) {
		return MessageFormat.format(format, new Object[] { arg1 });
	}
	public static String formatString(String format, String arg1, String arg2) {
		return MessageFormat.format(format, new Object[] { arg1, arg2 });
	}
	
	/**
	 * strips out invalid characters to conform to QName specs.
	 * If the resulting name is null, returns "bpel" as a valid QName
	 * to guarantee that something valid is returned.
	 */
	public static String generateValidName(String str) {
		StringBuffer result = new StringBuffer(""); //$NON-NLS-1$
		if (str != null) {
			char chrarray [] = str.trim().toCharArray();
			
			for (int i = 0; i < chrarray.length; i++) {
				int destLength = result.length();
				if (((destLength == 0) && (Character.isLetter(chrarray[i]) || chrarray[i] == '_'))
					|| ((destLength > 0) && Character.isJavaIdentifierPart(chrarray[i]))) {
					result.append(chrarray[i]);
				}
			}
		}
		
		if (result.length() == 0)
			result.append(IBPELUIConstants.EXTENSION_BPEL);
		
		return result.toString();
	}

	/**
	 * Helper that traverses the IContainer heirarchy of the given modelObject in depth
	 * first fashion and applies the given visitor to each node.
	 * 
	 * DO NOT USE THIS for anything that must see "all" model objects (including implicit
	 * sequences, for example).  Use TreeIterator modelObject.eAllContents() for that.
	 */
	public static void visitModelDepthFirst(Object modelObject, IModelVisitor visitor) {
		if (visitor.visit(modelObject)) {
			IContainer container = (IContainer)BPELUtil.adapt(modelObject, IContainer.class);
			if (container != null) {
				for (Iterator it = container.getChildren(modelObject).iterator(); it.hasNext(); ) {
					visitModelDepthFirst(it.next(), visitor);
				}
			}
			// TODO: Make this go away
			if (modelObject instanceof Flow) {
				// Hack: also visit the links of a flow!
				Flow flow = (Flow)modelObject;
				for (Iterator it = FlowLinkUtil.getFlowLinks(flow).iterator(); it.hasNext(); ) {
					visitModelDepthFirst(it.next(), visitor);
				}
			}
		}
	}
	
	private static class NameUnusedVisitor implements IModelVisitor {
		private boolean unused = true;
		private String candidateName;
		private Collection ignoreObjects;
		
		NameUnusedVisitor(String candidateName, Collection ignoreObjects) {
			this.candidateName = candidateName;
			if (ignoreObjects == null)  ignoreObjects = Collections.EMPTY_SET;
			this.ignoreObjects = ignoreObjects;
		}
		
		public boolean visit(Object child) {
			if (!ignoreObjects.contains(child)) {
			INamedElement namedElement = (INamedElement)BPELUtil.adapt(child, INamedElement.class);
			if (namedElement != null) {
				String name = namedElement.getName(child);
				if ((name != null) && (name.compareToIgnoreCase(candidateName) == 0))
					unused = false;
			}
			}
			return true;//unused;
		}
		
		public boolean isUnused() { 
			return unused;
		}
	}
	
	/** 
	 * checks if a name is available for use within the given process (i.e. if this name
	 * were added within the modelRoot, would it be unique).  
	 */
	public static boolean isNameUnused(EObject modelRoot, String candidateName, Collection ignoreObjects) {
		NameUnusedVisitor visitor = new NameUnusedVisitor(candidateName, ignoreObjects);
		for (TreeIterator it = modelRoot.eAllContents(); it.hasNext(); ) {
			Object n = it.next();
			visitor.visit(n);
			if (visitor.isUnused() == false) return false;
		}
		return true;
	}

	/**
	 * return a mangled name (based on the given hint) which is unique in the given process.
	 */	
	public static String getUniqueModelName(Process process, String hint, Collection ignoreObjects) {
		return getUniqueModelName2(process, hint, ignoreObjects);
	}

	/**
	 * return a mangled name (based on the given hint) which is unique in the given WSDL definition.
	 */	
	public static String getUniqueModelName(Definition definition, String hint, Collection ignoreObjects) {
		return getUniqueModelName2(definition, hint, ignoreObjects);
	}
	
	protected static String getUniqueModelName2(EObject modelRoot, String hint, Collection ignoreObjects) {
		// TODO: if we remove this upperCaseFirstLetter(), we should check against isJavaKeyword().
		hint = upperCaseFirstLetter(hint);
		// first try it exactly as hinted.
		String result = BPELUtil.generateValidName((hint==null)?"":hint.trim()); //$NON-NLS-1$
		if (isNameUnused(modelRoot, result, ignoreObjects))  return result;
		
		// go back to the first non-digit
		int digitPos = result.length()-1;
		while (digitPos >= 0 && Character.isDigit(result.charAt(digitPos)))  digitPos--;
		digitPos++; // move back to the digit
		String nameWithoutNum = result.substring(0, digitPos);
		
		// try increasing numbers until one is accepted.
		for (int num = 1; ; num++)  {
			result = nameWithoutNum+String.valueOf(num);
			if (isNameUnused(modelRoot, result, ignoreObjects))  return result;
		}
	}

	public static String generateUniqueModelName(Process process, String hint, EObject model) {
		if (hint == null || "".equals(hint)) { //$NON-NLS-1$
			ILabeledElement element = (ILabeledElement)BPELUtil.adapt(model, ILabeledElement.class);
			hint = (element != null) ? element.getTypeLabel(model) : ""; //$NON-NLS-1$
		}
		return BPELUtil.getUniqueModelName(process, hint, Collections.singletonList(model));
	}
	
	public static String getFilenameFromUri(String uri) {
		if (uri == null)  return Messages.BPELUtil__unknown_URI__54; 
		// Hack. Why aren't we just using URI objects?
		int idx = Math.max(uri.lastIndexOf("/"), uri.lastIndexOf("\\")); //$NON-NLS-1$ //$NON-NLS-2$
		return (idx >= 0)? uri.substring(idx+1) : uri;
	}

	/**
	 * Converts the first letter of the target String to upper case.
	 */
	public static String upperCaseFirstLetter(String target) {
		if (target.length() < 1) {
			return target;
		}
		StringBuffer buf = new StringBuffer(target.length());
		buf.append(target.substring(0, 1).toUpperCase());
		buf.append(target.substring(1, target.length()));
		return buf.toString();
	}

	/**
	 * Converts the first letter of the target String to lower case.
	 */
	public static String lowerCaseFirstLetter(String target) {
		if (target.length() < 1) {
			return target;
		}
		StringBuffer buf = new StringBuffer(target.length());
		buf.append(target.substring(0, 1).toLowerCase());
		buf.append(target.substring(1, target.length()));
		return buf.toString();
	}

	/**
	 * Returns all of the PropertyAlias objects from WSDL files in the same ResourceSet as
	 * the resource containing messageType, which are aliases for messageType.
	 */
	public static List getPropertyAliasesForMessageType(Message messageType) {
		List aliases = new ArrayList();
		Resource resource = messageType.eResource();
		if (resource == null) {
			return aliases;
		}
		ResourceSet resourceSet = resource.getResourceSet();
		for (Iterator it = resourceSet.getResources().iterator(); it.hasNext(); ) {
			resource = (Resource)it.next();
			// TODO: this is a hack.  Why is there no WSDLResource interface??
			if (resource instanceof WSDLResourceImpl) {
				for (TreeIterator treeIt = resource.getAllContents(); treeIt.hasNext(); ) {
					Object object = treeIt.next();
					if (object instanceof PropertyAlias) {
						PropertyAlias alias = (PropertyAlias)object;
						if (messageType.equals(alias.getMessageType()))  aliases.add(alias);
					}
				}
			}
		}
		return aliases;
	}

	// Creates an implicit sequence with a name that is unique in the editor's process.
	// Note that an ActivityExtension is created and inserted in the extension map,
	// but the implicit sequence itself should be inserted in the model by the caller.
	public static Sequence createImplicitSequence(Process process, ExtensionMap extensionMap) {
		Sequence impSeq = BPELFactory.eINSTANCE.createSequence();
		ModelHelper.createExtensionIfNecessary(extensionMap, impSeq);
		Collection ignoreObjects = Collections.singletonList(impSeq);
		if (ModelHelper.isSpecCompliant(process)) {
			impSeq.setName(getUniqueModelName(process, Messages.BPELUtil_Sequence_1, ignoreObjects)); 
		} else {
			impSeq.setName(getUniqueModelName(process, Messages.BPELUtil_HiddenSequence_2, ignoreObjects)); 
			((ActivityExtension)ModelHelper.getExtension(impSeq)).setImplicit(true);
		}
		// TODO: also give sequence a unique ID marked as implicit!
		return impSeq;
	}

	public static TreeIterator nodeAndAllContents(final EObject node) {
		final TreeIterator allContents = node.eAllContents();
		return new TreeIterator() {
			boolean didNode = false;
			public void prune() {
				// TODO: This won't work when calling on the first item.
				if (!didNode) throw new IllegalStateException();
				allContents.prune();
			}

			public boolean hasNext() {
				if (didNode) return allContents.hasNext();
				return node != null;
			}

			public Object next() {
				if (didNode) return allContents.next();
				didNode = true;	return node;
			}

			public void remove() {
				// This won't work when calling on the first item.
				if (!didNode) throw new IllegalStateException();
				allContents.remove();
			}
		};
	}
	
	private static class RefreshActionVisitor implements IModelVisitor {
		private GraphicalViewer viewer;
		public RefreshActionVisitor(GraphicalViewer viewer) {
			this.viewer = viewer;			
		}

		public boolean visit(Object child) {
			EditPart ep = (EditPart) viewer.getEditPartRegistry().get(child);
			if (ep != null && ep instanceof BPELEditPart) {
				IFigure fig = ((BPELEditPart)ep).getContentPane();
				if (fig != null) {
					((BPELEditPart)ep).regenerateVisuals();
					ep.refresh();
				}
			}
			return true;//unused;
		}
	}
	
	/** 
	 * refreshes all the editparts of the process. Useful for changing layouts etc
	 */
	public static void regenerateVisuals(Process process, GraphicalViewer viewer) {
		RefreshActionVisitor visitor = new RefreshActionVisitor(viewer);
		visitModelDepthFirst(process, visitor);
		return;
	}
	
	
	/**
	 * The policy for whether a BPELEditPart's edges should be hilighted or not.  This one defers
	 * to the active tool if it is an IHilightControllingTool and says no otherwise.
	 */
	public static boolean shouldHilightEdges(EditDomain domain, EObject modelObject) {
		Tool tool = domain.getActiveTool();
		if (tool instanceof IHilightControllingTool) {
			return ((IHilightControllingTool)tool).hilightModelTarget(modelObject);
		}
		return false;
	}
	
	
	/**
	 * Used to determine the type of pattern to paint a container in the Process.
	 * Because the nesting of containers is confusing, we want to draw nice gradients
	 * to help the user.
	 * 1 and 3 return values mean solid fill.
	 * 0 and 2 mean gradient fills.
	 */
	public static int getRepaintFillType(IFigure fig) {
		int depth = 0;		
		IFigure parent = fig.getParent();
		while (parent != null) {
			if (parent != null && parent.getBorder() != null &&  parent.getBorder() instanceof GradientBorder) {
				depth++;
			}
			parent = parent.getParent();
		}
		return depth % 4;
	}

	public static void sortFlowList(List listOfFlowEditParts) {
		List result = listOfFlowEditParts;
		int resultSize = result.size();

		for (int i = 0; i<resultSize; i++) {
			for (int j = i+1; j<resultSize; j++) {
				Flow flow1 = (Flow)((FlowEditPart)result.get(i)).getModel();	
				Flow flow2 = (Flow)((FlowEditPart)result.get(j)).getModel();
				Flow[] parents = FlowLinkUtil.getParentFlows(flow2);
				for (int k = 0; k<parents.length; k++) {
					if (parents[k] == flow1) {
						// flow2 must be layed out before flow1 so its size will be known!
						Object temp = result.get(i);
						result.set(i, result.get(j));
						result.set(j, temp);
					}
				}
			}
		}
	}

	/**
	 * Refresh the given ComboViewer, and also make sure selectedObject is selected in it.
	 */
	public static void refreshCombo(ComboViewer viewer, Object selectedObject) {
		viewer.refresh();
		String s = ((ILabelProvider)viewer.getLabelProvider()).getText(selectedObject);
		viewer.getCombo().setText(s);
	}
	
	/**
	 * Helper method to calculate the width of a button.
	 * This is necessary for internationalization and accessibility.
	 * Returned value is the calculated width or defaultSize, whichever
	 * is larger.
	 */
	public static int calculateButtonWidth(Widget widget, int defaultSize){
		GC gc;
		int width = 0;
		
		if (widget instanceof Button) {
			Button w = (Button)widget;
			gc = new GC(w);
			gc.setFont(w.getFont());			
			width = gc.textExtent(w.getText()).x + 17;			
			gc.dispose();
			return Math.max(width, defaultSize);			
		}		
		return defaultSize;
	}
	
	
	public static String getMaxLengthString(String strings[]) {
		int max = -1;
		int index = -1;
		for (int i = 0; i < strings.length; i++) {
			if (strings[i].length() > max) {
				max = strings[i].length();
				index = i;
			}
		}
		
		if (index >= 0) return strings[index];
		return "";  //$NON-NLS-1$
	}
	
	/**
	 * Helper method to calculate the width of a CLabel.
	 * This is necessary for internationalization and accessibility.
	 * 
	 * Returned value is the calculated width or defaultSize, whichever
	 * is larger.
	 */
	public static int calculateLabelWidth(Widget widget, int defaultSize){
		GC gc;
		int width = 0;
		
		if (widget instanceof CLabel) {
			CLabel w = (CLabel)widget;
			gc = new GC(w);
			gc.setFont(w.getFont());			
			width = gc.textExtent(w.getText()).x + 17;			
			gc.dispose();
			
			return Math.max(width, defaultSize);			
		}		
		if (widget instanceof Label) {
			Label w = (Label)widget;
			gc = new GC(w);
			gc.setFont(w.getFont());			
			width = gc.textExtent(w.getText()).x + 5;			
			gc.dispose();
			return Math.max(width, defaultSize);			
		}
		return defaultSize;
	}

	public static IFile getFileFromURI(URI uri) {
		if (uri.isFile()) {
			return getFileFromDeviceURI(uri);
		}
		return getFileFromPlatformURI(uri);
	}
	
	public static IFile getFileFromDeviceURI(URI uri) {
		String device = uri.device();
		Iterator pathIt = uri.segmentsList().iterator();
		StringBuffer path = new StringBuffer();
		while (pathIt.hasNext()) {
			path.append("/" + pathIt.next()); //$NON-NLS-1$
		}
		return ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(device, path.toString()));
	}
	
	public static IFile getFileFromPlatformURI(URI uri) {
		String [] segs  = uri.segments();
		IPath path = null;
		// start at 1 to skip resource
		for (int i = 1; i< segs.length; i++) {
			if (path == null) {
				path = new Path(segs[i]);
			} else {
				path = path.append(segs[i]);
			}
		}
		return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
	}

	/** 
	 * Function to return a platform URI from a standard hierarchital URI.
	 * Normally we can use URI.createPlatformURI, but that function always assumes
	 * that it is non-platform
	 */
	public static URI getPlatformURI(URI uri) {
		String str = uri.toString();
		if (str.startsWith("platform:")) return uri; //$NON-NLS-1$
		return URI.createPlatformResourceURI(uri.toString());
	}

	/* external fault handler helpers */
	
	public static boolean getShowFaultHandler(EditPart part) {
		if (part instanceof ScopeEditPart)
			return ((ScopeEditPart)part).getShowFaultHandler();
		else if (part instanceof InvokeEditPart)
			return ((InvokeEditPart)part).getShowFaultHandler();
		else if (part instanceof StartNodeEditPart)
			return ((StartNodeEditPart)part).getShowFaultHandler();
		return false;
	}
	
	public static void setShowFaultHandler(EditPart part, boolean show) {
		if (part instanceof ScopeEditPart)
			((ScopeEditPart)part).setShowFaultHandler(show);
		else if (part instanceof InvokeEditPart)
			((InvokeEditPart)part).setShowFaultHandler(show);
		else if (part instanceof StartNodeEditPart)
			((StartNodeEditPart)part).setShowFaultHandler(show);
	}
	
	/* external compensation handler helpers */
	
	public static boolean getShowCompensationHandler(EditPart part) {
		if (part instanceof ScopeEditPart)
			return ((ScopeEditPart)part).getShowCompensationHandler();
		else if (part instanceof InvokeEditPart)
			return ((InvokeEditPart)part).getShowCompensationHandler();
		return false;
	}

	public static boolean getShowTerminationHandler(EditPart part) {
		if (part instanceof ScopeEditPart)
			return ((ScopeEditPart)part).getShowTerminationHandler();
		return false;
	}

	public static void setShowCompensationHandler(EditPart part, boolean show) {
		if (part instanceof ScopeEditPart)
			((ScopeEditPart)part).setShowCompensationHandler(show);
		else if (part instanceof InvokeEditPart)
			((InvokeEditPart)part).setShowCompensationHandler(show);
	}

	public static void setShowTerminationHandler(EditPart part, boolean show) {
		if (part instanceof ScopeEditPart)
			((ScopeEditPart)part).setShowTerminationHandler(show);
	}

	/* external event handler helpers */
	
	public static boolean getShowEventHandler(EditPart part) {
		if (part instanceof ScopeEditPart)
			return ((ScopeEditPart)part).getShowEventHandler();
		else if (part instanceof StartNodeEditPart)
			return ((StartNodeEditPart)part).getShowEventHandler();
		return false;
	}

	public static void setShowEventHandler(EditPart part, boolean show) {
		if (part instanceof ScopeEditPart)
			((ScopeEditPart)part).setShowEventHandler(show);
		else if (part instanceof StartNodeEditPart)
			((StartNodeEditPart)part).setShowEventHandler(show);
	}

	/**
	 * Returns the extension file of the given BPEL file.
	 */
	public static IFile getBPELEXFile(IFile bpelFile) {
		IPath path = bpelFile.getFullPath().removeFileExtension().addFileExtension(IBPELUIConstants.EXTENSION_MODEL_EXTENSIONS);
		return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
	}
	
	/**
	 * Returns the artifacts WSDL of the given BPEL file.
	 */
	public static IFile getArtifactsWSDLFile(IFile bpelFile) {
		IPath wsdlPath = bpelFile.getFullPath().removeFileExtension();
		String fileName = wsdlPath.lastSegment() + "Artifacts"; //$NON-NLS-1$
		wsdlPath = wsdlPath.removeLastSegments(1).append(fileName);
		wsdlPath = wsdlPath.addFileExtension(IBPELUIConstants.EXTENSION_WSDL);
		return ResourcesPlugin.getWorkspace().getRoot().getFile(wsdlPath);
	}
	
	public static Image getImage(IMarker marker) {
	    Image img = ModelMarkerUtil.getImage(marker);
	    ImageData background = null;
	    if (img != null) {
	    	background = img.getImageData();
	    }
		if (background == null) {
			// Don't give up yet. If this is also a problem marker, we can find an image to
			// display...
			try {
				if (marker.isSubtypeOf(IMarker.PROBLEM)) {
					background = ImageUtils.getImage(marker).getImageData();
				}
			} catch (CoreException e) {
				BPELUIPlugin.log(e);
				return null;
			}
		}
		if (background == null) return null;
		String uri = marker.getAttribute(IBPELUIConstants.MARKER_OVERLAYIMAGETOPLEFT, ""); //$NON-NLS-1$
		ImageData topLeft = getImageData(uri);
		uri = marker.getAttribute(IBPELUIConstants.MARKER_OVERLAYIMAGETOPRIGHT, ""); //$NON-NLS-1$
		ImageData topRight = getImageData(uri);
		uri = marker.getAttribute(IBPELUIConstants.MARKER_OVERLAYIMAGEBOTTOMLEFT, ""); //$NON-NLS-1$
		ImageData bottomLeft = getImageData(uri);
		uri = marker.getAttribute(IBPELUIConstants.MARKER_OVERLAYIMAGEBOTTOMRIGHT, ""); //$NON-NLS-1$
		ImageData bottomRight = getImageData(uri);
		OverlayCompositeImageDescriptor descriptor = new OverlayCompositeImageDescriptor(background, topLeft, topRight, bottomLeft, bottomRight);
		return descriptor.createImage();
	}

	private static ImageData getImageData(String uri) {
		if (uri.length() == 0) return null;
		URL url = null;
		try {
			url = new URL(uri);
		} catch (MalformedURLException e) {
			return null;
		}
		ImageDescriptor desc = ImageDescriptor.createFromURL(url);
		return desc.getImageData();
	}
	
	/**
	 * Returns the EditPart which is responsible for the given IFigure.
	 */
	public static EditPart mapFigure2EditPart(EditPartViewer viewer, IFigure figure) {
		Map visualPartMap = viewer.getVisualPartMap();
		EditPart part = null;
		while (part == null && figure != null) {
			part = (EditPart)visualPartMap.get(figure);
			figure = figure.getParent();
		}
		return part;
	}

	/**
	 * Reads the process from disk.
	 */
	public static Process getProcess(IResource bpelFile, ResourceSet resourceSet) throws IOException {
		URI uri = URI.createPlatformResourceURI(bpelFile.getFullPath().toString());
		Resource processResource = resourceSet.getResource(uri, true);
		EList contents = processResource.getContents();
		if (!contents.isEmpty()) {
			return (Process) contents.get(0);
		}
		return null;
	}

	public static AccessibleEditPart getAccessibleEditPart(GraphicalEditPart part) {
		final GraphicalEditPart thisPart = part;
		
		return new AccessibleEditPart() {
				public void getName(AccessibleEvent e) {
					String childType = null;
					String displayName = null;
					ILabeledElement labeledElement = (ILabeledElement)BPELUtil.adapt(thisPart.getModel(), ILabeledElement.class);
					if (labeledElement != null) {
						childType = labeledElement.getTypeLabel(thisPart.getModel());
						displayName = labeledElement.getLabel(thisPart.getModel());
						if (childType != null && displayName.equals(childType)) {
							childType = null;
						}
					} else {
						e.result = null;
						return;
					}

					// return something reasonable (type followed by name if any)
					// or nothing at all

					StringBuffer concat = new StringBuffer();
					if (childType != null && childType.length() > 0)
						concat.append(childType);
					if (concat.length() > 0)
						concat.append(" "); //$NON-NLS-1$
					if (displayName != null && displayName.length() > 0)
						concat.append(displayName);
					if (concat.length() > 0)
						e.result = concat.toString();
					else
						e.result = null;
					return;
				}

				public void getChildCount(AccessibleControlEvent e) {
					List list = thisPart.getChildren();
					int count = 0;
					for (int i = 0; i < list.size(); i++) {
						EditPart part = (EditPart)list.get(i);
						AccessibleEditPart access = (AccessibleEditPart)part.getAdapter(AccessibleEditPart.class);
						if (access == null)
							continue;
						count++;
					}
					e.detail = count;
				}

				public void getChildren(AccessibleControlEvent e) {
					List list = thisPart.getChildren();
					Vector childList = new Vector();
					for (int i = 0; i < list.size(); i++) {
						EditPart part = (EditPart)list.get(i);
						AccessibleEditPart access = (AccessibleEditPart)part.getAdapter(AccessibleEditPart.class);
						if (access == null)
							continue;
						childList.add(new Integer(access.getAccessibleID()));
					}
					e.children = childList.toArray();
				}
				
				public void getLocation(AccessibleControlEvent e) {
					Rectangle bounds = thisPart.getFigure().getBounds().getCopy();
					thisPart.getFigure().translateToAbsolute(bounds);
					org.eclipse.swt.graphics.Point p = new org.eclipse.swt.graphics.Point(0, 0);
					p = thisPart.getViewer().getControl().toDisplay(p);
					e.x = bounds.x + p.x;
					e.y = bounds.y + p.y;
					e.width = bounds.width;
					e.height = bounds.height;
				}
	
				/**
				 * @see AccessibleEditPart#getState(AccessibleControlEvent)
				 */
				public void getState(AccessibleControlEvent e) {
					e.detail = ACC.STATE_SELECTABLE | ACC.STATE_FOCUSABLE;
					if (thisPart.getSelected() != EditPart.SELECTED_NONE)
						e.detail |= ACC.STATE_SELECTED;
					if (thisPart.getViewer().getFocusEditPart() == thisPart)
						e.detail = ACC.STATE_FOCUSED;
				}
			};
	}
	
	
	/** creates a table cursor that can be used to navigate tables for keyboard accessibility **/
	
	public static TableCursor createTableCursor(final Table table, final TableViewer tableViewer) {
		// create a TableCursor to navigate around the table
		final TableCursor cursor = new TableCursor(table, SWT.NONE);
		cursor.addSelectionListener(new SelectionAdapter() {
			// when the TableEditor is over a cell, select the corresponding row in the table
			public void widgetSelected(SelectionEvent e) {
				if (cursor.getRow() != null)
					table.setSelection(new TableItem[] {cursor.getRow()});
			}
			// when the user hits "ENTER" in the TableCursor, pop up an editor
			public void widgetDefaultSelected(SelectionEvent e) {
				TableItem row = cursor.getRow();
				if (row != null) {
					int nRow = table.indexOf(row);
					int column = cursor.getColumn();
					Object obj = tableViewer.getElementAt(nRow);
					tableViewer.editElement(obj, column);
				}
			}
		});

		// Hide the TableCursor when the user hits the "CTRL" or "SHIFT" key.
		// This alows the user to select multiple items in the table.
		cursor.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if ((e.keyCode == SWT.CTRL) || (e.keyCode == SWT.SHIFT)	|| 
					(e.stateMask & SWT.CONTROL) != 0	|| (e.stateMask & SWT.SHIFT) != 0) {
					cursor.setVisible(false);
				}
			}
		});
		
		cursor.addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) { }
			public void mouseDown(MouseEvent e) {
				TableItem row = cursor.getRow();
				if (row != null) {
					int nRow = table.indexOf(row);
					int column = cursor.getColumn();
					Object obj = tableViewer.getElementAt(nRow);
					tableViewer.editElement(obj, column);
				}
			}
			public void mouseUp(MouseEvent e) {
			}
		});
				
		// Show the TableCursor when the user releases the "SHIFT" or "CTRL" key.
		// This signals the end of the multiple selection task.
		table.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CONTROL && (e.stateMask & SWT.SHIFT) != 0)
					return;
				if (e.keyCode == SWT.SHIFT && (e.stateMask & SWT.CONTROL) != 0)
					return;
				if (e.keyCode != SWT.CONTROL && (e.stateMask & SWT.CONTROL) != 0)
					return;
				if (e.keyCode != SWT.SHIFT && (e.stateMask & SWT.SHIFT) != 0)
					return;

				if (table.getItemCount() == 0)
					return;
				TableItem[] selection = table.getSelection();
				TableItem row = (selection.length == 0) ? table.getItem(table.getTopIndex()) : selection[0];
				table.showItem(row);
				cursor.setSelection(row, 0);
				cursor.setVisible(true);				
				cursor.setFocus();
			}
		});
		return cursor;
	}
	
	public static ResourceSet createResourceSetImpl() {
		// TODO: Extensibility
		return new ResourceSetImpl();
	}
	
	/**
	 * Returns a validator that checks that the new value is a valid NCName.
	 */
	public static IInputValidator getNCNameValidator() {
		return new IInputValidator() {
			public String isValid(String newText) {
				// TODO ! temporary hack
				return null;
			}
		};
	}
	
	public static void deleteNonContainmentRefs(EObject modelObject, Collection referents) {
		if (modelObject == null) return;
		for (Iterator it = modelObject.eClass().getEAllReferences().iterator(); it.hasNext(); ) {
			EReference feature = (EReference)it.next();
			if (feature.isMany()) {
				EList list = (EList)modelObject.eGet(feature, true);
				for (Iterator it2 = referents.iterator(); it2.hasNext(); ) {
					Object referent = it2.next();
					if (list.contains(referent)) list.remove(referent);
					// TODO: support non-changeable features!  print a warning.
				}
			} else {
				Object oldValue = modelObject.eGet(feature, true);
				for (Iterator it2 = referents.iterator(); it2.hasNext(); ) {
					Object referent = it2.next();
					if (oldValue == referent) {
						if (feature.isUnsettable()) {
							// this is okay, default is always null for EReferences.
							modelObject.eUnset(feature);
						} else {
							modelObject.eSet(feature, null);
						}
						break;
					}
					// TODO: support non-changeable features!  print a warning.
				}
			}
		}
	}

	//@return:  returns arraylist with all activities the compensate
	//			can validly point to
	public static ArrayList getCompensableActivities(Object context){
		final ArrayList returnObjects = new ArrayList();		
		if (context instanceof Compensate) {
			Compensate compensate = (Compensate) context;
			EObject enclosingContainer = compensate;
			if (compensate.eContainer() != null) {
				enclosingContainer = enclosingContainer.eContainer();
				// Go to parent scope where compensate is contained
				while (!(enclosingContainer instanceof Scope)
						&& (enclosingContainer.eContainer() != null)) {
					enclosingContainer = enclosingContainer.eContainer();
				}
			}
	
			// Put all scopes and invokes within parent scope in arraylist
			visitModelDepthFirst(enclosingContainer,
					new IModelVisitor() {
						public boolean visit(Object modelObject) {
							if (modelObject instanceof Scope) {
								returnObjects.add(modelObject);
							} else if (modelObject instanceof Invoke) {
								returnObjects.add(modelObject);
							}
							return true;
						}
					});		
			returnObjects.remove(0);	//remove the scope containing the compensate			
			return returnObjects;
			}
			throw new IllegalArgumentException();			
	}
	
	public static Object resolveXSDObject(Object xsdObject) {
		if (xsdObject instanceof XSDElementDeclaration) {
			XSDElementDeclaration resolvedElement = ((XSDElementDeclaration)xsdObject).getResolvedElementDeclaration();
			if (resolvedElement != null) xsdObject = resolvedElement;
		} else if (xsdObject instanceof XSDAttributeDeclaration) {
			XSDAttributeDeclaration resolvedAttribute = ((XSDAttributeDeclaration)xsdObject).getResolvedAttributeDeclaration();
			if (resolvedAttribute != null) xsdObject = resolvedAttribute;
		}
		return xsdObject;
	}
	
	public static String debugObject(Object object) {
		if (object == null) return "null"; //$NON-NLS-1$
		if (object instanceof String) { return "\""+(String)object+"\""; } //$NON-NLS-1$ //$NON-NLS-2$
		if (object.getClass().getName().startsWith("java.lang")) { //$NON-NLS-1$
			return object.toString();
		}
		if (object instanceof List) {
			StringBuffer b = new StringBuffer("("); //$NON-NLS-1$
			for (Iterator it = ((List)object).iterator(); it.hasNext(); ) {
				b.append(debugObject(it.next()));
				if (it.hasNext()) b.append(", "); //$NON-NLS-1$
			}
			b.append(")"); //$NON-NLS-1$
			return b.toString();
		}
		if (object instanceof QName) {
			QName qname = (QName)object;
			return "QName(\""+qname.getNamespaceURI()+"\", \""+qname.getLocalPart()+"\")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		StringBuffer b = new StringBuffer(shortClassName(object.getClass()));
		boolean proxy = (object instanceof EObject) && ((EObject)object).eIsProxy();
		if (proxy) b.append("-proxy"); //$NON-NLS-1$
		boolean isEObject = (object instanceof EObject);
		INamedElement namedElement = null;
		if (isEObject) {
			namedElement = (INamedElement)BPELUtil.adapt(object, INamedElement.class);
			if (namedElement != null) {
				try {
					String s = namedElement.getName(object);
					b.append((s==null)? "<null>" : "<\""+s+"\">"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				} catch (Exception e) {
					b.append("<???>"); //$NON-NLS-1$
				}
			}
		}
		if (namedElement==null) {
			b.append("{"); b.append(String.valueOf(object.hashCode())); b.append("}"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return b.toString();
	}
	
	public static String debug(Notification n) {
		StringBuffer b = new StringBuffer(shortClassName(n.getClass()));
		b.append("("); //$NON-NLS-1$
		b.append(debugObject(n.getNotifier()));
		b.append(", "); //$NON-NLS-1$
		switch (n.getEventType()) {
		case Notification.SET-1: b.append("CREATE"); break; //$NON-NLS-1$
		case Notification.SET: b.append("SET"); break; //$NON-NLS-1$
		case Notification.UNSET: b.append("UNSET"); break; //$NON-NLS-1$
		case Notification.ADD: b.append("ADD"); break; //$NON-NLS-1$
		case Notification.REMOVE: b.append("REMOVE"); break; //$NON-NLS-1$
		case Notification.ADD_MANY: b.append("ADD_MANY"); break; //$NON-NLS-1$
		case Notification.MOVE: b.append("MOVE"); break; //$NON-NLS-1$
		case Notification.REMOVING_ADAPTER: b.append("REMOVING_ADAPTER"); break; //$NON-NLS-1$
		case Notification.RESOLVE: b.append("RESOLVE"); break; //$NON-NLS-1$
		default: b.append("??? ("+String.valueOf(n.getEventType())+")"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		b.append(" "); //$NON-NLS-1$
		EStructuralFeature feature = (EStructuralFeature)n.getFeature();
		if (feature == null) b.append("???"); else b.append(feature.getName()); //$NON-NLS-1$
		if (n.getPosition() >= 0) {
			b.append("["); //$NON-NLS-1$
			b.append(String.valueOf(n.getPosition()));
			b.append("]"); //$NON-NLS-1$
		} else {
			if (feature != null && feature.isMany()) b.append("{***}"); //$NON-NLS-1$ 
		}
		b.append(": "); //$NON-NLS-1$
		b.append(debugObject(n.getOldValue()));
		b.append(" --> "); //$NON-NLS-1$
		b.append(debugObject(n.getNewValue()));
		b.append(")"); //$NON-NLS-1$
		return b.toString();
		
	}
	protected static String shortClassName(Class clazz) {
		StringBuffer b = new StringBuffer(clazz.getName());
		for (int i = b.indexOf("."); i >= 0; i = b.indexOf(".")) b.delete(0,i+1); //$NON-NLS-1$ //$NON-NLS-2$
		//if (b.indexOf("Impl") == b.length()-4) b.delete(b.length()-4, b.length()));
		return b.toString();
	}

	/**
	 * Creates a composite with a flat border around it.
	 */
	public static Composite createBorderComposite(Composite parent, TabbedPropertySheetWidgetFactory wf) {
		final Composite result = wf.createComposite(parent);
		FillLayout layout = new FillLayout();
		final int margin = 1;
		layout.marginHeight = margin;
		layout.marginWidth = margin;
		result.setLayout(layout);
		result.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				org.eclipse.swt.graphics.Rectangle bounds = result.getBounds();
				bounds.x = margin-1;
				bounds.y = margin-1;
				bounds.width = bounds.width - (margin*2) + 1;
				bounds.height = bounds.height - (margin*2) + 1;
				e.gc.drawRectangle(bounds);
			}
		});
		return result;
	}

	private static void addVariablesToMap(Map targetMap, Variables vars) {
		if (vars == null) return;
		for (Iterator it = vars.getChildren().iterator(); it.hasNext(); ) {
			Variable v = (Variable)it.next();
			if (v.getName() != null) targetMap.put(v.getName(), v);
		}
	}
	private static void addVisibleVariables(Map targetMap, EObject target) {
		if (target == null) return;
		if (target instanceof Resource) return;
		if (target instanceof Process) {
			addVariablesToMap(targetMap, ((Process)target).getVariables());
		} else {
			// recursively add less local variables first
			addVisibleVariables(targetMap, target.eContainer());
			if (target instanceof Scope) {
				addVariablesToMap(targetMap, ((Scope)target).getVariables());
			}
			if (target instanceof Catch) {
				Variable v = ((Catch)target).getFaultVariable();
				if (v != null && v.getName() != null) targetMap.put(v.getName(), v);
			}
			if (target instanceof OnEvent) {
				Variable v = ((OnEvent)target).getVariable();
				if (v != null && v.getName() != null) targetMap.put(v.getName(), v);
			}
			if (target instanceof ForEach) {
				Variable v = ((ForEach)target).getCounterName();
				if (v != null && v.getName() != null) targetMap.put(v.getName(), v);
			}
		}
	}

	private static void addPartnerLinksToMap(Map targetMap, PartnerLinks plinks) {
		if (plinks == null) return;
		for (Iterator it = plinks.getChildren().iterator(); it.hasNext(); ) {
			PartnerLink p = (PartnerLink)it.next();
			if (p.getName() != null) targetMap.put(p.getName(), p);
		}
	}
	private static void addVisiblePartnerLinks(Map targetMap, EObject target) {
		if (target == null) return;
		if (target instanceof Resource) return;
		if (target instanceof Process) {
			addPartnerLinksToMap(targetMap, ((Process)target).getPartnerLinks());
		} else {
			// recursively add less local partnerlinks first
			addVisiblePartnerLinks(targetMap, target.eContainer());
			if (target instanceof Scope) {
				addPartnerLinksToMap(targetMap, ((Scope)target).getPartnerLinks());
			}
		}
	}
	
	private static void addCorrelationSetsToMap(Map targetMap, CorrelationSets csets) {
		if (csets == null) return;
		for (Iterator it = csets.getChildren().iterator(); it.hasNext(); ) {
			CorrelationSet c = (CorrelationSet)it.next();
			if (c.getName() != null) targetMap.put(c.getName(), c);
		}
	}
	private static void addVisibleCorrelationSets(Map targetMap, EObject target) {
		if (target == null) return;
		if (target instanceof Resource) return;
		if (target instanceof Process) {
			addCorrelationSetsToMap(targetMap, ((Process)target).getCorrelationSets());
		} else {
			// recursively add less local correlationsets first
			addVisibleCorrelationSets(targetMap, target.eContainer());
			if (target instanceof Scope) {
				addCorrelationSetsToMap(targetMap, ((Scope)target).getCorrelationSets());
			}
		}
	}
	
	/**
	 * Look up the variables visible to a certain context activity (or the whole process).
	 * Variables in BPEL follow lexical scoping rules (resolved OASIS issue 101).
	 * 
	 * The returned variables are in no particular order.
	 */
	public static Variable[] getVisibleVariables(EObject target) {
		Map name2Variable = new HashMap();
		addVisibleVariables(name2Variable, target);
		if (name2Variable.isEmpty()) return EMPTY_VARIABLE_ARRAY;
		Variable[] result = new Variable[name2Variable.size()];
		name2Variable.values().toArray(result);
		return result;
	}
	
	/**
	 * Look up the PartnerLinks visible to a certain context activity (or the whole process).
	 * When local PartnerLinks are added to the spec, they will follow lexical scoping rules
	 * just like variables.
	 * 
	 * The returned PartnerLinks are in no particular order.
	 */
	public static PartnerLink[] getVisiblePartnerLinks(EObject target) {
		Map name2PartnerLink = new HashMap();
		addVisiblePartnerLinks(name2PartnerLink, target);
		if (name2PartnerLink.isEmpty()) return EMPTY_PARTNERLINK_ARRAY;
		PartnerLink[] result = new PartnerLink[name2PartnerLink.size()];
		name2PartnerLink.values().toArray(result);
		return result;
	}
	
	/**
	 * Look up the PartnerLinks visible to a certain context activity (or the whole process).
	 * When local PartnerLinks are added to the spec, they will follow lexical scoping rules
	 * just like variables.
	 * 
	 * The returned PartnerLinks are in no particular order.
	 */
	public static CorrelationSet[] getVisibleCorrelationSets(EObject target) {
		Map name2CorrelationSet = new HashMap();
		addVisibleCorrelationSets(name2CorrelationSet, target);
		if (name2CorrelationSet.isEmpty()) return EMPTY_CORRELATIONSET_ARRAY;
		CorrelationSet[] result = new CorrelationSet[name2CorrelationSet.size()];
		name2CorrelationSet.values().toArray(result);
		return result;
	}
	
	/**
	 * If the given message is used by an operation in the same definition,
	 * returns the Operation that uses the given message.  
	 * Otherwise, returns null. 
	 */
	public static Operation getOperationFromMessage(Message message) {
		if (message == null) return null;
		Definition def = message.getEnclosingDefinition();
		if (def == null) return null;
		Iterator ptIt = def.getEPortTypes().iterator();
		while (ptIt.hasNext()) {
			PortType pt = (PortType)ptIt.next();
			Iterator it = pt.getOperations().iterator();
			while (it.hasNext()) {
				Operation op = (Operation)it.next();
				Input input = op.getEInput();
				if (input != null) {
					if (input.getMessage().getQName().equals(message.getQName())) {
						return op;
					}
				}
				Output output = op.getEOutput();
				if (output != null) {
					if (output.getMessage().getQName().equals(message.getQName())) {
						return op;
					}
				}
				Iterator faultIterator = op.getEFaults().iterator();
				while (faultIterator.hasNext()) {
					Fault fault = (Fault)faultIterator.next();
					Message faultMessage = fault.getEMessage();
					if (faultMessage != null) {
						if (faultMessage.getQName() != null) {
							if (faultMessage.getQName().equals(message.getQName())) {
								return op;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	public static void openEditor(EObject modelObject, BPELEditor editor) {
		try {
			Assert.isNotNull(modelObject);
			Assert.isNotNull(modelObject.eResource());
			IFile file = BPELUtil.getFileFromURI(modelObject.eResource().getURI());
			IDE.openEditor(editor.getSite().getWorkbenchWindow().getActivePage(), file);	
		} catch (PartInitException ex) {
			BPELUIPlugin.log(ex, IStatus.WARNING);
		}
	}

	/**
	 * Returns the BPEL file associated with the given process.
	 */
	public static IFile getBPELFile(Process process) {
		return getFileFromURI(process.eResource().getURI());
	}

	/**
	 * Provides a generic implementation of {@link IMarkerHolder#getMarkers}.
	 */
	public static IMarker[] getMarkers(Object object) {
		EObject modelObject = (EObject) object;
		// If the model object is not contained in a resource, we fail.
		if (modelObject.eResource() == null)
			return new IMarker[0];
		BPELEditor editor = ModelHelper.getBPELEditor(modelObject);
		IFile file = ((IFileEditorInput) editor.getEditorInput()).getFile();
		try {
			IMarker[] markers = file.findMarkers(null, true, IResource.DEPTH_ZERO);
			if (markers.length == 0) return markers;
			List result = new ArrayList();
			for (int i = 0; i < markers.length; i++) {
				IMarker marker = markers[i];
				// TODO: Provide a mechanism to look up the EMF object
				// for a particular marker
				EObject markerModelObject = null;
				if (modelObject == markerModelObject) {
					result.add(marker);
				}
			}
			return (IMarker[]) result.toArray(new IMarker[result.size()]);
		} catch (CoreException e) {
			BPELUIPlugin.log(e);
			return new IMarker[0];
		}
	}
	
	/**
	 * Traverses the root object and returns all objects under it that are of the same 
	 * class or subclasses of "target".
	 */
	public static List getAllEObjectsOfType(EObject root, EClass eClass) {
		List allElems = new ArrayList();
		for (TreeIterator iter = root.eAllContents(); iter.hasNext();) {
			EObject element = (EObject) iter.next();
			if (eClass.isSuperTypeOf(element.eClass()) ||
				element.eClass() == eClass) {
				allElems.add(element);
			}
		}
		return allElems;
	}	
}