package com.example.AppointmentManager.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="PATIENT")
@Getter@Setter
public class Patient implements Serializable {

	private static final long serialVersionUID = 5287248854235729530L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
 	private long id;
	
	@Column(length=50, nullable=false, name="FULL_NAME")
    private String fullName;
	
	@Column(name="DATE_OF_BIRTH")
    private Date dateOfBirth;
	
	@Column(name="IDENTIFICATION")
    private String identification;
    
	@Column(name="TYPE_IDENTIFICATION")
    private String typeIdentification;
    
	@Column(name="HEALTHCARE_PROVIDER")
    private String healthcareProvider;
    
	@Column(name="CLINIC_HISTORY")
    private String clinicHistory;
}
