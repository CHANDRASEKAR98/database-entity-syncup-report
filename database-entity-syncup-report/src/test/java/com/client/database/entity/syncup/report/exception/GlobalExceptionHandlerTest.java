package com.client.database.entity.syncup.report.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.client.database.entity.syncup.report.DatabaseSyncBase;
import com.client.database.entity.syncup.report.dto.exception.DatabaseSyncUpError;
import com.client.database.entity.syncup.report.util.DatabaseEntitySyncupReportEnum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.Objects;

@RunWith(MockitoJUnitRunner.class)
public class GlobalExceptionHandlerTest extends DatabaseSyncBase {
	
	@InjectMocks
	GlobalExceptionHandler exceptionHandler;
	
	@Test
	public void handleRuntimeExceptionTest() {
		DatabaseEntitySyncupReportException exception = new DatabaseEntitySyncupReportException(DatabaseEntitySyncupReportEnum.UNEXPECTED_ERROR_DURING_DB_ENTITY_SYNCUP,
				new SQLException());
		ResponseEntity<DatabaseSyncUpError> expectedResponseEntity = getDatabaseSyncupExceptionRespEntity(exception);
		
		ResponseEntity<DatabaseSyncUpError> actualResponseEntity = exceptionHandler.handleDatabaseEntitySyncupReportException(exception);
		assertEquals(expectedResponseEntity, actualResponseEntity);
	}
	
	@Test
	public void handleBusinessLogicExceptionTest() {
		DatabaseEntitySyncupReportException exception = new DatabaseEntitySyncupReportException(DatabaseEntitySyncupReportEnum.UNEXPECTED_ERROR_DURING_DB_ENTITY_SYNCUP);
		ResponseEntity<DatabaseSyncUpError> expectedResponseEntity = getDatabaseSyncupExceptionRespEntity(exception);
		
		ResponseEntity<DatabaseSyncUpError> actualResponseEntity = exceptionHandler.handleDatabaseEntitySyncupReportException(exception);
		assertEquals(expectedResponseEntity, actualResponseEntity);
	}
	
	@Test
	public void handleGenericExceptionTest() {
		SQLException exception = new SQLException();
		ResponseEntity<DatabaseSyncUpError> expectedResponseEntity = new ResponseEntity<>(new DatabaseSyncUpError(
				String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), exception.getMessage(), DUMMY_DATA), 
				HttpStatus.INTERNAL_SERVER_ERROR);
		
		ResponseEntity<DatabaseSyncUpError> actualResponseEntity = exceptionHandler.handleGenericException(exception);
		
		assertEquals(expectedResponseEntity, actualResponseEntity);
	}

	private ResponseEntity<DatabaseSyncUpError> getDatabaseSyncupExceptionRespEntity(DatabaseEntitySyncupReportException exception) {
		ResponseEntity<DatabaseSyncUpError> expectedResponseEntity = null;
		if (Objects.nonNull(exception.getMessage()) && !exception.getMessage().isEmpty()) {
			expectedResponseEntity = new ResponseEntity<>(new DatabaseSyncUpError(exception.getDatabaseEntitySyncupReportEnum().getErrorCode(),
						exception.getMessage(), 
						exception.getDatabaseEntitySyncupReportEnum().getStatusCode()), 
					HttpStatus.BAD_REQUEST);
		} else {
			expectedResponseEntity = new ResponseEntity<>(new DatabaseSyncUpError(exception.getDatabaseEntitySyncupReportEnum().getErrorCode(),
					exception.getDatabaseEntitySyncupReportEnum().getErrorDescription(), 
					exception.getDatabaseEntitySyncupReportEnum().getStatusCode()), 
				HttpStatus.BAD_REQUEST);
		}
		return expectedResponseEntity;
	}

}
