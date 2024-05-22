package com.client.database.entity.syncup.report.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.client.database.entity.syncup.report.DatabaseSyncBase;
import com.client.database.entity.syncup.report.service.DatabaseEntitySyncReportGeneratorService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseEntitySyncReportControllerTest extends DatabaseSyncBase {
	
	@InjectMocks
	DatabaseEntitySyncReportController databaseEntitySyncReportController;
	
	@Mock
	DatabaseEntitySyncReportGeneratorService reportGeneratorService;
	
	@Test
	public void getTableReportTest() {
		when(reportGeneratorService.generateReportAsCSVForSpecificTable(anyString())).thenReturn(TABLE_ENTITY_SYNCUP_REPORT_SUCCESS_MESSAGE);
		String actualResponse = databaseEntitySyncReportController.getTableReport(DUMMY_DATA);
		assertEquals(TABLE_ENTITY_SYNCUP_REPORT_SUCCESS_MESSAGE, actualResponse);
	}

}
