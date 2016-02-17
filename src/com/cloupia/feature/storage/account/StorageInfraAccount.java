package com.cloupia.feature.storage.account;

import java.util.List;

import org.apache.log4j.Logger;

import com.cloupia.feature.policy.PolicyFeature;
import com.cloupia.fw.objstore.ObjStore;
import com.cloupia.fw.objstore.ObjStoreHelper;
import com.cloupia.lib.connector.account.AbstractInfraAccount;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.model.cIM.InfraAccount;
import com.cloupia.service.cIM.inframgr.collector.view2.ConnectorCredential;
import com.cloupia.service.cIM.inframgr.forms.wizard.FieldValidation;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;
import com.cloupia.service.cIM.inframgr.forms.wizard.HideFieldOnCondition;

public class StorageInfraAccount extends AbstractInfraAccount implements ConnectorCredential {

	static Logger logger = Logger.getLogger(StorageInfraAccount.class);

	@FormField(label = "Device IP", help = "Device IP", mandatory = true)
	private String deviceIp;

	@FormField(label = "Use Credential Policy", validate = true, help = "Select if you want to use policy to give the credentials.", type = FormFieldDefinition.FIELD_TYPE_BOOLEAN)
	private boolean isCredentialPolicy = false;
	
	@FormField(label = "Credential Policy", type = FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, 
			mandatory = true, popupFormId = PolicyFeature.CREDENTIAL_ADD_FORM, 
			popupFormHelpLabel = "Credential Policy Add Form")
    @HideFieldOnCondition(field = "isCredentialPolicy", op = FieldValidation.OP_EQUALS, value = "false")
    private String policy;

	@FormField(label = "Protocol", help = "Protocol", type = FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, validate = true, lov = {"http", "https" })
	@HideFieldOnCondition(field = "isCredentialPolicy", op = FieldValidation.OP_EQUALS, value = "true")
	private String protocol="http";
	
	@FormField(label = "Port", help = "Port Number", type = FormFieldDefinition.FIELD_TYPE_TEXT)
	@HideFieldOnCondition(field = "isCredentialPolicy", op = FieldValidation.OP_EQUALS, value = "true")
	private String port = "8080";
	
	@FormField(label = "Login", help = "Login")
	@HideFieldOnCondition(field = "isCredentialPolicy", op = FieldValidation.OP_EQUALS, value = "true")
	private String login;
	
	@FormField(label = "Password", help = "Password", type = FormFieldDefinition.FIELD_TYPE_PASSWORD)
	@HideFieldOnCondition(field = "isCredentialPolicy", op = FieldValidation.OP_EQUALS, value = "true")
	private String password;

	
	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	
	@Override
	public String getServerAddress() {
		// TODO Auto-generated method stub
		return getDeviceIp();
	}

	@Override
	public String getPolicy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCredentialPolicy() {
		// TODO Auto-generated method stub
		return isCredentialPolicy;
	}

	@Override
	public void setCredentialPolicy(boolean isCredentialPolicy) {
		// TODO Auto-generated method stub
		this.isCredentialPolicy = isCredentialPolicy;
	}

	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		this.password = password;
	}

	@Override
	public void setPolicy(String policy) {
		// TODO Auto-generated method stub
		this.policy = policy;
	}

	@Override
	public void setPort(int port) {
		// TODO Auto-generated method stub
		this.port = String.valueOf(port);
	}

	@Override
	public void setUsername(String login) {
		// TODO Auto-generated method stub
		this.login = login;
	}

	@Override
	public InfraAccount toInfraAccount() {
		
		InfraAccount account = null;
		try {
			ObjStore<InfraAccount> store = ObjStoreHelper.getStore(InfraAccount.class);
			String cquery = "server == '"
					+ deviceIp + "' && userID == '" + login
					+ "' && transport == '" + protocol + "' && port == "
					+ Integer.parseInt(port);
			logger.debug("query = " + cquery);

			List<InfraAccount> accList = store.query(cquery);
			if (accList != null && accList.size() > 0)
				account = accList.get(0);
			
		} catch (Exception e) {
			logger.error("Exception while mapping DeviceCredential to InfraAccount for server: "
					+ deviceIp + ": " + e.getMessage());
		}

		return account;
	}
	
	

}
