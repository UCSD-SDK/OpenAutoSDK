package com.cloupia.feature.storage.tabularReports;

import java.util.List;

import org.apache.log4j.Logger;

import com.cloupia.fw.scheduler.SystemTaskEntry;
import com.cloupia.fw.scheduler.SystemTaskRegistry;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.TabularReport;
import com.cloupia.service.cIM.inframgr.SystemStateEntry;
import com.cloupia.service.cIM.inframgr.SystemTaskUtil;
import com.cloupia.service.cIM.inframgr.TabularReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;
import com.cloupia.service.cIM.inframgr.reports.TabularReportInternalModel;

public class StorageAccountSystemTaskReportImpl implements TabularReportGeneratorIf{
	
	private static Logger logger = Logger.getLogger(StorageAccountSystemTaskReportImpl.class);
	
	/**
	 * Provide Tabular report implementation
	 * @return returns tabular report
	 */
	@Override
	public TabularReport getTabularReportReport(ReportRegistryEntry reportEntry,
			ReportContext context) throws Exception {
		// TODO Auto-generated method stub
		TabularReport report = new TabularReport();

		report.setGeneratedTime(System.currentTimeMillis());
		report.setReportName(reportEntry.getReportLabel());
		report.setContext(context);
		
		String accountName = "";
		String contextId = context.getId();
		logger.info("###### Context at System Task report#####: "+contextId);
		if(contextId != null)
			//As the contextId returns as: "account Name;POD Name"
			accountName = contextId.split(";")[0];
		
		TabularReportInternalModel model = new TabularReportInternalModel();

        model.addTextColumn("Name", "Name given to Task",true); 
        model.addTextColumn("Label", "Label given to Task");
        model.addTextColumn("Category", "Category of the Task");
        model.addTextColumn("Enabled", "Enabled");
       // model.addTextColumn("Frequency", "Frequency");
        model.addTextColumn("Execution Node Name", "Execution Node Name");
        model.addTextColumn("Execution Node Network Name", "Execution Node Network Name");
        model.addLongTextColumn("Execution Status", "Execution Status");    
        //model.addTextColumn("Execution Duration", "Execution Duration");
        //model.addTimeColumn("Start Time", "Start Time");
        model.addTimeColumn("Last Executed Time", "Last Executed Time");        
        model.addTimeColumn("Next Execution Time", "Next Execution Time"); 
        
        model.completedHeader();
        
        displayReport(model, report, accountName);
		
		return report;
	}

	private void displayReport(TabularReportInternalModel model,
			TabularReport report, String accountName) throws Exception {
		// TODO Auto-generated method stub
		List<SystemStateEntry> entries = SystemTaskUtil.getAllSystemStateEntriesByAccName(accountName);
		if(!entries.isEmpty()){
			for (SystemStateEntry entry : entries)
	    	{
				if (entry.getProperty()!=null && entry.getProperty().startsWith("task"))
	    		{
					String taskName = entry.getProperty();    			
	    	    	if (taskName.contains(".")) {
	    	    		taskName = taskName.substring(taskName.indexOf(".") + 1);
	    	    	}
	    	    	
					String category = getCategory(taskName);
					
					//ScheduleTask task = SystemScheduler.getInstance().getTaskByName(taskName);
					
					model.addTextValue(taskName);
					model.addTextValue(entry.getLabel());    	    
			    	model.addTextValue(category);
			    	
			    	String status = (entry != null) ? (entry.isDisabled() ? "Disabled" : "Enabled") : "NA";
			    	model.addTextValue(status);
			    	
			    	//model.addTextValue(getFrequencyAsString(task.getFrequency(), entry.getFrequency()));
			    	model.addTextValue(entry.getNodeName());
		    		model.addTextValue(entry.getAgentIpAddress());
		    		model.addLongTextValue(entry.getValue());
		    		//model.addTextValue("");
		    		model.addTimeValue(entry.getStartTime());
		        	model.addTimeValue(entry.getEndTime());
		        	
		        	
		        	model.completedRow();
					
					
	    		}
	    	}
		}
		model.updateReport(report);
		
	}

	private String getCategory(String taskName) {
		// TODO Auto-generated method stub
		String category = null;
		SystemTaskEntry taskDef = 
			SystemTaskRegistry.getInstance().getTaskEntry(taskName);
    	if (taskDef != null) {
    		category = taskDef.getCategory();
    	}
    	return category;
	}

}
