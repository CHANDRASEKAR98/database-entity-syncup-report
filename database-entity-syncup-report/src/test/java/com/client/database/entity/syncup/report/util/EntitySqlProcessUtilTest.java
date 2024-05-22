package com.client.database.entity.syncup.report.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.client.database.entity.syncup.report.DatabaseSyncBase;
import com.client.database.entity.syncup.report.constants.TableConstants;
import com.client.database.entity.syncup.report.entity.ExampleEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EntitySqlProcessUtilTest extends DatabaseSyncBase {
	
	@InjectMocks
	private EntitySqlProcessUtil entitySqlProcessUtil;
	
	@Test
	public void getTableQueryTest() {
		String actualResponse = entitySqlProcessUtil.getTableSqlQuery(TableConstants.EXAMPLE_TABLE);
		assertEquals(TableConstants.EXAMPLE_QUERY, actualResponse);
	}
	
	@Test
	public void getInvalidTableQueryTest() {
		String actualResponse = entitySqlProcessUtil.getTableSqlQuery(DUMMY_DATA);
		assertEquals(DUMMY_DATA, actualResponse);
	}

	@Test
	public void getEntityClassObjectTest() {
		Object actualResponse = entitySqlProcessUtil.getEntityClassObject(TableConstants.EXAMPLE_TABLE);
		assertEquals(new ExampleEntity().getClass(), actualResponse.getClass());
	}
	
	@Test
	public void getInvalidEntityClassObjectTest() {
		Object actualResponse = entitySqlProcessUtil.getEntityClassObject(DUMMY_DATA);
		assertEquals(DUMMY_DATA, actualResponse);
	}
}
