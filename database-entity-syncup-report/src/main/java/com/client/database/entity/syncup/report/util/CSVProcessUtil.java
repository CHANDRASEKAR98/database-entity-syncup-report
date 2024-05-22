package com.client.database.entity.syncup.report.util;

import com.client.database.entity.syncup.report.dto.SyncUpUtilDto;
import com.client.database.entity.syncup.report.exception.DatabaseEntitySyncupReportException;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.log4j.Log4j2;

import static com.client.database.entity.syncup.report.constants.MessageContants.DATE_FORMAT;
import static com.client.database.entity.syncup.report.constants.MessageContants.EMPTY_DATA;
import static com.client.database.entity.syncup.report.constants.MessageContants.WHITESPACE_DATA;
import static com.client.database.entity.syncup.report.constants.MessageContants.IS_NULLABLE;

/**
 * 
 * @author chandrasekar B
 * 
 * <p>This class contain the utilities for CSV file generation.
 *
 */
@Component
@Log4j2
public class CSVProcessUtil {
	
	@Value("${syncup.file.output.path}")
	private String outputReportPath;
	
	/**
	 * convertDataListToCSV method generates the CSV file.
	 * @param syncUpUtilList
	 * @param tableName
	 * @param dbUtilFlag
	 * @return
	 * @throws IOException
	 */
	public boolean convertDataListToCSV(List<SyncUpUtilDto> syncUpUtilList, String tableName, int dbUtilFlag) throws IOException {
		log.debug("convertDataListToCSV method starts");
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		String fileName = "";
		if (dbUtilFlag == 0) {
			fileName = tableName + "_Table_Report_" + dateFormat.format(new Date()) + ".csv";
		} else if (dbUtilFlag == 1) {
			fileName = tableName + "_Entity_Report_" + dateFormat.format(new Date()) + ".csv";
		} else {
			fileName = tableName + "_Table_Entity_Sync_Report_" + dateFormat.format(new Date()) + ".csv";
		}
		File csvOutputFile = new File(outputReportPath + fileName);
		if (csvOutputFile.getParentFile().mkdir()) {
			csvOutputFile.createNewFile();
		} else {
			csvOutputFile.createNewFile();
		}
		try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
			setCsvDataList(syncUpUtilList, dbUtilFlag).stream().map(this::convertToCSV).forEach(pw::println);
		}
		log.debug("convertDataListToCSV method ends");
		return csvOutputFile.exists();
	}
	
	/**
	 * setCsvDataList method sets the values to a list
	 * @param syncUpUtilList
	 * @param dbUtilFlag
	 * @return
	 */
	private List<Object[]> setCsvDataList(List<SyncUpUtilDto> syncUpUtilList, int dbUtilFlag) {
		List<Object[]> dataLines = new ArrayList<>();
		if (dbUtilFlag == 0) {
			dataLines.add(new Object[] {"Column Name", "Column DataType", "Column Length", IS_NULLABLE});
		} else if (dbUtilFlag == 1) {
			dataLines.add(new Object[] {"Field Name", "Field DataType", "Field Length", IS_NULLABLE});
		} else {
			dataLines.add(new Object[] {"Column Name", "Column DataType", "Column Length", IS_NULLABLE, WHITESPACE_DATA, "Field Name", "Field DataType", "Field Length", IS_NULLABLE});
		}
		for (SyncUpUtilDto syncUpData: syncUpUtilList) {
			if (Objects.nonNull(syncUpData.getDatabaseUtilityDto()) && Objects.nonNull(syncUpData.getEntitySyncUtilityDto())) {
				dataLines.add(new Object[] { 
						syncUpData.getDatabaseUtilityDto().getColumnName(),
						syncUpData.getDatabaseUtilityDto().getColumnDataType(),
						syncUpData.getDatabaseUtilityDto().getColumnLength(),
						syncUpData.getDatabaseUtilityDto().isNullable(), WHITESPACE_DATA,
						syncUpData.getEntitySyncUtilityDto().getFieldName(),
						syncUpData.getEntitySyncUtilityDto().getFieldDataType(),
						syncUpData.getEntitySyncUtilityDto().getFieldLength(),
						syncUpData.getEntitySyncUtilityDto().isNullable()
						});
			} else if (Objects.isNull(syncUpData.getDatabaseUtilityDto()) && Objects.nonNull(syncUpData.getEntitySyncUtilityDto())) {
				dataLines.add(new Object[] { 
						EMPTY_DATA, EMPTY_DATA, EMPTY_DATA, EMPTY_DATA, WHITESPACE_DATA,
						syncUpData.getEntitySyncUtilityDto().getFieldName(),
						syncUpData.getEntitySyncUtilityDto().getFieldDataType(),
						syncUpData.getEntitySyncUtilityDto().getFieldLength(),
						syncUpData.getEntitySyncUtilityDto().isNullable()
						});
			} else if (Objects.nonNull(syncUpData.getDatabaseUtilityDto()) && Objects.isNull(syncUpData.getEntitySyncUtilityDto())) {
				dataLines.add(new Object[] { 
						syncUpData.getDatabaseUtilityDto().getColumnName(),
						syncUpData.getDatabaseUtilityDto().getColumnDataType(),
						syncUpData.getDatabaseUtilityDto().getColumnLength(),
						syncUpData.getDatabaseUtilityDto().isNullable(), WHITESPACE_DATA,
						EMPTY_DATA, EMPTY_DATA, EMPTY_DATA, EMPTY_DATA
						});
			} else {
				dataLines.add(new Object[] { EMPTY_DATA, EMPTY_DATA, EMPTY_DATA, EMPTY_DATA, WHITESPACE_DATA, EMPTY_DATA, EMPTY_DATA, EMPTY_DATA, EMPTY_DATA });
			}
		}
		return dataLines;
	}
	
	/**
	 * convertToCSV method converts the data list to a csv file format.
	 * @param data
	 * @return
	 */
	private String convertToCSV(Object[] data) {
		return Stream.of(data).map(this::checkNullConstraint).collect(Collectors.joining(","));
	}
	
	/**
	 * checkNullConstraint method checks if the data is null.
	 * @param data
	 * @return
	 */
	private String checkNullConstraint(Object data) {
		if (data == null) {
			throw new IllegalArgumentException("Input data cannot be null");
		}
		return data.toString();
	}
	
	/**
	 * mergeCsvFiles method will merge all the available csv files in the path to xls file.
	 * @return
	 */
	public boolean mergeCsvFiles() {
		try {
			File folder = new File(outputReportPath);
			File[] listOfFiles = folder.listFiles();
			@SuppressWarnings("resource")
			HSSFWorkbook workbook = new HSSFWorkbook();
			
			for (File file : listOfFiles) {
				if (file.isFile()) {
					String thisLine;
					ArrayList<String> al = null;
					ArrayList<ArrayList<String>> arList = new ArrayList<>();
					
					HSSFSheet sheet = workbook.createSheet(file.getName());
					FileInputStream fis = new FileInputStream(file);
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					
					while((thisLine = br.readLine()) != null) {
						al = new ArrayList<>();
						String strar[] = thisLine.split(",");
						for (int j = 0;j < strar.length; j++) {
							for (int k = 0;k < arList.size(); k++) {
								ArrayList<String> arData = (ArrayList<String>) arList.get(k);
								HSSFRow row = sheet.createRow((short) k);
								for (int p = 0;p < arData.size();p++) {
									HSSFCell cell = row.createCell((short) p);
									cell.setCellValue(arData.get(p).toString());
								}
							}
							al.add(strar[j]);
						}
						arList.add(al);
					}
					SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
					fis.close();
					FileOutputStream fileOut = new FileOutputStream(outputReportPath + "_Data_Mapping_" + dateFormat.format(new Date()) + ".xls");
					workbook.write(fileOut);
					fileOut.flush();
					fileOut.close();
					br.close();
				}
			}
		} catch (Exception exception) {
			throw new DatabaseEntitySyncupReportException(DatabaseEntitySyncupReportEnum.UNEXPECTED_ERROR_DURING_DB_ENTITY_SYNCUP, exception);
		}
		return true;
	}

}
