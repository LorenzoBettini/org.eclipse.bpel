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
package org.eclipse.bpel.common.extension.model.impl;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.bpel.common.extension.model.Extension;
import org.eclipse.bpel.common.extension.model.ExtensionMap;

import org.eclipse.bpel.common.extension.model.ExtensionmodelFactory;
import org.eclipse.bpel.common.extension.model.ExtensionmodelPackage;
import org.eclipse.bpel.common.extension.model.adapters.ExtendedObjectAdapter;
import org.eclipse.bpel.common.extension.model.adapters.ExtendedObjectUserAdapter;
import org.eclipse.bpel.common.extension.model.adapters.impl.ExtendedObjectAdapterImpl;
import org.eclipse.bpel.common.extension.model.adapters.impl.ExtendedObjectUserAdapterImpl;
import org.eclipse.bpel.common.extension.model.adapters.impl.ExtensionAdapterImpl;
import org.eclipse.bpel.common.extension.model.adapters.impl.ExtensionMapAdapterImpl;
import org.eclipse.bpel.common.extension.model.notify.ExtensionModelNotification;
import org.eclipse.bpel.common.extension.model.notify.impl.ExtensionModelNotificationImpl;
import org.eclipse.bpel.common.extension.model.util.ExtensionmodelAdapterFactory;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Extension Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.common.extension.model.impl.ExtensionMapImpl#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link org.eclipse.bpel.common.extension.model.impl.ExtensionMapImpl#getExtensions <em>Extensions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExtensionMapImpl extends EObjectImpl implements ExtensionMap {
	/**
	 * The default value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected static final String NAMESPACE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected String namespace = NAMESPACE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExtensions() <em>Extensions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensions()
	 * @generated
	 * @ordered
	 */
	protected EList extensions = null;
	
	/**
	 * The value of the user adapter if one has been instantiated via the initializeAdapter()
	 * method.  This attribute is not modeled as it is not required to 
	 * serialize its value.
	 * @customized
	 */
	private ExtendedObjectUserAdapter userAdapter = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtensionMapImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ExtensionmodelPackage.eINSTANCE.getExtensionMap();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamespace(String newNamespace) {
		String oldNamespace = namespace;
		namespace = newNamespace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExtensionmodelPackage.EXTENSION_MAP__NAMESPACE, oldNamespace, namespace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getExtensions() {
		if (extensions == null) {
			extensions = new EObjectContainmentEList(Extension.class, this, ExtensionmodelPackage.EXTENSION_MAP__EXTENSIONS);
		}
		return extensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @customized
	 */
	public void initializeAdapter() {
		ExtensionmodelAdapterFactory factory = new ExtensionmodelAdapterFactory();
		if (userAdapter == null)
			userAdapter = (ExtendedObjectUserAdapter)factory.createEObjectAdapter();
		
		userAdapter.setExtensionMap(this);
		
		Set extendedObjects = this.keySet();
		
		for (Iterator iter = extendedObjects.iterator(); iter.hasNext();) {
			EObject element = (EObject) iter.next();
			adaptEObject(element,userAdapter);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ExtensionmodelPackage.EXTENSION_MAP__EXTENSIONS:
					return ((InternalEList)getExtensions()).basicRemove(otherEnd, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ExtensionmodelPackage.EXTENSION_MAP__NAMESPACE:
				return getNamespace();
			case ExtensionmodelPackage.EXTENSION_MAP__EXTENSIONS:
				return getExtensions();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ExtensionmodelPackage.EXTENSION_MAP__NAMESPACE:
				setNamespace((String)newValue);
				return;
			case ExtensionmodelPackage.EXTENSION_MAP__EXTENSIONS:
				getExtensions().clear();
				getExtensions().addAll((Collection)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ExtensionmodelPackage.EXTENSION_MAP__NAMESPACE:
				setNamespace(NAMESPACE_EDEFAULT);
				return;
			case ExtensionmodelPackage.EXTENSION_MAP__EXTENSIONS:
				getExtensions().clear();
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ExtensionmodelPackage.EXTENSION_MAP__NAMESPACE:
				return NAMESPACE_EDEFAULT == null ? namespace != null : !NAMESPACE_EDEFAULT.equals(namespace);
			case ExtensionmodelPackage.EXTENSION_MAP__EXTENSIONS:
				return extensions != null && !extensions.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (namespace: ");
		result.append(namespace);
		result.append(')');
		return result.toString();
	}

	/**
	 * This is the Map entrySet.
	 */
	public class EntrySet extends java.util.AbstractSet {
		/**
		 * ESet constructor comment.
		 */
		protected EntrySet() {
			super();
		}

		public java.util.Iterator iterator() {
			return new EIterator();
		}

		public int size() {
			int n=0;
			for (Iterator i=iterator(); i.hasNext(); ) {
				i.next();
				n++;
			}
			return n;
		}

		/**
		 * This is the map entry iterator.
		 */
		public class EIterator implements java.util.Iterator {
			private Iterator fieldListIterator;
			/**
			 * EIterator constructor comment.
			 */
			public EIterator() {
				super();
				fieldListIterator = getExtensions().iterator();
			}
			/**
			 * Returns <tt>true</tt> if the iteration has more elements. (In other
			 * words, returns <tt>true</tt> if <tt>next</tt> would return an element
			 * rather than throwing an exception.)
			 *
			 * @return <tt>true</tt> if the iterator has more elements.
			 */
			public boolean hasNext() {
				return fieldListIterator.hasNext();
			}
			/**
			 * Returns the next element in the iteration.
			 *
			 * @returns the next element in the iteration.
			 * @exception NoSuchElementException iteration has no more elements.
			 */
			public Object next() {
				Extension object = (Extension)fieldListIterator.next();
				if (object == null)
					return new Entry(null, null);
				return new Entry(object.getExtendedObject(),object.getExtensionObject());
			}
			/**
			 * 
			 * Removes from the underlying collection the last element returned by the
			 * iterator (optional operation).  This method can be called only once per
			 * call to <tt>next</tt>.  The behavior of an iterator is unspecified if
			 * the underlying collection is modified while the iteration is in
			 * progress in any way other than by calling this method.
			 *
			 * @exception UnsupportedOperationException if the <tt>remove</tt>
			 *        operation is not supported by this Iterator.
			 
			 * @exception IllegalStateException if the <tt>next</tt> method has not
			 *        yet been called, or the <tt>remove</tt> method has already
			 *        been called after the last call to the <tt>next</tt>
			 *        method.
			 */
			public void remove() {
				fieldListIterator.remove();
			}

		}
	}

	/**
	 * This is the Map entrySet.
	 */
	public class KeySet extends java.util.AbstractSet {

		/**
		 * ESet constructor comment.
		 */
		protected KeySet() {
			super();
		}

		public java.util.Iterator iterator() {
			return new EIterator();
		}

		public int size() {
			int n=0;
			for (Iterator i=iterator(); i.hasNext(); ) {
				i.next();
				n++;
			}
			return n;
		}

		/**
		 * This is the map entry iterator.
		 */
		public class EIterator implements java.util.Iterator {
			private Iterator fieldListIterator;
			/**
			 * EIterator constructor comment.
			 */
			public EIterator() {
				super();
				fieldListIterator = getExtensions().iterator();
					
			}
			/**
			 * Returns <tt>true</tt> if the iteration has more elements. (In other
			 * words, returns <tt>true</tt> if <tt>next</tt> would return an element
			 * rather than throwing an exception.)
			 *
			 * @return <tt>true</tt> if the iterator has more elements.
			 */
			public boolean hasNext() {
				return fieldListIterator.hasNext();
			}
			/**
			 * Returns the next element in the iteration.
			 *
			 * @returns the next element in the iteration.
			 * @exception NoSuchElementException iteration has no more elements.
			 */
			public Object next() {
				Extension object = (Extension)fieldListIterator.next();
				if (object == null)
					return null;
				return object.getExtendedObject();
			}
			/**
			 * 
			 * Removes from the underlying collection the last element returned by the
			 * iterator (optional operation).  This method can be called only once per
			 * call to <tt>next</tt>.  The behavior of an iterator is unspecified if
			 * the underlying collection is modified while the iteration is in
			 * progress in any way other than by calling this method.
			 *
			 * @exception UnsupportedOperationException if the <tt>remove</tt>
			 *        operation is not supported by this Iterator.
			 
			 * @exception IllegalStateException if the <tt>next</tt> method has not
			 *        yet been called, or the <tt>remove</tt> method has already
			 *        been called after the last call to the <tt>next</tt>
			 *        method.
			 */
			public void remove() {
				fieldListIterator.remove();
			}

		}
	}
	
	/**
	 * This is the Map entrySet.
	 */
	public class Values extends AbstractCollection {
		/**
		 * ESet constructor comment.
		 */
		protected Values() {
			super();
		}

		public java.util.Iterator iterator() {
			return new EIterator();
		}

		public int size() {
			int n=0;
			for (Iterator i=iterator(); i.hasNext(); ) {
				i.next();
				n++;
			}
			return n;
		}

		/**
		 * This is the map entry iterator.
		 */
		public class EIterator implements java.util.Iterator {
			private Iterator fieldListIterator;
			/**
			 * EIterator constructor comment.
			 */
			public EIterator() {
				super();
				fieldListIterator = getExtensions().iterator();
			}
			/**
			 * Returns <tt>true</tt> if the iteration has more elements. (In other
			 * words, returns <tt>true</tt> if <tt>next</tt> would return an element
			 * rather than throwing an exception.)
			 *
			 * @return <tt>true</tt> if the iterator has more elements.
			 */
			public boolean hasNext() {
				return fieldListIterator.hasNext();
			}
			/**
			 * Returns the next element in the iteration.
			 *
			 * @returns the next element in the iteration.
			 * @exception NoSuchElementException iteration has no more elements.
			 */
			public Object next() {
				Extension object = (Extension)fieldListIterator.next();
				if (object == null)
					return null;
				return object.getExtensionObject();
			}
			/**
			 * 
			 * Removes from the underlying collection the last element returned by the
			 * iterator (optional operation).  This method can be called only once per
			 * call to <tt>next</tt>.  The behavior of an iterator is unspecified if
			 * the underlying collection is modified while the iteration is in
			 * progress in any way other than by calling this method.
			 *
			 * @exception UnsupportedOperationException if the <tt>remove</tt>
			 *        operation is not supported by this Iterator.
			 
			 * @exception IllegalStateException if the <tt>next</tt> method has not
			 *        yet been called, or the <tt>remove</tt> method has already
			 *        been called after the last call to the <tt>next</tt>
			 *        method.
			 */
			public void remove() {
				fieldListIterator.remove();
			}

		}
	}
	/**
	 * Map entry.
	 */
	public class Entry implements Map.Entry {
		Object key;
		Object value;

		public Entry(Object key, Object value) {
			this.key = key;
			this.value = value;
		}

		public Object getKey() {
			return key;
		}

		public Object getValue() {
			return value;
		}

		public Object setValue(Object value) {
			Object oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public boolean equals(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry e = (Map.Entry) o;
			return (key == null ? e.getKey() == null : key.equals(e.getKey())) && (value == null ? e.getValue() == null : value.equals(e.getValue()));
		}

		public int hashCode() {
			return key == null ? 0 : key.hashCode();
		}

		public String toString() {
			return key + "=" + value;
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	public int size() {
		return getExtensions().size();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return getExtensions().isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object extendedObject) {
		boolean containsKey = false;
		if(extendedObject != null){
			
			for (Iterator iter = getExtensions().iterator(); iter.hasNext();) {
				if(((Extension)iter.next()).getExtendedObject().equals(extendedObject)){
					containsKey = true;
					break;	
				}
			}
		}
		return containsKey;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object extensionObject) {
		boolean containsValue = false;
		if(extensionObject != null){
			for (Iterator iter = getExtensions().iterator(); iter.hasNext();) {
				if(((Extension)iter.next()).getExtensionObject().equals(extensionObject)){
					containsValue = true;
					break;	
				}
			}
		}
		return containsValue;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public Object get(Object extendedObject) {
		Object extension = null;
		
		extension = getExtensionObject((EObject)extendedObject);
		if(extension!=null)
			return ((Extension)extension).getExtensionObject();
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(Object extendedObject, Object extensionObject) {
		
		EObject originalObject = null;
		
		if(extendedObject != null && extensionObject!= null){
						
			Extension extension = getExtensionObject((EObject)extendedObject);
			
			if(extension!= null)
				originalObject = extension.getExtensionObject();	
			else {
				extension = ExtensionmodelFactory.eINSTANCE.createExtension();
				getExtensions().add(extension);
			}

			extension.setExtendedObject((EObject)extendedObject);
			extension.setExtensionObject((EObject)extensionObject);
						
			ExtensionmodelAdapterFactory adapterFactory = new ExtensionmodelAdapterFactory();
			
			ExtendedObjectAdapter extAdptr = (ExtendedObjectAdapter) adapterFactory.createExtendedObjectAdapter();
			
			extAdptr.setExtension(extension);
			extAdptr.setNamespace(getNamespace());
			
			adaptEObject((EObject)extendedObject,extAdptr);

			adapterFactory.adapt(extension,ExtensionAdapterImpl.class);
			
			if(userAdapter == null)
				initializeAdapter();
			else 				
				adaptEObject((EObject)extendedObject,userAdapter);
			
			if (eNotificationRequired())
				eNotify(new ExtensionModelNotificationImpl(this, Notification.ADD, ExtensionModelNotification.EXTENSION_MAP_PUT, extendedObject, originalObject));

			return originalObject;
							
		}else
			return null;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public Object remove(Object extendedObject) {
		if(extendedObject == null)
			return null;
		else{
			Extension extensionObject = getExtensionObject((EObject)extendedObject);
			if(getExtensions().contains(extensionObject)){
				Object oldExtension = extensionObject.getExtensionObject();
				getExtensions().remove(extensionObject);
				if (eNotificationRequired())
					eNotify(new ExtensionModelNotificationImpl(this, Notification.REMOVE, ExtensionModelNotification.EXTENSION_MAP_REMOVE, extendedObject, oldExtension));
			}
			
			// Remove adapter if one exists
			removeAdapters((EObject) extendedObject);
				
			if(extensionObject != null)
				return extensionObject.getExtensionObject();
			else
				return null;
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map extendedObjectMap) {

		Map originalMap = null;
		if (eNotificationRequired()){
			originalMap = buildMap();
		}
		
		if(extendedObjectMap != null && !extendedObjectMap.isEmpty()){
			for (Iterator iter = extendedObjectMap.entrySet().iterator(); iter.hasNext();) {
				Map.Entry entry = (Map.Entry) iter.next();
				put(entry.getKey(),entry.getValue());			
			}
		}
		
		if (eNotificationRequired())
			eNotify(new ExtensionModelNotificationImpl(this, Notification.ADD_MANY, ExtensionModelNotification.EXTENSION_MAP_PUTALL, originalMap, null));
		
	}

	/* (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	public void clear() {
		Map originalMap = null;
		if (eNotificationRequired() && !getExtensions().isEmpty()){
			originalMap = buildMap();
		}

		getExtensions().clear();
		
		if (originalMap != null){
			int et = Notification.REMOVE_MANY;
			if(originalMap.size() == 1)
				et = Notification.REMOVE;
			
			eNotify(new ExtensionModelNotificationImpl(this, et, ExtensionModelNotification.EXTENSION_MAP_CLEAR, originalMap, null));
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	public Set keySet() {
		return new KeySet();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	public Collection values() {
		return new Values();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	public Set entrySet() {
		return new EntrySet();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @customized
	 */
	private Extension getExtensionObject(EObject extendedObject) {
		Extension extension = null;

		if(extendedObject!=null){
		
			// First check if the is an adapted associated with this object.
			// If there is, the adapter points to the Extension object.  Return this
			// object.
			EList adapterList = extendedObject.eAdapters();
					
			for (Iterator iter = adapterList.iterator(); iter.hasNext();) {
				Adapter element = (Adapter) iter.next();
				if(element instanceof ExtendedObjectAdapter && ((ExtendedObjectAdapter)element).getNamespace().equals(getNamespace())){
					extension = ((ExtendedObjectAdapter)element).getExtension();
					break;
				}
			}
					
			// Was the extension found though the adapter?  Not if it's null.
			// There is no adapter associated with this bject so we must search the
			// Extension list.  Once the Extension is found, associate an adapter with this
			// object to improve performance in future queries.
			if(extension == null){
					
				EList extensionList = getExtensions();
						
				for (Iterator iter = extensionList.iterator(); iter.hasNext();) {
					Extension ext = (Extension) iter.next();
					
					if (ext.getExtendedObject() != null && ext.getExtendedObject().equals(extendedObject)){
						extension = ext;
						
						ExtensionmodelAdapterFactory adapterFactory = new ExtensionmodelAdapterFactory();
				
						ExtendedObjectAdapter extAdptr = (ExtendedObjectAdapter) adapterFactory.createExtendedObjectAdapter();
				
						extAdptr.setExtension(extension);
						extAdptr.setNamespace(getNamespace());
						
						ExtensionmodelFactory.eINSTANCE.adaptEObject(extendedObject,extAdptr);
						
						adapterFactory.adapt(extension,ExtensionAdapterImpl.class);
						
						break;
					}
					 
				}
			}
		}
		
		return extension;
	}
	
	/**
	 * @return ExtendedObjectUserAdapter
	 * @customized
	 */
	public ExtendedObjectUserAdapter getUserAdapter() {
		return userAdapter;
	}
	
	/**
	  * Method adaptEObject.
	  * This method associates an adapter to a model object.
	  * @param target - object to which the adaptor is to be associated
	  * @param adapter - adapter to asociate to target
	  * @customized
	  */
	 private void adaptEObject(EObject target, Adapter adapter) {
		
		if(target != null  && adapter != null){
			 for (Iterator adapters = target.eAdapters().iterator(); adapters.hasNext(); )
			 {
			   Adapter currAdapter = (Adapter)adapters.next();
			   if ((adapter instanceof ExtendedObjectUserAdapter && currAdapter instanceof ExtendedObjectUserAdapter && ((ExtendedObjectUserAdapter)currAdapter).getNamespace().equals(getNamespace()))||
			       (adapter instanceof ExtendedObjectAdapter && currAdapter instanceof ExtendedObjectAdapter && ((ExtendedObjectAdapter)currAdapter).getNamespace().equals(getNamespace())))
				 return;
			 }
			   
			 target.eAdapters().add(adapter);
		}
	   
	 }

	 /**
	  * @customized
	  */
	 private void removeAdapters(EObject target) {
		
		if(target != null){
			EList removeAdapterList = new BasicEList();
			for (Iterator adapters = target.eAdapters().iterator(); adapters.hasNext(); ){
				Adapter currAdapter = (Adapter)adapters.next();
				if ((currAdapter instanceof ExtendedObjectUserAdapter && ((ExtendedObjectUserAdapter)currAdapter).getNamespace().equals(getNamespace()))||
					(currAdapter instanceof ExtendedObjectAdapter && ((ExtendedObjectAdapter)currAdapter).getNamespace().equals(getNamespace())))
					removeAdapterList.add(currAdapter);	
			}
		   	
		   	for (Iterator iter = removeAdapterList.iterator(); iter.hasNext();) {
				target.eAdapters().remove(iter.next()); 
			}
		}
	 }

	 private Map buildMap(){
	 	
	 	boolean buildMap = false;
	 	
	 	for (Iterator iter = eAdapters().iterator(); iter.hasNext(); ) {
	 		Adapter element = (Adapter) iter.next();
	 		if(!(element instanceof ExtendedObjectAdapterImpl) && 
	 				!(element instanceof ExtendedObjectUserAdapterImpl) && 
					!(element instanceof ExtensionAdapterImpl) && 
					!(element instanceof ExtensionMapAdapterImpl)){
	 			buildMap = true;
	 			break;
	 		}
	 	}
	    
	 	if(!buildMap)
	 		return null;
	 	
		Map map = new HashMap();
		for (Iterator iter = getExtensions().iterator(); iter.hasNext();) {
			Extension element = (Extension) iter.next();
			map.put(element.getExtendedObject(), element.getExtensionObject());				
		}		
		return map;
	 }

} //ExtensionMapImpl
