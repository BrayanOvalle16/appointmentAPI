package com.example.AppointmentManager.entities;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APPOINTMENT")
@Getter@Setter
public class Appointment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@OneToOne()
	private Patient patient;
	
	@OneToOne()
	private Doctor doctor;
	
	@Column(name="START_TIME")
	private Time startTime;
	
	@Column(name="DATE_APPOINTMENT")
	private Date date;
}
