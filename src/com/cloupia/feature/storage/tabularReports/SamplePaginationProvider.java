package com.cloupia.feature.storage.tabularReports;

import org.apache.log4j.Logger;

import com.cloupia.model.cIM.ColumnDefinition;
import com.cloupia.model.cIM.Query;
import com.cloupia.model.cIM.QueryBuilder;
import com.cloupia.model.cIM.ReportContext;
import com.cloupia.model.cIM.pagination.PaginatedReportHandler;
import com.cloupia.model.cIM.pagination.TabularReportMetadata;
import com.cloupia.service.cIM.inframgr.reportengine.ReportRegistryEntry;

public class SamplePaginationProvider extends PaginatedReportHandler{
	
	static Logger logger = Logger.getLogger(SamplePaginationProvider.class);

	@Override
	public Query appendContextSubQuery(ReportRegistryEntry entry,
			TabularReportMetadata metaData, ReportContext reportContext, Query query) {
		
		String contextId = null;
		
		if(reportContext != null)
			contextId = reportContext.getId();
		
		if (contextId != null && !contextId.isEmpty()) {
			logger.info("Pagination Report Context: "+contextId);
			String accountName = contextId.split(";")[0];
			
			int mgmtColIndex = entry.getManagementColumnIndex();
			ColumnDefinition mgmtCol = metaData.getColumns()[mgmtColIndex];
			String mgmtColId = mgmtCol.getColumnId();
			
			QueryBuilder sqb = new QueryBuilder();
			sqb.putParam(mgmtColId).eq(accountName);
			
			if (query == null) {
				//if query is null and the id field has actual value, we only want to return columnName = value of id
				//which is what sqb should be
				logger.info("query == null ::  ---- paginated context ID = " + contextId);
				Query q = sqb.get();
				return q;
			} else {
				QueryBuilder qb = new QueryBuilder();
				qb.and(query, sqb.get());
				return qb.get();
			}
		}else{
			return query;
		}
	}

}
