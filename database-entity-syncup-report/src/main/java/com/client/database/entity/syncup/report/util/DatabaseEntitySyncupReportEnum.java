package com.client.database.entity.syncup.report.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author chandrasekar B
 * 
 * THis enum contain the business validation messages.
 *
 */
@Getter
@AllArgsConstructor
public enum DatabaseEntitySyncupReportEnum {
	
	UNEXPECTED_ERROR_DURING_DB_ENTITY_SYNCUP("E1000", "Unexpected error has occured during Database-Entity syncup report generation", "400"),
	INVALID_TABLE_NAME("E1001", "Table name is invalid", "400"),
	ENTITY_CLASS_NOT_AVAILABLE_FOR_TABLE("E1002", "Entity class not available for this table", "400");
	
	private final String errorCode;
	
	private final String errorDescription;
	
	private final String statusCode;

}
