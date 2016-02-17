package com.cloupia.feature.storage;

import org.apache.log4j.Logger;

import com.cloupia.feature.storage.account.StorageAccountTestConnectionHandler;
import com.cloupia.feature.storage.account.StorageConvergedStackBuilder;
import com.cloupia.feature.storage.account.StorageDeviceInfo;
import com.cloupia.feature.storage.account.StorageInfraAccount;
import com.cloupia.feature.storage.account.StorageInventoryListener;
import com.cloupia.feature.storage.account.inventory.StorageRootInventoryItemHandler;
import com.cloupia.feature.storage.constants.StorageConstants;
import com.cloupia.feature.storage.menuProvider.SampleMenuProvider;
import com.cloupia.feature.storage.nonTabularReports.BarChartReport;
import com.cloupia.feature.storage.nonTabularReports.FormReport;
import com.cloupia.feature.storage.nonTabularReports.HeatmapReport;
import com.cloupia.feature.storage.nonTabularReports.LineChartReport;
import com.cloupia.feature.storage.nonTabularReports.PieChartReport;
import com.cloupia.feature.storage.tabularReports.StorageSampleEasyReport;
import com.cloupia.feature.storage.tabularReports.StorageSamplePaginationReport;
import com.cloupia.feature.storage.tabularReports.StorageSampleSystemTaskReport;
import com.cloupia.feature.storage.tasks.HelloWorldTask;
import com.cloupia.feature.storage.tasks.RollbackHelloWorldTask;
import com.cloupia.feature.storage.wfInputTypeProvider.EmailIdValidator;
import com.cloupia.feature.storage.wfInputTypeProvider.SampleLovProvider;
import com.cloupia.feature.storage.wfInputTypeProvider.SampleTabularProvider;
import com.cloupia.lib.connector.ConfigItemDef;
import com.cloupia.lib.connector.account.AccountTypeEntry;
import com.cloupia.lib.connector.account.PhysicalAccountTypeManager;
import com.cloupia.model.cIM.FormFieldDefinition;
import com.cloupia.model.cIM.InfraAccountTypes;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.service.cIM.inframgr.AbstractCloupiaModule;
import com.cloupia.service.cIM.inframgr.AbstractTask;
import com.cloupia.service.cIM.inframgr.CustomFeatureRegistry;
import com.cloupia.service.cIM.inframgr.collector.controller.CollectorFactory;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaReport;

public class StorageModule extends AbstractCloupiaModule{
	private static Logger logger = Logger.getLogger(StorageModule.class);
	
	@Deprecated
	@Override
	public CollectorFactory[] getCollectors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CloupiaReport[] getReports() {
		CloupiaReport[] reports = new CloupiaReport[8];	
		
		//registering tabular report
		reports[0] = new StorageSamplePaginationReport();
		reports[1] = new StorageSampleEasyReport("storageModule.sample.easy.report", "Sample Easy Report", StorageDeviceInfo.class);
		reports[2] = new StorageSampleSystemTaskReport();
		
		//registering non-tabular report
		reports[3] = new PieChartReport();
		reports[4] = new LineChartReport();
		reports[5] = new BarChartReport();
		reports[6] = new HeatmapReport();
		reports[7] = new FormReport();
		
		
		return reports;
	}

	@Override
	public AbstractTask[] getTasks() {
		// TODO Auto-generated method stub
		AbstractTask[] tasks = new AbstractTask[2];
		
		tasks[0] = new HelloWorldTask();
		tasks[1] = new RollbackHelloWorldTask();
		
		return tasks;
	}

