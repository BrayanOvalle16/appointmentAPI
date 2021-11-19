package com.example.AppointmentManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AppointmentManager.entities.Patient;

/**
 * 
 * Defines different queries used to send or get information from the database for Patient's object
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
  
}
