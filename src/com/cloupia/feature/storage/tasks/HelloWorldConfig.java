package com.cloupia.feature.storage.tasks;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.feature.storage.constants.StorageConstants;
import com.cloupia.feature.storage.wfInputTypeProvider.SampleTabularProvider;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.customactions.UserInputField;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormController;
import com.cloupia.service.cIM.inframgr.forms.wizard.FormField;

@FormController(value = "com.cloupia.feature.storage.tasks.HelloWorldConfigController")
@PersistenceCapable(detachable = "true", table = "storageModule_helloworldconfig")
public class HelloWorldConfig implements TaskConfigIf {

	public static final String displayLabel = "HelloWorldConfig";
	@Persistent
	private long configEntryId;
	@Persistent
	private long actionId;

	@FormField(label = "Email Id", help = "Provide your mail id", mandatory = true, rbid = "storageModule.form.HelloWorldConfig.email.field")
	@UserInputField(type=StorageConstants.EMAIL_INPUT_TYPE)
	@Persistent
	private String             email;
	
	@FormField(label = "Tabular Input", help = "tabular field input", mandatory = true,
            type = FormFieldDefinition.FIELD_TYPE_TABULAR_POPUP, table = SampleTabularProvider.SAMPLE_TABULAR_PROVIDER)
	@Persistent
	private String tabField;
	
	@FormField(label = "Initiator Type", help = "Initiator Type", mandatory = true,
            validate = true, type = FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV)
    @Persistent
    private String initiatorType;
	
	@FormField(label = "Initiator", help = "Initiator", mandatory = true,
            type = FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV)
    @Persistent
    private String initiator;
	
	
	public String getTabField() {
		return tabField;
	}

	public void setTabField(String tabField) {
		this.tabField = tabField;
	}

	public String getInitiatorType() {
		return initiatorType;
	}

	public void setInitiatorType(String initiatorType) {
		this.initiatorType = initiatorType;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
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
