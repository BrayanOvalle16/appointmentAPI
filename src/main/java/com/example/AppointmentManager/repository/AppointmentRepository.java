package com.example.AppointmentManager.repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.AppointmentManager.entities.Appointment;

/**
 * 
 * Defines different queries used to send or get information from the database for Appointment's object
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	/**
	 * Check if the appointment time fits the doctor schedule
	 * 
	 * @param start_apointment_time a Time representation of the appointment's hour to check
	 * @param idDoctor Long representation of the doctor's primary key on the database
	 * @return 0 if 
	 */
	@Query(value = "SELECT count(*) " +
				   "FROM DOCTOR " +
				   "WHERE HOUR(start_attention_time)<=HOUR(:start_apointment_time) AND " +
				   " HOUR(final_attention_time)>HOUR(:start_apointment_time) AND " +
				   " id=:idDoctor", 
		nativeQuery = true
		)
	int checkDoctorSchedule(@Param("start_apointment_time")Time  start_apointment_time, @Param("idDoctor")Long idDoctor); 
	
	/**
	 * Check if the doctor has or not an appointment
	 * 
	 * @param doctorId Long representation of the doctor's primary key on the database
	 * @param dateApointment Date representation of the date's appointment
	 * @param start_time Time representation of the appointment's hour to check
	 * @param id Long representation of the appointments's primary key on the database
	 * @return 0 if the appointment is taken and 1 if the appointment is available
	 */
	@Query(
			value = "SELECT count(*) "+
					"FROM APPOINTMENT "+
					"WHERE doctor_id=:doctorId AND "+
					"      date_appointment=:dateAppointment AND "+
					"      HOUR(start_time)=HOUR(:start_time) AND "+
					"      id<>:id",
			nativeQuery = true
			)
	int checkAvaliability(@Param("doctorId")Long doctorId, @Param("dateAppointment")Date dateAppointment, 
							@Param("start_time")Time  start_time, @Param("id")Long id);
	
	/**
	 * Check the number of appointments that a patient has with the same doctor monthly
	 * 
	 * @param monthApointment Date representation of the date's appointment
	 * @param patient_id Long representation of the patient's primary key on the database
	 * @param idDoctor Long representation of the doctor's primary key on the database
	 * @param id Long representation of the appointment's primary key on the database
	 * @return the number of appointments that a patient has with the same doctor monthly
	 */
	@Query(
			value = "SELECT count(*)"+
					"FROM appointment "+
					"WHERE patient_id=:patient_id "+
					"AND MONTH(date_appointment)= MONTH(:monthAppointment) "+
					"AND MONTH(date_appointment)= MONTH(:monthAppointment) "+
					"AND id<>:id "+
					"AND doctor_id=:idDoctor",
			nativeQuery = true
			)
	int checkMonthlyAppointments(@Param("monthAppointment")Date monthAppointment, @Param("patient_id")Long patient_id,
								@Param("idDoctor")Long idDoctor, @Param("id")Long id);
	
	@Query(
			value = "SELECT start_time "+
					"FROM appointment "+
					"WHERE doctor_id=:doctorId "+
					"AND date_appointment= :day "+
					"AND id<>:appointmentId",
			nativeQuery = true
			)
	
	List<Time> checkDoctorAppointment(@Param("day")Date day, @Param("doctorId")Long doctorId ,@Param("appointmentId")Long appointmentId);
	
}
