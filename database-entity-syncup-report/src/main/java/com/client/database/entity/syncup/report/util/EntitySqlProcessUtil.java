package com.client.database.entity.syncup.report.util;

import static com.client.database.entity.syncup.report.constants.TableConstants.EXAMPLE_TABLE;
import static com.client.database.entity.syncup.report.constants.TableConstants.EXAMPLE_QUERY;
import static com.client.database.entity.syncup.report.constants.MessageContants.EMPTY_DATA;
import com.client.database.entity.syncup.report.entity.ExampleEntity;

import org.springframework.stereotype.Component;

/**
 * 
 * @author chandrasekar B
 * 
 * <p>This class contain the logic for choosing the entity and SQL query.
 *
 */
@Component
public class EntitySqlProcessUtil {
	
	/**
	 * getTableSqlQuery method gets the table sql query based on the table name.
	 * @param tableName
	 * @return
	 */
	public String getTableSqlQuery(String tableName) {
		switch (tableName) {
		case EXAMPLE_TABLE:
			return EXAMPLE_QUERY;
		default:
			return EMPTY_DATA;
		}
	}
	
	/**
	 * getEntityClassObject method gets the entity object based on the table name.
	 * @param tableName
	 * @return
	 */
	public Object getEntityClassObject(String tableName) {
		switch (tableName) {
		case EXAMPLE_TABLE:
			return new ExampleEntity();
		default:
			return EMPTY_DATA;
		}
	}

}