	@Override
	public void onStart(CustomFeatureRegistry cfr) {
		
		 try {
			 	// registering context for infraAccount
				ReportContextRegistry.getInstance().register(StorageConstants.INFRA_ACCOUNT_TYPE, StorageConstants.INFRA_ACCOUNT_LABEL);
				
				//registering context for StorageSamplePaginationReport. Need to be assign as map rule for child report
				ReportContextRegistry.getInstance().register(StorageConstants.SAMPLE_REPORT_CONTEXT_TYPE, StorageConstants.SAMPLE_REPORT_CONTEXT_LABEL);
				
				//registering context for sample menu
				ReportContextRegistry.getInstance().register(StorageConstants.SAMPLE_MENU_CONTEXT_TYPE, StorageConstants.SAMPLE_MENU_CONTEXT_LABEL);
				
				//registering LOV provider
				cfr.registerLovProviders(SampleLovProvider.SAMPLE_LOV_PROVIDER, new SampleLovProvider());
				
				//registering TabularReport input provider
				cfr.registerTabularField(SampleTabularProvider.SAMPLE_TABULAR_PROVIDER, SampleTabularProvider.class, "0", "0");
				
				//registering workflow input type. You can use the registered input provider with workflows
				cfr.registerWorkflowInputFieldType(StorageConstants.SAMPLE_LOV_PROVIDER_TYPE, StorageConstants.SAMPLE_LOV_PROVIDER_TYPE_LABEL, 
						FormFieldDefinition.FIELD_TYPE_EMBEDDED_LOV, SampleLovProvider.SAMPLE_LOV_PROVIDER);
				cfr.registerWorkflowInputFieldType(StorageConstants.SAMPLE_TABULAR_PROVIDER_TYPE, StorageConstants.SAMPLE_TABULAR_PROVIDER_TYPE_LABEL, 
						FormFieldDefinition.FIELD_TYPE_TABULAR_POPUP, SampleTabularProvider.SAMPLE_TABULAR_PROVIDER);
				
				//registering workflow input type as multi select. You can use the registered input provider
				cfr.registerWorkflowInputFieldType(StorageConstants.SAMPLE_MULTISELECT_TABULAR_PROVIDER_TYPE, StorageConstants.SAMPLE_MULTISELECT_TABULAR_PROVIDER_LABEL,
						FormFieldDefinition.FIELD_TYPE_TABULAR_POPUP, SampleTabularProvider.SAMPLE_TABULAR_PROVIDER, true, null);
				
				//registering workflow input type with input validator
				cfr.registerWorkflowInputFieldType(StorageConstants.EMAIL_INPUT_TYPE, StorageConstants.EMAIL_INPUT_LABEL,
						FormFieldDefinition.FIELD_TYPE_TEXT, null, false, new EmailIdValidator());
				
				//registering sample menu
				new SampleMenuProvider().registerWithProvider();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		createAccountEntry();	
	}
	
	private void createAccountEntry() {
		
		AccountTypeEntry entry = new AccountTypeEntry();
		
		entry.setCredentialClass(StorageInfraAccount.class);
		entry.setAccountType(StorageConstants.INFRA_ACCOUNT_TYPE);
		entry.setAccountLabel(StorageConstants.INFRA_ACCOUNT_LABEL);
		entry.setCategory(InfraAccountTypes.CAT_STORAGE);
		entry.setContextType(ReportContextRegistry.getInstance().getContextByName(StorageConstants.INFRA_ACCOUNT_TYPE).getType());
		entry.setAccountClass(AccountTypeEntry.PHYSICAL_ACCOUNT);
		entry.setInventoryTaskPrefix("OAStorageTask");
		entry.setWorkflowTaskCategory("Storage Tasks");
		//entry.setCollectorFactory(new StorageAccountCollectorFactory());
		entry.setInventoryFrequencyInMins(15);
		entry.setPodTypes(new String[] { "FlexPod" });
		entry.setTestConnectionHandler(new StorageAccountTestConnectionHandler());
		entry.setInventoryListener(new StorageInventoryListener());
		entry.setConvergedStackComponentBuilder(new StorageConvergedStackBuilder());
		entry.setIconPath("/app/uploads/openauto/dummy_storage_logo.png");
		
		try {

			// Adding inventory
			registerInventoryObjects(entry);
			
			logger.info("Registering OA Storage Module account entry type");
			PhysicalAccountTypeManager.getInstance().addNewAccountType(entry);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void registerInventoryObjects(AccountTypeEntry entry) {
		logger.info("Registering the invetory objects in the OA Storage Module");
		
		ConfigItemDef rootInfo = entry.createInventoryRoot("oa.storage.root.item", StorageRootInventoryItemHandler.class);
		//rootInfo.addChild("oa.storage.child.item", StorageChildInventoryItemHandler.class);
		
	}

}
