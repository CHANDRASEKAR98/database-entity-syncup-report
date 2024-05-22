package com.client.database.entity.syncup.report.dto;

import lombok.Data;

/**
 * 
 * @author chandrasekar B
 * 
 * <p>This class contain the fields that represent the properties of the table and entity.
 *
 */
@Data
public class SyncUpUtilDto implements Comparable<SyncUpUtilDto> {
	
	private DatabaseUtilityDto databaseUtilityDto;
	
	private EntitySyncUtilityDto entitySyncUtilityDto;
	
	@Override
	public int compareTo(SyncUpUtilDto data) {
		return 0;
	}

}
