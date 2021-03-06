package com.cloupia.feature.storage.tabularReports;

import com.cloupia.feature.storage.constants.StorageConstants;
import com.cloupia.feature.storage.reportActions.StorageAccountSystemTaskRunAction;
import com.cloupia.model.cIM.DynReportContext;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.service.cIM.inframgr.reportengine.ContextMapRule;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReportAction;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReportWithActions;

public class StorageSampleSystemTaskReport extends CloupiaReportWithActions {
	
	private static final String NAME = "storageModule.system.task.report";
	private static final String LABEL = "Storage Account System Tasks";
	
	public StorageSampleSystemTaskReport(){
		super();
		this.setMgmtColumnIndex(0);
	}

	/**
	 * Provide implementation for report actions
	 * @return returns array of actions defined for the report
	 */
	@Override
	public CloupiaReportAction[] getActions() {
		// TODO Auto-generated method stub
		CloupiaReportAction[] actions = new CloupiaReportAction[1];
		
		actions[0] = new StorageAccountSystemTaskRunAction();
		return actions;
	}
	/**
	 * Provide report implementation class
	 * @return returns report implementation class
	 */
	@Override
	public Class getImplementationClass() {
		// TODO Auto-generated method stub
		return StorageAccountSystemTaskReportImpl.class;
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
		return true;
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
