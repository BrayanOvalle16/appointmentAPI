package com.example.AppointmentManager.services;

import java.util.List;
import java.util.Optional;

import com.example.AppointmentManager.entities.Doctor;

/**
 * 
 * Defines the different methods used to fulfill the Doctor object's requests of the API
 */
public interface DoctorServicesI {

	public List<Doctor> findAll();
	
	public Doctor save(Doctor doctor);
	
	public Optional<Doctor> findById(Long id);
	
	public void deleteById(Long id);
}
