package com.client.database.entity.syncup.report.service;

import com.client.database.entity.syncup.report.constants.MessageContants;
import com.client.database.entity.syncup.report.dto.DatabaseUtilityDto;
import com.client.database.entity.syncup.report.dto.EntitySyncUtilityDto;
import com.client.database.entity.syncup.report.dto.SyncUpUtilDto;
import com.client.database.entity.syncup.report.exception.DatabaseEntitySyncupReportException;
import com.client.database.entity.syncup.report.util.CSVProcessUtil;
import com.client.database.entity.syncup.report.util.DatabaseEntitySyncupReportEnum;
import com.client.database.entity.syncup.report.util.DatabaseSyncUtil;
import com.client.database.entity.syncup.report.util.EntitySqlProcessUtil;
import com.client.database.entity.syncup.report.util.EntitySyncUtil;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author chandrasekar B
 * 
 * <p>This class contains the core business logic for the report generation for the table.</p>
 *
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class DatabaseEntitySyncReportGeneratorService {
	
	private final DatabaseSyncUtil databaseSyncUtil;
	
	@SuppressWarnings("rawtypes")
	private final EntitySyncUtil entitySyncUtil;
	
	private final CSVProcessUtil csvProcessUtil;
	
	private final EntitySqlProcessUtil entitySqlProcessUtil;
	
	@Value("${database.util.table.detail}")
	private int tableDetailFlag;
	
	@Value("${database.util.entity.detail}")
	private int entityDetailFlag;
	
	@Value("${database.util.table.entity.sync.detail}")
	private int tableEntitySyncDetailFlag;
	
	@Value("${syncup.file.output.path}")
	private String outputReportPath;
	
	/**
	 * generateReportAsCSVForSpecificTable method will generate table syncup report in csv file format.
	 * @param tableName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String generateReportAsCSVForSpecificTable(String tableName) {
		log.debug("generateReportAsCSVForSpecificTable method starts");
		try {
			List<SyncUpUtilDto> dbSyncList = new ArrayList<>();
			List<SyncUpUtilDto> entitySyncList = new ArrayList<>();
			List<SyncUpUtilDto> syncUpUtilList = new ArrayList<>();
			List<DatabaseUtilityDto> databaseDetailSyncList = databaseSyncUtil.getTableMetadata(
					entitySqlProcessUtil.getTableSqlQuery(tableName));
			List<EntitySyncUtilityDto> entityDetailSyncList = entitySyncUtil.getEntityFieldMetaData(
					entitySqlProcessUtil.getEntityClassObject(tableName));
			if (Objects.isNull(databaseDetailSyncList) || Objects.isNull(entityDetailSyncList)) {
				return DatabaseEntitySyncupReportEnum.ENTITY_CLASS_NOT_AVAILABLE_FOR_TABLE.getErrorDescription();
			}
			for (DatabaseUtilityDto dbData : databaseDetailSyncList) {
				SyncUpUtilDto syncUpUtilDto = new SyncUpUtilDto();
				syncUpUtilDto.setDatabaseUtilityDto(dbData);
				dbSyncList.add(syncUpUtilDto);
			}
			for (EntitySyncUtilityDto entityData: entityDetailSyncList) {
				SyncUpUtilDto syncUpUtilDto = new SyncUpUtilDto();
				syncUpUtilDto.setEntitySyncUtilityDto(entityData);
				entitySyncList.add(syncUpUtilDto);
			}
			dbSyncList = dbSyncList.stream().sorted((dbData1, dbData2) -> dbData1.getDatabaseUtilityDto().getColumnName().compareTo(dbData2.getDatabaseUtilityDto().getColumnName())).collect(Collectors.toList());
			entitySyncList = entitySyncList.stream().sorted((entityData1, entityData2) -> entityData1.getEntitySyncUtilityDto().getFieldName().compareTo(entityData2.getEntitySyncUtilityDto().getFieldName())).collect(Collectors.toList());
			Iterator<SyncUpUtilDto> databaseListIterator = dbSyncList.iterator();
			Iterator<SyncUpUtilDto> entityListIterator = entitySyncList.iterator();
			int count = 0;
			while (databaseListIterator.hasNext()) {
				SyncUpUtilDto syncUpUtilDto = new SyncUpUtilDto();
				syncUpUtilDto.setDatabaseUtilityDto(databaseListIterator.next().getDatabaseUtilityDto());
				syncUpUtilList.add(syncUpUtilDto);
			}
			while (entityListIterator.hasNext() && count < syncUpUtilList.size()) {
				SyncUpUtilDto syncUpUtilDto = syncUpUtilList.get(count);
				syncUpUtilDto.setEntitySyncUtilityDto(entityListIterator.next().getEntitySyncUtilityDto());
				syncUpUtilList.set(count, syncUpUtilDto);
				count++;
			}
			log.debug("generateReportAsCSVForSpecificTable method ends");
			return csvProcessUtil.convertDataListToCSV(syncUpUtilList, tableName, tableEntitySyncDetailFlag) ? 
					MessageContants.TABLE_ENTITY_SYNCUP_REPORT_SUCCESS_MESSAGE : 
						MessageContants.TABLE_ENTITY_SYNCUP_REPORT_FAILURE_MESSAGE;
		} catch (Exception exception) {
			log.error("Error occured during Database syncup report generation: ", exception);
			throw new DatabaseEntitySyncupReportException(
					DatabaseEntitySyncupReportEnum.UNEXPECTED_ERROR_DURING_DB_ENTITY_SYNCUP, exception);
		}
	}
	
	/**
	 * generateListOfTableEntitySyncReport method will generate the sync report for the list of tables.
	 * 
	 * @param tableNameList
	 * @return
	 */
	public String generateListOfTableEntitySyncReport(List<String> tableNameList) {
		AtomicInteger reportCount = new AtomicInteger(0);
		try {
			File filePath = new File(outputReportPath);
			if (filePath.isDirectory()) {
				FileUtils.cleanDirectory(new File(outputReportPath));
			}
			tableNameList.stream().forEach(tableName -> {
				generateReportAsCSVForSpecificTable(tableName);
				reportCount.getAndIncrement();
			});
			if (reportCount.get() == tableNameList.size()) {
				return csvProcessUtil.mergeCsvFiles() ? MessageContants.ALL_TABLE_ENTITY_SYNCUP_REPORT_SUCCESS_MESSAGE 
						: MessageContants.ALL_TABLE_ENTITY_SYNCUP_REPORT_FAILURE_MESSAGE;
			}
		} catch (Exception exception) {
			log.error("Error occured during Database Syncup report generation: ", exception);
			throw new DatabaseEntitySyncupReportException(DatabaseEntitySyncupReportEnum.UNEXPECTED_ERROR_DURING_DB_ENTITY_SYNCUP, exception);
		}
		return MessageContants.ALL_TABLE_ENTITY_SYNCUP_REPORT_FAILURE_MESSAGE;
	}

}
