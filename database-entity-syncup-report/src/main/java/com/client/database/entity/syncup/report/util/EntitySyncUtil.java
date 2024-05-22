package com.client.database.entity.syncup.report.util;

import com.client.database.entity.syncup.report.dto.EntitySyncUtilityDto;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;

/**
 * 
 * @author chandrasekar B
 * 
 * This generic class is used to get all the properties of an entity.
 *
 * @param <T>
 */
@Component
public class EntitySyncUtil<T> {
	
	/**
	 * getEntityFieldMetaData method gets all metadata of an Entity<T>
	 * @param entity
	 * @return
	 */
	public List<EntitySyncUtilityDto> getEntityFieldMetaData(T entity) {
		List<EntitySyncUtilityDto> entityfieldSyncList = new ArrayList<>();
		if (Objects.isNull(entity)) {
			return null;
		}
		
		for (Field field: entity.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Column.class)) {
				entityfieldSyncList.add(EntitySyncUtilityDto.builder()
						.fieldName(field.getAnnotation(Column.class).name())
						.fieldDataType(field.getType().getSimpleName())
						.fieldLength(field.getAnnotation(Column.class).length())
						.nullable(field.getAnnotation(Column.class).nullable()).build());
			}
		}
		return entityfieldSyncList;
	}

}
