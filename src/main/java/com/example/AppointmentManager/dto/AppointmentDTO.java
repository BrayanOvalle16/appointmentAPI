package com.example.AppointmentManager.dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import org.springframework.beans.BeanUtils;

import com.example.AppointmentManager.entities.Doctor;
import com.example.AppointmentManager.entities.Patient;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AppointmentDTO implements Serializable {

	private static final long serialVersionUID = -1881568150148463872L;

	private Long id;

	private PatientDTO patient;

	private DoctorDTO doctor;

	private Time startTime;

	private Date date;
	
	public Doctor getDoctor() {
		Doctor doctorCopy = new Doctor();
		BeanUtils.copyProperties(doctor, doctorCopy);
		System.out.println(doctorCopy.getId());
		return doctorCopy;
	}
	
	public DoctorDTO getDoctorDto() {
		return doctor;
	}
	
	public Patient getPatient() {
		Patient patientCopy = new Patient();
		BeanUtils.copyProperties(patient, patientCopy);
		return patientCopy;
	}
	
	public PatientDTO getPatientDto() {
		return patient;
	}
	
	public void setDoctor(Doctor doctor) {
		this.doctor = new DoctorDTO();
		BeanUtils.copyProperties(doctor, this.doctor);
	}
	
	public void setDoctorDto(DoctorDTO doctor) {
		this.doctor = doctor;
	}
	
	public void setPatientDto(PatientDTO patient) {
		this.patient = patient;
	}
	
	public void setPatient(Patient patientCopy) {
		this.patient = new PatientDTO();
		BeanUtils.copyProperties(patientCopy, patient);
	}
	
}

