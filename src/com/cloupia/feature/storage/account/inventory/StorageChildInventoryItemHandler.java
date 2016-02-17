package com.cloupia.feature.storage.account.inventory;

import org.apache.log4j.Logger;

import com.cloupia.lib.connector.AbstractInventoryItemHandler;
import com.cloupia.lib.connector.InventoryContext;

public class StorageChildInventoryItemHandler extends AbstractInventoryItemHandler{
	private static Logger logger = Logger.getLogger(StorageChildInventoryItemHandler.class);

	@Override
	public void cleanup(String accountName) throws Exception {
		logger.debug("At StorageChildInventoryItemHandler clean up method");

	}

	@Override
	public void doInventory(String accountName, InventoryContext context)
			throws Exception {
		logger.debug("At StorageChildInventoryItemHandler doInventory method");

	}

	@Override
	public void doInventory(String accountName, Object object) throws Exception {
		// TODO Auto-generated method stub

	}

}
