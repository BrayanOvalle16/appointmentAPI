package com.example.AppointmentManager.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.AppointmentManager.entities.Patient;
import com.example.AppointmentManager.repository.PatientRepository;
import com.example.AppointmentManager.services.PatientServicesI;

@Service
public class PatientServices implements PatientServicesI {
	
	private PatientRepository patientRepository;
	
	public PatientServices(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}
	
	/**
	 * Finds all patient objects
	 * 
	 * @return an list with all the patient objects
	 */
	@Transactional(readOnly=true)
	public List<Patient> findAll() {
		return patientRepository.findAll();
	}
	
	/**
	 * Creates a new patient object, given the data provided
	 * 
	 * @param patient an patient object 
	 * @return the created object
	 */
	@Transactional
	public Patient save(Patient patient) {
		return patientRepository.save(patient);
	}
	
	/**
	 * Finds an patient object, given the patient's id
	 *  
	 * @param patientId a Long representation of an patient's primary key on the database
	 * @return the patient as a patient object representation
	 */
	@Transactional(readOnly=true)
	public Optional<Patient> findById(Long id) {
		return patientRepository.findById(id);
	}

	/**
	 * Delete an patient object
	 * 
	 * @param patientId a Long representation of an patient's primary key on the database
	 */
	@Transactional
	public void deleteById(Long id) {
		patientRepository.deleteById(id);
	}
}
