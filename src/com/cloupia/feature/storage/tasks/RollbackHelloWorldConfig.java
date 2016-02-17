package com.cloupia.feature.storage.tasks;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.feature.storage.constants.StorageConstants;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.customactions.UserInputField;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

@PersistenceCapable(detachable = "true", table = "storageModule_rollbackhelloworldconfig")
public class RollbackHelloWorldConfig implements TaskConfigIf {

	public static final String displayLabel = "RollbackHelloWorldConfig";
	@Persistent
	private long configEntryId;
	@Persistent
	private long actionId;

	//This field is supposed to consume output from the EmailDatacentersTask, you'll see the
	//type in user input field below matches the output type in EmailDatacentersTask's output definition.
	@FormField(label = "Email Id", help = "Provide your mail id here", mandatory = true)
	@UserInputField(type = StorageConstants.FOO_HELLO_WORLD_EMAIL_TYPE)
	@Persistent
	private String             email;

	public RollbackHelloWorldConfig() {

	}

	public RollbackHelloWorldConfig(String email) {
		super();
		this.email = email;
	}

	@Override
	public long getActionId() {
		return actionId;
	}

	@Override
	public long getConfigEntryId() {
		return configEntryId;
	}

	@Override
	public String getDisplayLabel() {
		return displayLabel;
	}

	@Override
	public void setActionId(long actionId) {
		this.actionId = actionId;
	}

	@Override
	public void setConfigEntryId(long configEntryId) {
		this.configEntryId = configEntryId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



}
