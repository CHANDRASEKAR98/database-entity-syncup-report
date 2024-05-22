package com.client.database.entity.syncup.report.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import com.client.database.entity.syncup.report.DatabaseSyncBase;

import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseSyncUtilTest extends DatabaseSyncBase {

	@InjectMocks
	private DatabaseSyncUtil databaseSyncUtil;
	
	@Mock
	private DataSource dataSource;
	
	@Mock
	private Connection con;
	
	@Mock
	private Statement statement;
	
	@Mock
	private ResultSet resultSet;
	
	@Mock
	private ResultSetMetaData resultSetMetaData;
	
	@Before
	public void setup() throws SQLException {
		int columnCount = 1;
		assertNotNull(resultSet);
		when(con.createStatement()).thenReturn(statement);
		when(dataSource.getConnection()).thenReturn(con);
		when(statement.executeQuery(anyString())).thenReturn(resultSet);
		when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
		when(resultSetMetaData.getColumnCount()).thenReturn(columnCount);
		when(resultSetMetaData.getColumnName(anyInt())).thenReturn(DUMMY_DATA);
		when(resultSetMetaData.getColumnTypeName(anyInt())).thenReturn(DUMMY_DATA);
		when(resultSetMetaData.getColumnDisplaySize(anyInt())).thenReturn(columnCount);
		when(resultSetMetaData.isNullable(anyInt())).thenReturn(columnCount);
	}
	
	@Test
	public void getTableMetaDataSuccessTest() throws SQLException {
		assertNotNull(databaseSyncUtil.getTableMetadata("ABCD"));
	}
	
	@Test
	public void getTableMetaDataFailureTest() throws SQLException {
		assertNull(databaseSyncUtil.getTableMetadata(""));
	}
}
