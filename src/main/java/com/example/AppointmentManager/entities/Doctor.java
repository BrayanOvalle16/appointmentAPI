package com.example.AppointmentManager.entities;

import java.io.Serializable;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="DOCTOR")
@Getter@Setter
public class Doctor implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	@Column(nullable=false, length=40, name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
    private String lastName;
    
	@Column(name="IDENTIFICATION")
    private String identification; 
    
	@Column(name="TYPE_IDENTIFICATION")
    private String typeIdentification;
    
	@Column(name="PROFESSIONAL_CARD_NUMBER")
    private String professionalCardNumber;
    
	@Column(name="YEARS_OF_EXPERIENCE")
    private int yearsOfExperience;
    
	@Column(name="SPECIALISM")
    private String specialism;
    
	@Column(name="START_ATTENTION_TIME")
    private Time startAttentionTime;
    
	@Column(name="FINAL_ATTENTION_TIME")
    private Time  finalAttentionTime;
 
}
