package com.client.database.entity.syncup.report.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.client.database.entity.syncup.report.DatabaseSyncBase;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class CSVProcessUtilTest extends DatabaseSyncBase {
	
	@InjectMocks
	private CSVProcessUtil csvProcessUtil;
	
	@Mock
	private File file;
	
	@Mock
	private BufferedReader bufferedReader;
	
	@Before
	public void setup() {
		ReflectionTestUtils.setField(csvProcessUtil, "outputReportPath", "src/test/resources/dbTestOutput/");
	}
	
	@Test
	public void convertDataListtoCSVDbTableNotNullTest() throws IOException {
		assertNotNull(csvProcessUtil.convertDataListToCSV(syncUpUtilList(), DUMMY_DATA, DUMMY_INT_VALUE));
	}
	
	@Test
	public void convertDataListtoCSVDbEntityNotNullTest() throws IOException {
		assertNotNull(csvProcessUtil.convertDataListToCSV(syncUpUtilList(), DUMMY_DATA, DUMMY_INT_VALUE_1));
	}
	
	@Test
	public void convertDataListtoCSVDbTableEntityNotNullTest() throws IOException {
		assertNotNull(csvProcessUtil.convertDataListToCSV(syncUpUtilList(), DUMMY_DATA, DUMMY_INT_VALUE_2));
	}
	
	@Test
	public void convertDataListtoCSVDbNullTest() throws IOException {
		assertNotNull(csvProcessUtil.convertDataListToCSV(syncUpUtilDbNullList(), DUMMY_DATA, DUMMY_INT_VALUE));
	}
	
	@Test
	public void convertDataListtoCSVDbEntityNullTest() throws IOException {
		assertNotNull(csvProcessUtil.convertDataListToCSV(syncUpUtilEntityNullList(), DUMMY_DATA, DUMMY_INT_VALUE_1));
	}
	
	@Test
	public void convertDataListtoCSVNullTest() throws IOException {
		assertNotNull(csvProcessUtil.convertDataListToCSV(syncUpUtilNullList(), DUMMY_DATA, DUMMY_INT_VALUE));
	}
	
	@Test
	public void mergeCSVFIleTest() throws IOException {
		File testFile = new File("src/test/resources/dbTestOutput/");
		boolean actualResponse = csvProcessUtil.mergeCsvFiles();
		if (testFile.isDirectory()) {
			FileUtils.cleanDirectory(testFile);
		}
		assertEquals(true, actualResponse);
	}

}
