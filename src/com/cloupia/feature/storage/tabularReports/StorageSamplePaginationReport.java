package com.cloupia.feature.storage.tabularReports;

import com.cloupia.feature.storage.account.StorageDeviceInfo;
import com.cloupia.feature.storage.constants.StorageConstants;
import com.cloupia.feature.storage.reportActions.StorageSampleAction;
import com.cloupia.model.cIM.DynReportContext;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.model.cIM.ReportDefinition;
import com.cloupia.service.cIM.inframgr.reportengine.ContextMapRule;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReportAction;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReportWithActions;
import com.cloupia.service.cIM.inframgr.reports.simplified.actions.DrillDownAction;

public class StorageSamplePaginationReport extends CloupiaReportWithActions{
	
	private static final String NAME = "storageModule.sample.report";
	private static final String LABEL = "Storage Account Sample";
	
	
	public StorageSamplePaginationReport() {
		super();
		//IMPORTANT: this tells the framework which column of this report you want to pass as the report context id
		//when there is a UI action being launched in this report
		this.setMgmtColumnIndex(0);
		this.setMgmtDisplayColumnIndex(0);
	}

	/**
	 * Provide implementation for report actions
	 * @return returns array of actions defined for the report
	 */
	@Override
	public CloupiaReportAction[] getActions() {
		CloupiaReportAction[] actions = new CloupiaReportAction[2];
		
		actions[0] = new StorageSampleAction();
		actions[1] = new DrillDownAction();
		return actions;
	}
	
	/**
	 * Provide report implementation class
	 * @return returns report implementation class
	 */
	@Override
	public Class getImplementationClass() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Provide report label to be visible in UI
	 * @return returns report label
	 */
	@Override
	public String getReportLabel() {
		// TODO Auto-generated method stub
		return LABEL;
	}
	/**
	 * Unique report name used internally in UCSD
	 * @return returns report name
	 */
	@Override
	public String getReportName() {
		// TODO Auto-generated method stub
		return NAME;
	}
	/**
	 * Defines report implementation approach. Returns true if easy report, i.e pojo based
	 * @return returns true if easy report
	 */
	@Override
	public boolean isEasyReport() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Defines leaf/drillable report type
	 * @return returns true if leaf reoprt
	 */
	@Override
	public boolean isLeafReport() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * This method is used to get the Model class for the Pagination support
	 * @return Class This returns Paginated Report Model class
	 */
	@Override
	public Class getPaginationModelClass() {
		// TODO Auto-generated method stub
		return StorageDeviceInfo.class;
	}

	
	/** This method is used to get the Pagination Handler class for the Pagination support
	 * @return Class This returns Pagination handler class
	 */
	@Override
	public Class getPaginationProvider() {
		// TODO Auto-generated method stub
		return SamplePaginationProvider.class;
	}

	
	/** This method is used to check whether the report is Pagination or not.
	 * @return boolean This returns true(if it is pagination supported report)
	 */
	@Override
	public boolean isPaginated() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/** This method is used to get ReportHint type.
	 * If the report type  is pagination,explicitly we have to specify the return type as ReportDefinition.REPORT_HINT_PAGINATED_TABLE.
	 * @return int This returns PaginationHint 
	 */
	@Override
	public int getReportHint(){
		return ReportDefinition.REPORT_HINT_PAGINATED_TABLE;
	}
	
	/**
	 * Defines under which menu the report should be shown in UI
	 * @return returns menu Id
	 */
	@Override
	public int getMenuID() {
		return StorageConstants.PHYSICAL_STORAGE_MENU;
	}
	
	/**
	 * Defines the report context map rule
	 * @return returns map rule
	 */
	@Override
	public ContextMapRule[] getMapRules() {
		//i'm using an autogenerated report context (which I registered in FooModule), as mentioned in documentation
		//the type may vary depending on deployments, so the safest way to retrieve the auto generated type value
		//is to use the getContextByName api!
		DynReportContext dummyContextOneType = ReportContextRegistry.getInstance().getContextByName(StorageConstants.INFRA_ACCOUNT_TYPE);
		ContextMapRule rule = new ContextMapRule();
		rule.setContextName(dummyContextOneType.getId());
		rule.setContextType(dummyContextOneType.getType());
		
		ContextMapRule[] rules = new ContextMapRule[1];
		rules[0] = rule;
		
		return rules;
	}

}
