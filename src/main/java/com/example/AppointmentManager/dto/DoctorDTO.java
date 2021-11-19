package com.example.AppointmentManager.dto;

import java.io.Serializable;
import java.sql.Time;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class DoctorDTO implements Serializable{

	private static final long serialVersionUID = 7159260714708131088L;

	private long id;
	
	private String firstName;
	
    private String lastName;
    
    private String identification; 
    
    private String typeIdentification;

    private String professionalCardNumber;
  
    private int yearsOfExperience;

    private String specialism;

    private Time startAttentionTime;

    private Time finalAttentionTime;

}
