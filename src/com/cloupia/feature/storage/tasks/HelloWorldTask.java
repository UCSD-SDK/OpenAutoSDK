package com.cloupia.feature.storage.tasks;

import com.cloupia.feature.storage.constants.StorageConstants;
import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.TaskConfigIf;
import com.cloupia.service.cIM.inframgr.TaskOutputDefinition;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionLogger;
import com.cloupia.service.cIM.inframgr.customactions.CustomActionTriggerContext;

public class HelloWorldTask extends AbstractTask{

	@Override
	public void executeCustomAction(CustomActionTriggerContext context,
			CustomActionLogger actionLogger) throws Exception {

		long configEntryId = context.getConfigEntry().getConfigEntryId();
		//retrieving the corresponding config object for this handler
		HelloWorldConfig config = (HelloWorldConfig) context.loadConfigObject();

		if (config == null)
		{
			throw new Exception("No hello world configuration found for custom action " + context.getActionDef().getName()
					+ " entryId " + configEntryId);
		}
		
		actionLogger.addInfo("Hello World: " + config.getEmail());
		actionLogger.addInfo("the task name is: " + this.getTaskName());

		//this is how you would register a roll back task, the first two values are displayed in the roll back
		//workflow UI.  the following two values are basically descriptions for what has taken place in the current task.
		//the final two values are what is important, you need to use the getTaskName() method for the task will
		//rollback with and the config to go with that task.  Usually we can define a constructor for the rollback config
		//that takes the user input so we know all the details in which the original task was executed.
		context.getChangeTracker().undoableResourceAdded("assetType", "idString", "Hello World", 
				"Hello World task", 
				new RollbackHelloWorldTask().getTaskName(), new RollbackHelloWorldConfig(config.getEmail()));


		try
		{
			context.saveOutputValue(StorageConstants.FOO_HELLO_WORLD_EMAIL_TYPE, config.getEmail());

		} catch (Exception e)
		{
			actionLogger.addWarning("Action :" + HelloWorldConfig.displayLabel + ":" + e.getMessage());
		}
	}

	@Override
	public TaskConfigIf getTaskConfigImplementation() {
		
		return new HelloWorldConfig();
	}

	@Override
	public String getTaskName() {
		
		return HelloWorldConfig.displayLabel;
	}

	@Override
	public TaskOutputDefinition[] getTaskOutputDefinitions() {
		TaskOutputDefinition[] ops = new TaskOutputDefinition[1];
		//NOTE: If you want to use the output of this task as input to another task. Then the second argument 
		//of the output definition MUST MATCH the type of UserInputField in the config of the task that will
		//be receiving this output.  Take a look at RollbackHelloWorldConfig as an example.
		ops[0] = new TaskOutputDefinition(
				StorageConstants.FOO_HELLO_WORLD_EMAIL_LABEL,
				StorageConstants.FOO_HELLO_WORLD_EMAIL_TYPE,
				"EMAIL IDs");
		return ops;
	}

}
