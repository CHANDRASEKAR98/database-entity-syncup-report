package com.client.database.entity.syncup.report;

import com.client.database.entity.syncup.report.dto.DatabaseUtilityDto;
import com.client.database.entity.syncup.report.dto.EntitySyncUtilityDto;
import com.client.database.entity.syncup.report.dto.SyncUpUtilDto;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSyncBase {

	protected static final String TABLE_ENTITY_SYNCUP_REPORT_SUCCESS_MESSAGE = "Table Entity Syncup report generated successfully.";

	protected static final String TABLE_ENTITY_SYNCUP_REPORT_FAILURE_MESSAGE = "Table Entity Syncup report not generated";
	
	protected static final String ENTITY_CLASS_NOT_AVAILABLE_FOR_TABLE = "Entity class not available for this table";
	
	protected static final String UNEXPECTED_ERROR_DURING_DB_ENTITY_SYNCUP_MSG = "Unexpected error has occured during Database-Entity syncup report generation";
	
	protected static final String DUMMY_DATA = "";
	
	protected static final int DUMMY_INT_VALUE = 0;
	
	protected static final int DUMMY_INT_VALUE_1 = 1;
	
	protected static final int DUMMY_INT_VALUE_2 = 2;
	
	protected static final String TABLE_NAME = "customer";
	
	protected static List<DatabaseUtilityDto> databaseSyncList() {
		List<DatabaseUtilityDto> databasesyncList = new ArrayList<>();
		databasesyncList.add(DatabaseUtilityDto.builder().columnName(DUMMY_DATA)
				.columnDataType(DUMMY_DATA).columnLength(DUMMY_INT_VALUE)
				.nullable(Boolean.FALSE).build());
		databasesyncList.add(DatabaseUtilityDto.builder().columnName(DUMMY_DATA)
				.columnDataType(DUMMY_DATA).columnLength(DUMMY_INT_VALUE)
				.nullable(Boolean.FALSE).build());
		return databasesyncList;
	}
	
	protected static List<EntitySyncUtilityDto> entitySyncList() {
		List<EntitySyncUtilityDto> entitysyncList = new ArrayList<>();
		entitysyncList.add(EntitySyncUtilityDto.builder().fieldName(DUMMY_DATA)
				.fieldDataType(DUMMY_DATA).fieldLength(DUMMY_INT_VALUE)
				.nullable(Boolean.FALSE).build());
		entitysyncList.add(EntitySyncUtilityDto.builder().fieldName(DUMMY_DATA)
				.fieldDataType(DUMMY_DATA).fieldLength(DUMMY_INT_VALUE)
				.nullable(Boolean.FALSE).build());
		return entitysyncList;
	}
	
	protected static List<SyncUpUtilDto> syncUpUtilList() {
		List<SyncUpUtilDto> syncUpUtilList = new ArrayList<>();
		SyncUpUtilDto syncUpUtilDto = new SyncUpUtilDto();
		syncUpUtilDto.setDatabaseUtilityDto(databaseSyncList().get(DUMMY_INT_VALUE));
		syncUpUtilDto.setEntitySyncUtilityDto(entitySyncList().get(DUMMY_INT_VALUE));
		syncUpUtilList.add(syncUpUtilDto);
		return syncUpUtilList;
	}
	
	protected static List<SyncUpUtilDto> syncUpUtilDbNullList() {
		List<SyncUpUtilDto> syncUpUtilList = new ArrayList<>();
		SyncUpUtilDto syncUpUtilDto = new SyncUpUtilDto();
		syncUpUtilDto.setDatabaseUtilityDto(null);
		syncUpUtilDto.setEntitySyncUtilityDto(entitySyncList().get(DUMMY_INT_VALUE));
		syncUpUtilList.add(syncUpUtilDto);
		return syncUpUtilList;
	}
	
	protected static List<SyncUpUtilDto> syncUpUtilEntityNullList() {
		List<SyncUpUtilDto> syncUpUtilList = new ArrayList<>();
		SyncUpUtilDto syncUpUtilDto = new SyncUpUtilDto();
		syncUpUtilDto.setDatabaseUtilityDto(databaseSyncList().get(DUMMY_INT_VALUE));
		syncUpUtilDto.setEntitySyncUtilityDto(null);
		syncUpUtilList.add(syncUpUtilDto);
		return syncUpUtilList;
	}
	
	protected static List<SyncUpUtilDto> syncUpUtilNullList() {
		List<SyncUpUtilDto> syncUpUtilList = new ArrayList<>();
		SyncUpUtilDto syncUpUtilDto = new SyncUpUtilDto();
		syncUpUtilDto.setDatabaseUtilityDto(null);
		syncUpUtilDto.setEntitySyncUtilityDto(null);
		syncUpUtilList.add(syncUpUtilDto);
		return syncUpUtilList;
	}
	
	protected static List<String> tableNameDataList() {
		List<String> tableNameList = new ArrayList<>();
		tableNameList.add(DUMMY_DATA);
		tableNameList.add(DUMMY_DATA);
		return tableNameList;
	}

}
