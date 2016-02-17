package com.cloupia.feature.storage.account;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.cloupia.lib.easyui.annotations.ReportField;
import com.cloupia.service.cIM.inframgr.reports.simplified.ReportableIf;

@PersistenceCapable(detachable = "true")
public class StorageDeviceInfo implements ReportableIf{
	
	@ReportField(label="Account Name")
	@Persistent
	private String accountName;
	
	@ReportField(label="Status")
	@Persistent
 	private String status;
	
	@ReportField(label="IP Address")
	@Persistent
 	private String deviceIp;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String setDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	@Override
	public String getInstanceQuery() {
		// TODO Auto-generated method stub
		return null;
	}

}
