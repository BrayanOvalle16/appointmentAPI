package com.example.AppointmentManager.services;

import java.util.List;
import java.util.Optional;

import com.example.AppointmentManager.entities.Patient;

/**
 * 
 * Defines the different methods used to fulfill the Patient object's requests of the API
 */
public interface PatientServicesI {
	
	public List<Patient> findAll();
	
	public Patient save(Patient Patient);
	
	public Optional<Patient> findById(Long id);
	
	public void deleteById(Long id);
}
