package com.client.database.entity.syncup.report.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TableDetailRequest implements Serializable {
	
	private static final long serialVersionUID = 8917188275673111213L;
	
	private List<String> tableNameList = new ArrayList<>();

}
