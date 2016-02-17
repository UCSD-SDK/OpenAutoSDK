package com.cloupia.feature.storage.account;

import org.apache.log4j.Logger;

import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.CredentialStore;
import com.cloupia.lib.connector.account.PhysicalConnectivityStatus;
import com.cloupia.lib.connector.account.PhysicalConnectivityTestHandler;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;

public class StorageAccountTestConnectionHandler extends
		PhysicalConnectivityTestHandler {
	static Logger logger = Logger.getLogger(StorageAccountTestConnectionHandler.class);

	@Override
	public PhysicalConnectivityStatus testConnection(String accountName)
			throws Exception {
		
		CredentialStore<StorageInfraAccount> store = CredentialStore
				.getStore(StorageInfraAccount.class);
		StorageInfraAccount acc = store.getCredential(accountName);

		PhysicalInfraAccount infraAccount = AccountUtil.getAccountByName(accountName);
		
		PhysicalConnectivityStatus retStatus = new PhysicalConnectivityStatus(infraAccount);
		
		try {
				retStatus.setConnectionOK(true);
			} catch (Exception e) {
					retStatus.setConnectionOK(false);
					logger.error("Not able to connect Storage  Device:"
							+ acc.getServerAddress(), e);
			}
		
		return retStatus;
	}

}
