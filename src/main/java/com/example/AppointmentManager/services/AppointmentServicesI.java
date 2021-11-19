package com.example.AppointmentManager.services;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import com.example.AppointmentManager.entities.Appointment;

/**
 * 
 * Defines the different methods used to fulfill the Appointment object's requests of the API
 */
public interface AppointmentServicesI {

	public List<Appointment> findAll();
	
	public Appointment save(Appointment appointment);
	
	public Optional<Appointment> findById(Long id);
	
	public void deleteById(Long id);
	
	public boolean checkAvailability(Appointment appointment);
	
	public List<Time> checkDoctorAppointment(Long id, Date date, Long AppointmentId);
	
}
