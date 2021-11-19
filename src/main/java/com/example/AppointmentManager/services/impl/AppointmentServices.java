package com.example.AppointmentManager.services.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.AppointmentManager.entities.Appointment;
import com.example.AppointmentManager.repository.AppointmentRepository;
import com.example.AppointmentManager.services.AppointmentServicesI;

@Service
public class AppointmentServices implements AppointmentServicesI {
	
	AppointmentRepository apointmentRepository;
	
	public AppointmentServices(AppointmentRepository apointmentRepository) {
		this.apointmentRepository = apointmentRepository;
	}
	
	/**
	 * Finds all appointment objects
	 * 
	 * @return an list with all the appointment objects
	 */
	@Transactional(readOnly=true)
	public List<Appointment> findAll() {
		return apointmentRepository.findAll();
	}
	
	/**
	 * Finds an Appointment object, given the Appointment's id
	 * 
	 * @param appointmentId a Long representation of an appointment's primary key on the database
	 * @return the appointment as a appointment object representation
	 */
	@Transactional(readOnly=true)
	public Optional<Appointment> findById(Long id) {
		return apointmentRepository.findById(id);
	}
	
	/**
	 * Creates a new appointment object, given the data provided
	 * 
	 * @param appointment an appointment object 
	 * @return the created object
	 */
	@Transactional
	public Appointment save(Appointment Appointment) {
		return apointmentRepository.save(Appointment);
	}
	
	/**
	 * Delete an appointment object
	 * 
	 * @param appointmentId a Long representation of an appointment's primary key on the database
	 */
	@Transactional
	public void deleteById(Long id) {
		apointmentRepository.deleteById(id);
	}
	
	/**
	 * Check if booking the appointment is possible
	 * 
	 * @return true when the appointment is available, and false when it is not
	 */
	public boolean checkAvailability(Appointment Appointment) {
		int checkDoctorSchedule = apointmentRepository.checkDoctorSchedule(Appointment.getStartTime(),  
									Appointment.getDoctor().getId());
		
		int checkDisponibility = apointmentRepository.checkAvaliability(Appointment.getDoctor().getId(),
								Appointment.getDate(), Appointment.getStartTime(),Appointment.getId());
		
		int checkMonthlyAppointments = apointmentRepository.checkMonthlyAppointments(Appointment.getDate(),
										Appointment.getPatient().getId(), Appointment.getDoctor().getId(), Appointment.getId());
		
		return (checkDoctorSchedule>0 && checkDisponibility==0 && checkMonthlyAppointments<2);
	}
	
	/***
	 *Check the hours not available per doctor, daily
	 *
	 *@param id an Long representation of the doctor's id
	 *@param date an Date representation of the day to search
	 *@param appoinmentId an Long representation of the appointment's id
	 *
	 *@return List<Time> a List with all the hour taken on that day
	 */
	public List<Time> checkDoctorAppointment(Long id, Date date, Long appointmentId) {
		return this.apointmentRepository.checkDoctorAppointment(date, id, appointmentId);
	};
}