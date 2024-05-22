package com.client.database.entity.syncup.report.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * @author chandrasekar B
 * 
 * <p>This class is an example of an Entity that maps to a table in database.
 *
 */
@Entity
@Table(name =  "CUSTOMER_INFO")
@Data
public class ExampleEntity implements Serializable {
	
	private static final long serialVersionUID = 3564009998264445379L;
	
	@Id
	@Column(name = "CUSTOMER_ID", nullable = false)
	private long customerId;
	
	@Column(name = "FIRST_NAME", length = 255)
	private String firstName;
	
	@Column(name = "LAST_NAME", length = 255)
	private String lastName;
	
	@Column(name = "EMAIL_ID", length = 255)
	private String emailId;
	
	@Column(name = "CONTACT_NO", length = 255)
	private String contactNo;

}
