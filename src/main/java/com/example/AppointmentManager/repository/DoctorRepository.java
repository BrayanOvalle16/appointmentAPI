package com.example.AppointmentManager.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AppointmentManager.entities.Doctor;

/**
 * 
 * Defines different queries used to send or get information from the database for Doctor's object
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	
}
