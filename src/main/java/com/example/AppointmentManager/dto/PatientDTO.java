package com.example.AppointmentManager.dto;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class PatientDTO implements Serializable{

	private static final long serialVersionUID = 9070325910813535573L;

	private long id;
	
    private String fullName;
	
    private Date dateOfBirth;
	
    private String identification;
    
    private String typeIdentification;
    
    private String healthcareProvider;

    private String clinicHistory;

}
