package com.cloupia.feature.storage.tasks;

import com.cloupia.model.cIM.FormLOVPair;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.TabularReport;
import com.cloupia.service.cIM.inframgr.CustomFeatureRegistry;
import com.cloupia.service.cIM.inframgr.TabularReportGeneratorIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.AbstractObjectUIController;
import com.cloupia.service.cIM.inframgr.forms.wizard.Page;
import com.cloupia.service.cIM.inframgr.forms.wizard.TabularFieldRegistry;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;
import com.cloupia.service.cIM.inframgr.reports.TabularReportInternalModel;

public class HelloWorldConfigController extends AbstractObjectUIController {
	
	//Required for dynamic input field population
	@Override
    public void beforeMarshall(Page page, String id, ReportContext context, Object pojo) throws Exception
    {
		HelloWorldConfig config = (HelloWorldConfig) pojo;
		
		FormLOVPair[] initiator = new FormLOVPair[2];
		initiator[0] = new FormLOVPair("Groups", "Groups");
		initiator[1] = new FormLOVPair("Users", "Users");
		page.setEmbeddedLOVs(id + ".initiatorType", initiator);
		
		if ((config.getInitiatorType() == null) || (config.getInitiatorType().trim().length() == 0))
        {
            config.setInitiatorType(initiator[0].getValue());
            page.setValue(id + ".initiatorType", initiator[0].getValue());
        }
		
        String initiatorType = "";

        if (page.isValidateSet(id + ".initiatorType"))
        {
        	initiatorType = page.getValue(id + ".initiatorType");
        }else{
        	initiatorType = config.getInitiatorType();
        }
        
        FormLOVPair[] groups = new FormLOVPair[2];
        FormLOVPair[] users = new FormLOVPair[2];
        
        groups[0] = new FormLOVPair("Group1", "Group1");
        groups[1] = new FormLOVPair("Group2", "Group2");
        
        users[0] = new FormLOVPair("User1", "User1");
        users[1] = new FormLOVPair("User2", "User2");
        
        if(initiatorType.equals("Groups")){
        	page.setEmbeddedLOVs(id + ".initiator", groups);
        }else if(initiatorType.equals("Users")){
        	page.setEmbeddedLOVs(id + ".initiator", users);
        }
        
       
        Class<?> reportImpl = createDynamicReport();
        CustomFeatureRegistry.getInstance().registerTabularField("TestTabularField", reportImpl, "0", "0");
        
        page.setTabularPopupField(id + ".tabField", "TestTabularField", "0", "0");
        
    }

	private Class<?> createDynamicReport() {
		TabularReportGeneratorIf reportClass = new TabularReportGeneratorIf(){

			@Override
			public TabularReport getTabularReportReport(
					ReportRegistryEntry reportEntry, ReportContext context)
					throws Exception {
				TabularReport report = new TabularReport();

	            report.setGeneratedTime(System.currentTimeMillis());
	            report.setReportName(reportEntry.getReportLabel());
	            report.setContext(context);
	           
	            TabularReportInternalModel model = new TabularReportInternalModel();

	            model.addTextColumn("Snapshot Name", "Snapshot Name");
	            model.completedHeader();
	            model.addTextValue("aaaa");
	            model.completedRow();
	            model.addTextValue("bbbb");
	            model.completedRow();

	            model.updateReport(report);
				return report;
			}
			
		};
		
		return reportClass.getClass();
	}
	
	//Required for providing field validation
	/*@Override
    public void afterUnmarshall(Page page, String id, ReportContext context, Object pojo) throws Exception
    {
		HelloWorldConfig config = (HelloWorldConfig) pojo;
		boolean check = validate(config.getEmail());
		
		if(!check){
			//to provide error message wrt to field. Here email should be the field name
			page.setError(id + ".email", "Please enter a valid mail id");
			
			//to display page level error message
            page.setPageMessage("Please fix the errors to proceed.");
			
		}
    }
	
	private boolean validate(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	//Required for providing field show/hide
	 @Override
	 public void afterMarshall(Page page, String id, ReportContext context, Object pojo) throws Exception
	 {
		 
	 }*/
	
}
