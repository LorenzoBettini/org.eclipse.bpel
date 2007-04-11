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
package org.eclipse.bpel.validator.model;

/**
 * Java JDK dependencies only please ...
 */

import java.text.MessageFormat;
import java.util.Map;
import java.util.TreeMap;

/**
 * An implementation of IProblem. 
 * 
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Sep 18, 2006
 *
 */

public class Problem implements IProblem {

	/** A counter to indicate the sequence no of the problem */
	static int ID = 1;
	
	/** The attributes of the problem, described in IProblem */	
	Map<String,Object> mMap = new TreeMap<String,Object>();
	
	/** When we were created */
	final long  mCreated = System.currentTimeMillis();

	/** Our ID */	
	final long mId = ID++ ;

	/** 
	 * @see org.eclipse.bpel.validator.model.IProblem#getAttribute(java.lang.String)
	 */
	
	@SuppressWarnings("unchecked")
	public <T> T getAttribute (String attributeName) {
		Object value = mMap.get(attributeName);
		return (T) value;	
	}
	
	
	/**
	 * @see org.eclipse.bpel.validator.model.IProblem#getAttribute(java.lang.String, java.lang.Object)
	 */
	public <T> T getAttribute(String attributeName, T defaultValue) {
		T value = getAttribute(attributeName);
		return (value == null ? defaultValue : value );
	} 

	/** (non-Javadoc)
	 * @see org.eclipse.bpel.validator.model.IProblem#setAttribute(java.lang.String, java.lang.Object)
	 */
	public <T> void setAttribute(String attributeName, T value) {
		mMap.put(attributeName,value);		
	}
	
	
	/** (non-Javadoc)
	 * @see org.eclipse.bpel.validator.model.IProblem#setAttributes(java.util.Map)
	 */
	public void setAttributes(Map<String, Object> attributes) {
		mMap.clear();
		mMap.putAll( attributes );		
	}
	

	/**
	 * 
	 * @see org.eclipse.bpel.validator.model.IProblem#getAttributes()
	 */	
	public Map<String,Object> getAttributes() {
		return mMap;
	}

	/**
	 * @see org.eclipse.bpel.validator.model.IProblem#getCreationTime()
	 */
	public long getCreationTime() {
		return mCreated;
	}

	/**
	 * @see org.eclipse.bpel.validator.model.IProblem#getId()
	 */
	public long getId() {
		return mId;
	}

	/**
	 * Read all the information regarding the message into the problem
	 *  
	 * @param msgId the message id
	 * @param args the arguments
	 */
	
	public void fill ( String msgId, Object ... args) {
				
		String key = msgId;
		
		for(int i=0; i < args.length; i++) {
			args[i] = asArg (args[i]) ;
		}
		
		setAttribute(IProblem.MESSAGE_ID,   msgId);
		setAttribute(IProblem.MESSAGE_ARGS, args );
		
		String msgDef = Messages.missingKey(key);
		String msg = getMessage(key,msgDef);
				
		try {
			setAttribute (IProblem.MESSAGE, MessageFormat.format(msg, args) );			
		} catch (Throwable t) {
			setAttribute (IProblem.MESSAGE, MessageFormat.format(msgDef , t , t.getCause() ) );
		}
		
		key = msgId + ".fix"; //$NON-NLS-1$
		
		// Fix if any.
		msg = getMessage ( key, null );
		
		if (msg != null) {
			try {
				setAttribute (IProblem.FIX, MessageFormat.format(msg, args) );
			} catch (Throwable t) {
				setAttribute (IProblem.FIX, MessageFormat.format(Messages.missingKey(key), t, t.getCause() ) );
			}					
		}				
		// Error Code ?		
	}
	
	
	@SuppressWarnings("nls")
	Object asArg (Object o) {
		if (o == null) {
			return null;
		}
		
		if (o.getClass().isArray()) {			
			StringBuilder sb = new StringBuilder ("{");
			Object oa[] = (Object[]) o;
			for(int j=0; j < oa.length; j++) {
				sb.append(oa[j]);
				if (j+1 < oa.length) {
					sb.append(",");
				}
			}
			sb.append("}");
			return sb.toString();
		} else if (o.getClass() == String.class) {
			String v = (String) o;
			if (v.startsWith("text.")) {
				return getMessage(v, v);
			}			
		}
		
		return o ;
	}
	
	/**
	 * Return the message to push into the IProblem. We consult the 
	 * messages.properties located in current package.
	 * 
	 * @param key
	 * @return the message
	 */
	
	@SuppressWarnings("nls")
	String getMessage ( String key , String def ) {
		
		Class<?> clazz = getAttribute( BUNDLE_CLAZZ );
		
		while (clazz != null && clazz != Object.class) {		 
			Messages msg = Messages.getMessages( clazz.getPackage().getName() + ".messages" );
			if (msg.containsKey(key)) {
				return msg.get(key);
			}
			clazz = clazz.getSuperclass();			
		}
		return def;		
	}
	
}
