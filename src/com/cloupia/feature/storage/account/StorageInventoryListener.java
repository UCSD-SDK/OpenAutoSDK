package com.cloupia.feature.storage.account;

import org.apache.log4j.Logger;

import com.cloupia.lib.connector.InventoryContext;
import com.cloupia.lib.connector.InventoryEventListener;

public class StorageInventoryListener implements InventoryEventListener {
	
	private static Logger logger = Logger.getLogger(StorageInventoryListener.class);

	@Override
	public void afterInventoryDone(String arg0, InventoryContext arg1)
			throws Exception {
		logger.debug("Call in StorageInventoryListener afterInventoryDone ");

	}

	@Override
	public void beforeInventoryStart(String arg0, InventoryContext arg1)
			throws Exception {
		logger.debug("Call in StorageInventoryListener beforeInventoryStart ");

	}

}
