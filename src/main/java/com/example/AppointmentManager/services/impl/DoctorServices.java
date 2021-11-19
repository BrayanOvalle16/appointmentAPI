package com.example.AppointmentManager.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.AppointmentManager.entities.Doctor;
import com.example.AppointmentManager.repository.DoctorRepository;
import com.example.AppointmentManager.services.DoctorServicesI;

@Service
public class DoctorServices implements DoctorServicesI {
	
	private DoctorRepository doctorRepository;
	
	public DoctorServices(DoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
	}
	
	/**
	 * Finds all doctor objects
	 * 
	 * @return an list with all the doctor objects
	 */
	@Transactional(readOnly=true)
	public List<Doctor> findAll() {
		return doctorRepository.findAll();
	}
	
	/**
	 * Creates a new doctor object, given the data provided
	 * 
	 * @param doctor an doctor object 
	 * @return the created object
	 */
	@Transactional
	public Doctor save(Doctor medico) {
		return doctorRepository.save(medico);
	}
	
	/**
	 * Finds an Doctor object, given the doctor's id 
	 * 
	 * @param doctorId a Long representation of an doctor's primary key on the database
	 * @return the doctor as a doctor object representation
	 */
	@Transactional(readOnly=true)
	public Optional<Doctor> findById(Long id) {
		return doctorRepository.findById(id);
	}
	
	/**
	 * Delete an doctor object
	 * 
	 * @param doctorId a Long representation of an doctor's primary key on the database
	 */
	@Transactional
	public void deleteById(Long id) {
		doctorRepository.deleteById(id);
	}
}


