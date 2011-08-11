package org.eclipse.bpel.jboss.riftsaw.runtime;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jst.server.generic.core.internal.GenericServerRuntime;
import org.eclipse.bpel.runtimes.IBPELRuntimeDelegate;

@SuppressWarnings("restriction")
public class RiftsawServerRuntime extends GenericServerRuntime implements IBPELRuntimeDelegate {

	public String getServerAddress() {
		return "localhost";
	}
	
	public String getPort()
	{
		Map m = getAttribute("generic_server_attributes", new HashMap());
		return (String)m.get("port");
	}

	public String getDeployDir() {
		return "";
	}
}
