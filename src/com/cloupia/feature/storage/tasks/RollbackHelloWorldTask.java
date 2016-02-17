package com.cloupia.feature.storage.tasks;

import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;


public class RollbackHelloWorldTask extends AbstractTask {

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {
		long configEntryId = context.getConfigEntry().getConfigEntryId();
		//retrieving the corresponding config object for this handler
		RollbackHelloWorldConfig config = (RollbackHelloWorldConfig) context.loadConfigObject();

		if (config == null)
		{
			throw new Exception("No hello world configuration found for custom action " + context.getActionDef().getName()
					+ " entryId " + configEntryId);
		}

		actionLogger.addInfo("We are rolling back anything done in the Hello World Task for: " + config.getEmail());
		actionLogger.addInfo("the task name is: " + this.getTaskName());
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		return new RollbackHelloWorldConfig();
	}

	@Override
	public String getTaskName() {
		return RollbackHelloWorldConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		return null;
	}

}
