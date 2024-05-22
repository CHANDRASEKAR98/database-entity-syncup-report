package com.client.database.entity.syncup.report.exception;

import com.client.database.entity.syncup.report.util.DatabaseEntitySyncupReportEnum;

import lombok.Getter;

/**
 * 
 * @author chandrasekar B
 * 
 * <p>This class contain the fields that will be used on handling exception.
 *
 */
@Getter
@SuppressWarnings("serial")
public class DatabaseEntitySyncupReportException extends RuntimeException {
	
	private String errorCode;
	
	private String errorDescription;
	
	private String statusCode;
	
	private DatabaseEntitySyncupReportEnum databaseEntitySyncupReportEnum;
	
	public DatabaseEntitySyncupReportException(DatabaseEntitySyncupReportEnum databaseEntitySyncupReportEnum, Throwable err) {
		super(err);
		this.databaseEntitySyncupReportEnum = databaseEntitySyncupReportEnum;
	}

	public DatabaseEntitySyncupReportException(DatabaseEntitySyncupReportEnum databaseEntitySyncupReportEnum) {
		this.databaseEntitySyncupReportEnum = databaseEntitySyncupReportEnum;
	}
	
	

}
