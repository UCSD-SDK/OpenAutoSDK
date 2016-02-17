package com.cloupia.feature.storage.account;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cloupia.feature.storage.constants.StorageConstants;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;
import com.cloupia.model.cIM.ConvergedStackComponentDetail;
import com.cloupia.model.cIM.ReportContextRegistry;
import com.cloupia.service.cIM.inframgr.reports.contextresolve.ConvergedStackComponentBuilderIf;

public class StorageConvergedStackBuilder implements
ConvergedStackComponentBuilderIf {

	private static Logger logger = Logger.getLogger(StorageConvergedStackBuilder.class);

	@Override
	public ConvergedStackComponentDetail buildConvergedStackComponent(
			String accountIdentity) throws Exception {
		logger.info("######Context at converged view: #######"+ accountIdentity);
		ConvergedStackComponentDetail detail = new ConvergedStackComponentDetail();
		
		try{
			String[] arr = accountIdentity.split(";");

			PhysicalInfraAccount account = AccountUtil.getAccountByName(arr[0]);
			

			if(account != null)
			{
				logger.info("AccountName:" + account.getAccountName());
				detail.setModel(account.getHwModel());
				detail.setOsVersion(account.getSwVersion());
				detail.setVendorLogoUrl("/app/uploads/openauto/dummy_logo.png");
				detail.setMgmtIPAddr(account.getServerAddress());
				detail.setStatus(account.isReachable() ? "OK" : "Down");
				detail.setVendorName("Cisco");
				detail.setContextType(ReportContextRegistry.getInstance().getContextByName(StorageConstants.INFRA_ACCOUNT_TYPE).getType());
				detail.setContextValue(accountIdentity);
				
				String filerIdentity = account.getServerAddress() + "@" + account.getAccountName() + ";" + account.getPodName();
				detail.setId(filerIdentity);
				
				List<String> storageSummaryList = new ArrayList<String>();
				storageSummaryList.add("Account Name,"+account.getAccountName());
				storageSummaryList.add("POD Name,"+account.getPodName());
				
				detail.setComponentSummaryList(storageSummaryList);

			}else{
            	detail.setStatus("Down");
            }  
			
		}catch(Exception e){
			logger.info("Error while building status for IBM Storage device for Converged device.",
	                e);
		}
		
		return detail;
	}

}
