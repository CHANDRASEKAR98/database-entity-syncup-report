package com.client.database.entity.syncup.report.exception;

import com.client.database.entity.syncup.report.dto.exception.DatabaseSyncUpError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author chandrasekar B
 * 
 * <p>This class contains logic that can handle the exceptions. 
 *
 */
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
	
	/**
	 * handleDatabaseEntitySyncupReportException method handles the custom exception thrown on business logic.
	 * @param exception
	 * @return
	 */
        @ExceptionHandler(exception=DatabaseEntitySyncupReportException.class)
	ResponseEntity<DatabaseSyncUpError> handleDatabaseEntitySyncupReportException(DatabaseEntitySyncupReportException exception) {
		log.error("DatabaseEntitySyncupReportException occured, ", exception);
		ResponseEntity<DatabaseSyncUpError> responseEntity;
		if (Objects.nonNull(exception.getMessage()) && !exception.getMessage().isEmpty()) {
			responseEntity = new ResponseEntity<>(new DatabaseSyncUpError(exception.getDatabaseEntitySyncupReportEnum().getErrorCode(), exception.getMessage(), exception.getDatabaseEntitySyncupReportEnum().getStatusCode()), HttpStatus.BAD_REQUEST);
		} else {
			responseEntity = new ResponseEntity<>(new DatabaseSyncUpError(exception.getDatabaseEntitySyncupReportEnum().getErrorCode(), exception.getDatabaseEntitySyncupReportEnum().getErrorDescription(), exception.getDatabaseEntitySyncupReportEnum().getStatusCode()), HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
	
	/**
	 * handleGenericException method handles the generic exception.
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(exception=Exception.class)
	ResponseEntity<DatabaseSyncUpError> handleGenericException(Exception exception) {
		log.error("DatabaseEntitySyncupReportException occured, ", exception);
		ResponseEntity<DatabaseSyncUpError> responseEntity;
		responseEntity = new ResponseEntity<>(new DatabaseSyncUpError(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), exception.getMessage(), ""), HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;
	}

}
