package com.example.AppointmentManager.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AppointmentManager.dto.PatientDTO;
import com.example.AppointmentManager.entities.Patient;
import com.example.AppointmentManager.services.PatientServicesI;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
	
	private PatientServicesI pacientServices;
	
	public PatientController(PatientServicesI pacientServices) {
		this.pacientServices = pacientServices;
	}
	
	/**
	 * Creates a new Patient object, given the data provided
	 * 
	 * @param Patient a JSON representation of an Patient object
	 * @return the created object, and HTTP status of the request (CREATED, when the object is created. BAD REQUESt, when it is not)
	 */
	@PostMapping
	public ResponseEntity<PatientDTO> create(@RequestBody PatientDTO patientDto) {
		Patient patient = new Patient();
		Date newDate = new Date(patientDto.getDateOfBirth().getTime()+ (1000 * 60 * 60 * 24));
		patientDto.setDateOfBirth(newDate);
		BeanUtils.copyProperties(patientDto, patient);
		patient = pacientServices.save(patient);
		BeanUtils.copyProperties(patient, patientDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(patientDto);
	}
	
	/**
	 * Finds an Patient object, given the Patient's id 
	 * 
	 * @param PatientId a Long representation of an Patient's primary key on the database
	 * @return the Patient as a JSON representation, and the HTTP status of the request (NOT FOUND, when the object does not exist. OK, when it is found)
	 */
	@GetMapping("/{id}")
	public ResponseEntity<PatientDTO> read(@PathVariable(value="id")Long patientId) {
		Optional<Patient> patient = pacientServices.findById(patientId);
		PatientDTO patientDto = new PatientDTO();
		
		if(!patient.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(patient.get(), patientDto);
		return ResponseEntity.ok(patientDto);
	}
	
	/**
	 * Update an existing Patient object, given the data provided
	 * 
	 * @param detailsPatient a JSON representation of an Patient object
	 * @param PatientId a Long id of an Patient object
	 * @return the updated object, and HTTP status of the request (CREATED, when the object is update. BAD REQUESt, when it is not)
	 */
	@PutMapping("/{id}")
	public ResponseEntity<PatientDTO> update(@RequestBody PatientDTO detailsPatient, @PathVariable(value="id")Long patientId) {
		Optional<Patient> patientDto= pacientServices.findById(patientId);
		Date newDate = new Date(detailsPatient.getDateOfBirth().getTime()+ (1000 * 60 * 60 * 24));
		detailsPatient.setDateOfBirth(newDate);
		
		if(!patientDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Patient patient = new Patient();
		BeanUtils.copyProperties(detailsPatient, patient);
		patient.setId(patientId);
		patient = pacientServices.save(patient);
		BeanUtils.copyProperties(patient,detailsPatient);
		return ResponseEntity.status(HttpStatus.OK).body(detailsPatient);
	}
	
	/**
	 * Delete an patient object
	 * 
	 * @param PatientId a Long representation of an Patient's primary key on the database
	 * @return a HTTP response. NOT FOUND, when the Patient does not exist. OK, when it was already deleted
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable(value="id")Long patientId) {
		Optional<Patient> patient= pacientServices.findById(patientId);
		
		if(!patient.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		pacientServices.deleteById(patientId);
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Finds all Patient objects
	 * 
	 * @return an list with all the Patient objects
	 */
	@GetMapping
	public List<PatientDTO> readAll() {
		List<Patient> list = pacientServices.findAll();
		List<PatientDTO> listDto = list.stream().map(patient->  {
					PatientDTO patientDto = new PatientDTO();
					BeanUtils.copyProperties(patient, patientDto);
					return patientDto;
		}).collect(Collectors.toList());
		  
		return listDto;
	
	}
	
}
