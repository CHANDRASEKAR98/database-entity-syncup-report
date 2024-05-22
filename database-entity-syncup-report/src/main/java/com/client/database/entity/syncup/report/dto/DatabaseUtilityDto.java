package com.client.database.entity.syncup.report.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author chandrasekar B
 * 
 * <p>This class contain the fields that represent the table properties.
 *
 */
@Data
@Builder
public class DatabaseUtilityDto {
	
	private String columnName;
	
	private String columnDataType;
	
	private int columnLength;
	
	private boolean nullable;

}
