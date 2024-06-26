package com.client.database.entity.syncup.report.controller;

import com.client.database.entity.syncup.report.dto.TableDetailRequest;
import com.client.database.entity.syncup.report.service.DatabaseEntitySyncReportGeneratorService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author chandrasekar B
 * 
 * <p>This class contain API endpoints to generate the table report.<p>
 *
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/database-entity/")
public class DatabaseEntitySyncReportController {
	
	private final DatabaseEntitySyncReportGeneratorService databaseEntitySyncReportGeneratorService;
	
	/**
	 * getTableReport method will call the service for report generation of specified table 
	 * for the table-entity sync up report.
	 * 
	 * @param tableName
	 * @return
	 */
	@GetMapping("/sync-report/{table-name}")
	public String getTableReport(@PathVariable(name = "table-name", required = true) String tableName) {
		return databaseEntitySyncReportGeneratorService.generateReportAsCSVForSpecificTable(tableName);
	}
	
	/**
	 * getAllTableReport method will call the service for report generation for all listed tables 
	 * & entities.
	 * 
	 * @param tableDetailRequest
	 * @return
	 */
	@PostMapping("/sync-report")
	public String getAllTableReport(@RequestBody TableDetailRequest tableDetailRequest) {
		return databaseEntitySyncReportGeneratorService.generateListOfTableEntitySyncReport(tableDetailRequest.getTableNameList());
	}
	
}
