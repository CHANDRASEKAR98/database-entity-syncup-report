package com.client.database.entity.syncup.report.dto.exception;

import lombok.Data;

/**
 * 
 * @author chandrasekar B
 * 
 * <p>This class contain the fields that will be returned on error response.
 *
 */
@Data
public class DatabaseSyncUpError {
	
	private String errorCode;
	
	private String errorDescription;
	
	private String statusCode;

	public DatabaseSyncUpError(String errorCode, String errorDescription, String statusCode) {
		super();
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
		this.statusCode = statusCode;
	}

}