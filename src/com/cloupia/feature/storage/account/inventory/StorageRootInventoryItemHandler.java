package com.cloupia.feature.storage.account.inventory;

import org.apache.log4j.Logger;

import com.cloupia.feature.storage.account.StorageDeviceInfo;
import com.cloupia.fw.objstore.ObjStore;
import com.cloupia.fw.objstore.ObjStoreHelper;
import com.cloupia.lib.connector.AbstractInventoryItemHandler;
import com.cloupia.lib.connector.InventoryContext;
import com.cloupia.lib.connector.account.AccountUtil;
import com.cloupia.lib.connector.account.PhysicalInfraAccount;

public class StorageRootInventoryItemHandler extends AbstractInventoryItemHandler{
	private static Logger logger = Logger.getLogger(StorageRootInventoryItemHandler.class);

	@Override
	public void cleanup(String accountName) throws Exception {
		logger.debug("At StorageRootInventoryItemHandler clean up method");
		
		try {
            ObjStore<StorageDeviceInfo> store = ObjStoreHelper.getStore(StorageDeviceInfo.class);
            String query = "accountName == '" + accountName + "'";
            store.delete(query);
        } catch(Exception e)
        {
            throw new Exception("Exception while deleting root inventory " + e.getMessage());
        }
		
	}

	@Override
	public void doInventory(String accountName, InventoryContext context)
			throws Exception {
		logger.debug("At StorageRootInventoryItemHandler do Inventory method");

		try {
			
			PhysicalInfraAccount account = AccountUtil.getAccountByName(accountName);
			
            ObjStore<StorageDeviceInfo> store = ObjStoreHelper.getStore(StorageDeviceInfo.class);
            
            StorageDeviceInfo obj = new StorageDeviceInfo();
    		obj.setAccountName(account.getAccountName());
    		obj.setDeviceIp(account.getServerAddress());
    		obj.setStatus(account.isReachable() ? "OK" : "Down");
    		
    		store.insert(obj);
        } catch(Exception e)
        {
            throw new Exception("Exception while deleting root inventory " + e.getMessage());
        }
		
	}

	@Override
	public void doInventory(String accountName, Object object) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
