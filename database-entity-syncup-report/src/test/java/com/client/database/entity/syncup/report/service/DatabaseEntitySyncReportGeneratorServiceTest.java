package com.client.database.entity.syncup.report.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.client.database.entity.syncup.report.DatabaseSyncBase;
import com.client.database.entity.syncup.report.entity.ExampleEntity;
import com.client.database.entity.syncup.report.exception.DatabaseEntitySyncupReportException;
import com.client.database.entity.syncup.report.util.CSVProcessUtil;
import static com.client.database.entity.syncup.report.util.DatabaseEntitySyncupReportEnum.UNEXPECTED_ERROR_DURING_DB_ENTITY_SYNCUP;
import com.client.database.entity.syncup.report.util.DatabaseSyncUtil;
import com.client.database.entity.syncup.report.util.EntitySqlProcessUtil;
import com.client.database.entity.syncup.report.util.EntitySyncUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.sql.SQLException;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseEntitySyncReportGeneratorServiceTest extends DatabaseSyncBase {
	
	@Mock
    DatabaseSyncUtil databaseSyncUtil;
	
    @Mock
	EntitySyncUtil<Object> entitySyncUtil;
	
	@Mock
	CSVProcessUtil csvProcessUtil;
	
	@Mock
	EntitySqlProcessUtil entitySqlProcessUtil;
	
	@InjectMocks
	DatabaseEntitySyncReportGeneratorService databaseEntitySyncReportGenService;
	
	@Before
	public void setup() {
		ReflectionTestUtils.setField(databaseEntitySyncReportGenService, "tableDetailFlag", 0);
		ReflectionTestUtils.setField(databaseEntitySyncReportGenService, "entityDetailFlag", 1);
		ReflectionTestUtils.setField(databaseEntitySyncReportGenService, "tableEntitySyncDetailFlag", 2);
		ReflectionTestUtils.setField(databaseEntitySyncReportGenService, "outputReportPath", "src/test/resources/dbTestOutput");
	}
	
	@Test
	public void generateReportAsCSVForSpecificTableSuccessTest() throws SQLException, IOException {
		when(databaseSyncUtil.getTableMetadata(anyString())).thenReturn(databaseSyncList());
		when(entitySqlProcessUtil.getTableSqlQuery(anyString())).thenReturn(DUMMY_DATA);
		when(entitySqlProcessUtil.getEntityClassObject(anyString())).thenReturn(new ExampleEntity());
		when(entitySyncUtil.getEntityFieldMetaData(any(Object.class))).thenReturn(entitySyncList());
		when(csvProcessUtil.convertDataListToCSV(anyList(), anyString(), anyInt())).thenReturn(Boolean.TRUE);
		String actualResponse = databaseEntitySyncReportGenService.generateReportAsCSVForSpecificTable(TABLE_NAME);
		assertEquals(TABLE_ENTITY_SYNCUP_REPORT_SUCCESS_MESSAGE, actualResponse);
	}
	
	@Test
	public void generateReportAsCSVForSpecificTableFailureTest() throws SQLException, IOException {
		when(databaseSyncUtil.getTableMetadata(anyString())).thenReturn(databaseSyncList());
		when(entitySqlProcessUtil.getTableSqlQuery(anyString())).thenReturn(DUMMY_DATA);
		when(entitySqlProcessUtil.getEntityClassObject(anyString())).thenReturn(new ExampleEntity());
		when(entitySyncUtil.getEntityFieldMetaData(any(Object.class))).thenReturn(entitySyncList());
		when(csvProcessUtil.convertDataListToCSV(anyList(), anyString(), anyInt())).thenReturn(Boolean.FALSE);
		String actualResponse = databaseEntitySyncReportGenService.generateReportAsCSVForSpecificTable(TABLE_NAME);
		assertEquals(TABLE_ENTITY_SYNCUP_REPORT_FAILURE_MESSAGE, actualResponse);
	}
	
	@Test
	public void generateReportAsCSVForSpecificTableNullTest() throws SQLException, IOException {
		when(databaseSyncUtil.getTableMetadata(anyString())).thenReturn(null);
		when(entitySqlProcessUtil.getTableSqlQuery(anyString())).thenReturn(DUMMY_DATA);
		when(entitySqlProcessUtil.getEntityClassObject(anyString())).thenReturn(new ExampleEntity());
		when(entitySyncUtil.getEntityFieldMetaData(any(Object.class))).thenReturn(entitySyncList());
		String actualResponse = databaseEntitySyncReportGenService.generateReportAsCSVForSpecificTable(TABLE_NAME);
		assertEquals(ENTITY_CLASS_NOT_AVAILABLE_FOR_TABLE, actualResponse);
	}
	
	@Test
	public void generateReportAsCSVForSpecificTableExceptionTest() throws SQLException, IOException {
		when(databaseSyncUtil.getTableMetadata(anyString())).thenThrow(new DatabaseEntitySyncupReportException(UNEXPECTED_ERROR_DURING_DB_ENTITY_SYNCUP));
		when(entitySqlProcessUtil.getTableSqlQuery(anyString())).thenReturn(DUMMY_DATA);
		DatabaseEntitySyncupReportException actualException = assertThrows(DatabaseEntitySyncupReportException.class, 
				() -> databaseEntitySyncReportGenService.generateReportAsCSVForSpecificTable(TABLE_NAME));
		assertNotNull(actualException);
		assertEquals(UNEXPECTED_ERROR_DURING_DB_ENTITY_SYNCUP, actualException.getDatabaseEntitySyncupReportEnum());
	}
	
	@Test
	public void generateListOfTableEntitySyncReportSuccessTest() throws SQLException, IOException {
		when(databaseSyncUtil.getTableMetadata(anyString())).thenReturn(databaseSyncList());
		when(entitySqlProcessUtil.getTableSqlQuery(anyString())).thenReturn(DUMMY_DATA);
		when(entitySqlProcessUtil.getEntityClassObject(anyString())).thenReturn(new ExampleEntity());
		when(entitySyncUtil.getEntityFieldMetaData(any(Object.class))).thenReturn(entitySyncList());
		when(csvProcessUtil.convertDataListToCSV(anyList(), anyString(), anyInt())).thenReturn(Boolean.TRUE);
		when(csvProcessUtil.mergeCsvFiles()).thenReturn(Boolean.TRUE);
		String actualResponse = databaseEntitySyncReportGenService.generateListOfTableEntitySyncReport(tableNameDataList());
		assertEquals(ALL_TABLE_ENTITY_SYNCUP_REPORT_SUCCESS_MESSAGE, actualResponse);
	}
	
	@Test
	public void generateListOfTableEntitySyncReportFailureTest() throws SQLException, IOException {
		when(databaseSyncUtil.getTableMetadata(anyString())).thenReturn(databaseSyncList());
		when(entitySqlProcessUtil.getTableSqlQuery(anyString())).thenReturn(DUMMY_DATA);
		when(entitySqlProcessUtil.getEntityClassObject(anyString())).thenReturn(new ExampleEntity());
		when(entitySyncUtil.getEntityFieldMetaData(any(Object.class))).thenReturn(entitySyncList());
		when(csvProcessUtil.convertDataListToCSV(anyList(), anyString(), anyInt())).thenReturn(Boolean.FALSE);
		when(csvProcessUtil.mergeCsvFiles()).thenReturn(Boolean.FALSE);
		String actualResponse = databaseEntitySyncReportGenService.generateListOfTableEntitySyncReport(tableNameDataList());
		assertEquals(ALL_TABLE_ENTITY_SYNCUP_REPORT_FAILURE_MESSAGE, actualResponse);
	}
	
	@Test
	public void generateListOfTableEntitySyncReportExceptionTest() throws SQLException, IOException {
		when(databaseSyncUtil.getTableMetadata(anyString())).thenThrow(new DatabaseEntitySyncupReportException(UNEXPECTED_ERROR_DURING_DB_ENTITY_SYNCUP));
		when(entitySqlProcessUtil.getTableSqlQuery(anyString())).thenReturn(DUMMY_DATA);
		DatabaseEntitySyncupReportException actualException = assertThrows(DatabaseEntitySyncupReportException.class, 
				() -> databaseEntitySyncReportGenService.generateListOfTableEntitySyncReport(tableNameDataList()));
		assertNotNull(actualException);
		assertEquals(UNEXPECTED_ERROR_DURING_DB_ENTITY_SYNCUP, actualException.getDatabaseEntitySyncupReportEnum());
	}

}
