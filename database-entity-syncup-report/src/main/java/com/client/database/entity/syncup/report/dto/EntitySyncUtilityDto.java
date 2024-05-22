package com.client.database.entity.syncup.report.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author chandrasekar B
 * 
 * <p>THis class contain the fields that represent the entity properties.
 *
 */
@Data
@Builder
public class EntitySyncUtilityDto {
	
	private String fieldName;
	
	private String fieldDataType;
	
	private int fieldLength;
	
	private boolean nullable;

}
