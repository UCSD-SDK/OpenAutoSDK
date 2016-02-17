package com.cloupia.feature.storage.tabularReports;

import com.cloupia.lib.easyui.EasyReportQueryBuilderIf;
import com.cloupia.model.cIM.ReportContext;

public class SampleEasyReportQueryBuilder implements EasyReportQueryBuilderIf {
	/**
	 * This method act as a query generator for easy report.
	 * @return returns query
	 */
	@Override
	public String buildQuery(ReportContext reportContext) {
		String context = reportContext.getId();
		//String[] args = context.split(";");
		
		return "accountName == '"+context+"'";
	}

}
