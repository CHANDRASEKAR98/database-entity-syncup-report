package com.client.database.entity.syncup.report.util;

import static org.junit.Assert.assertNotNull;

import com.client.database.entity.syncup.report.entity.ExampleEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EntitySyncUtilTest {
	
	@InjectMocks
	private EntitySyncUtil<ExampleEntity> entitySyncUtil;
	
	@Test
	public void getEntityFieldMetaData() {
		assertNotNull(entitySyncUtil.getEntityFieldMetaData(new ExampleEntity()));
	}

}
