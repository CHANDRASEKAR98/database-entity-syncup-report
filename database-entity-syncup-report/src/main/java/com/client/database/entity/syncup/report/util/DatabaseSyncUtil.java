package com.client.database.entity.syncup.report.util;

import com.client.database.entity.syncup.report.dto.DatabaseUtilityDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author chandrasekar B
 * 
 * <p>This class contain the utilities that can fetch the properties of a table. 
 *
 */
@Component
@Log4j2
public class DatabaseSyncUtil {
	
	@Autowired
	private DataSource dataSource;
	
	/**
	 * getTableMetadata method get all metadata of a table.
	 * @param tableSql
	 * @return
	 * @throws SQLException
	 */
	public List<DatabaseUtilityDto> getTableMetadata(String tableSql) throws SQLException {
		if (tableSql.isBlank()) {
			log.info(DatabaseEntitySyncupReportEnum.INVALID_TABLE_NAME.getErrorDescription());
			return null;
		}
		Connection con = dataSource.getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(tableSql);
		
		ResultSetMetaData rsMetaData = rs.getMetaData();
		int numberOfColumns = rsMetaData.getColumnCount();
		String tableName = rsMetaData.getTableName(numberOfColumns - 1);
		log.info("Getting the metadata of the table '{}'", tableName);
		
		List<DatabaseUtilityDto> tableColumnPropertyList = new ArrayList<>();
		for (int i = 1;i < numberOfColumns + 1;i++) {
			tableColumnPropertyList.add(DatabaseUtilityDto.builder()
					.columnName(rsMetaData.getColumnName(i))
					.columnDataType(rsMetaData.getColumnTypeName(i))
					.columnLength(rsMetaData.getColumnDisplaySize(i))
					.nullable(rsMetaData.isNullable(i) == 1 ? true : false).build());
		}
		rs.close();
		st.close();
		con.close();
		return tableColumnPropertyList;
	}

}
