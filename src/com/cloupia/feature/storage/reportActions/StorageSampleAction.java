package com.cloupia.feature.storage.reportActions;

import java.net.URI;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.cloupia.model.cIM.ConfigTableAction;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.service.cIM.inframgr.FileManagementUtil;
import com.cloupia.service.cIM.inframgr.forms.wizard.Page;
import com.cloupia.service.cIM.inframgr.forms.wizard.PageIf;
import com.cloupia.service.cIM.inframgr.forms.wizard.WizardSession;
import com.cloupia.service.cIM.inframgr.reports.simplified.CloupiaPageAction;

public class StorageSampleAction extends CloupiaPageAction {
	
	private static Logger logger = Logger.getLogger(StorageSampleAction.class);
	
	private static final String formId = "storageModule.sample.form.delete";
	private static final String ACTION_ID = "storageModule.sample.action.delete";
	//this is the label show in UI for this action
	private static final String label = "Delete";

	/**
	 * @return this method return id for action field 
	 */
	@Override
	public String getActionId() {
		return ACTION_ID;
	}

	/**
	 * @return this method returns action form id 
	 */
	public String getFormId()
	{
		return formId;
	}
	
	/**
	 * @return this method  return label for action field 
	 */
	@Override
	public String getLabel() {
		return label;
	}
	
	/**
	 *@return action type for any UI actions        
	 */
	@Override
	public int getActionType() {
		
		return ConfigTableAction.ACTION_TYPE_POPUP_FORM;
	}
	/** 
	 * @return return true when a row needs to be selected for this action to proceed and false if row selection is not required
     */
	@Override
	public boolean isSelectionRequired() {
	
		return false;
	}
	/**
	 * @return return true when a double click action performed for this action to proceed and false if double click action performed not required
     */
	@Override
	public boolean isDoubleClickAction() {
		return false;
	}
	/** 
	 * @return return true when a double click a action and open a child report open  for this action to proceed and false if no child report or no click action  is required
     */
	@Override
	public boolean isDrilldownAction() {
		return false;
	}
	/**
	 * this is where you define the layout of the form page
	 * the easiest way to do this is to use this "bind" method
	 * 
	 * @param pagecontext
	 * @param reportcontext
	 */
	@Override
	public void definePage(Page page, ReportContext context) {
		
		page.bind(formId, StorageSampleForm.class);
	}
	
	 /**
     *  This method loads the form fields and field data to the page.
	 *
	 * @param pagecontext
	 * @param reportcontext
	 * @param wizardsession
     */
	@Override
	public void loadDataToPage(Page page, ReportContext context, WizardSession session) throws Exception {
		
		String contextId = context.getId();
		logger.info("Context Id for Sample Action = " + contextId);
		
		StorageSampleForm form = new StorageSampleForm();
		form.setName("This is how you set default values to fields");
		
		session.getSessionAttributes().put(formId, form);
		page.marshallFromSession(formId);
		
		String dir = FileManagementUtil.getDir(page.getSession());
	
		page.setSourceReport(formId + ".uploadFileName", dir);	
	}
	/**
	 * This method do the validation for the form fields.
	 * 
	 * @param pagecontext
	 * @param reportcontext
	 * @param wizardsession
	 * @return ststus 
	 */
	
	@Override
	public int validatePageData(Page page, ReportContext context, WizardSession session) throws Exception {
		
		Object obj = page.unmarshallToSession(formId);
		StorageSampleForm form = (StorageSampleForm) obj;
		logger.info("Retrieve data for form " + form.getName() + " : " + form.getValue());
		
		if (form.getUploadFileName() != null && !form.getUploadFileName().equals("")) {
			String uploadFile = FileManagementUtil.getDir(page.getSession()) + form.getUploadFileName();
			try {
				byte[] fileData = FileManagementUtil.getBytesFromFile(uploadFile);
				logger.info("uploaded fileData.length = " + fileData.length);
				//when you're done with the file, make sure you clean up!
				FileManagementUtil.cleanupDirectory(page.getSession());
			} catch (Exception e) {
				throw new Exception("Error locating uploaded file.");
			}
		}
		
		String[] selectedTableValues = form.getPlainTabularValues();
		for (String selected:selectedTableValues) {
			logger.info("selected tabular value = " + selected);
		}

		String name = form.getName();
		if (name.equals("fail")) {
			//to signal an error you can throw an exception OR return error status
			page.setPageMessage("sample action failed!");
			return PageIf.STATUS_ERROR;
		} else {
			//if you want to display a message to the user, use page.SetPageMessage(...)
			page.setPageMessage("sample action was completed!");
			//return this constant so the UI will display to the user, the action has succeeded.
			return PageIf.STATUS_OK;
		}
	}
	/**
     * @return action title 
     */
	@Override
	public String getTitle() {
		return label;
	}

}
