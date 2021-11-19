package com.example.AppointmentManager.controllers;

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

import com.example.AppointmentManager.dto.DoctorDTO;
import com.example.AppointmentManager.entities.Doctor;
import com.example.AppointmentManager.services.DoctorServicesI;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
	
	private DoctorServicesI doctorServices;
	
	public DoctorController(DoctorServicesI doctorServices) {
		this.doctorServices =  doctorServices;
	}
	/**
	 * Creates a new Doctor object, given the data provided
	 * 
	 * @param Doctor a JSON representation of an Doctor object
	 * @return the created object, and HTTP status of the request (CREATED, when the object is created. BAD REQUESt, when it is not)
	 */
	@PostMapping
	public ResponseEntity<DoctorDTO> create(@RequestBody DoctorDTO doctordto) {
		Doctor doctor = new Doctor();
		BeanUtils.copyProperties(doctordto, doctor); 
		doctor = doctorServices.save(doctor);
		BeanUtils.copyProperties(doctor, doctordto); 
		return ResponseEntity.status(HttpStatus.CREATED).body(doctordto);
	}
	
	/**
	 * Finds an Doctor object, given the Doctor's id 
	 * 
	 * @param doctorId a Long representation of an Doctor's primary key on the database
	 * @return the Doctor as a JSON representation, and the HTTP status of the request (NOT FOUND, when the object does not exist. OK, when it is found)
	 */
	@GetMapping("/{id}")
	public ResponseEntity<DoctorDTO> read(@PathVariable(value="id")Long doctorId) {
		Optional<Doctor> doctor= doctorServices.findById(doctorId);
		DoctorDTO doctorDto = new DoctorDTO();
		
		
		if(!doctor.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(doctor.get(), doctorDto);
		return ResponseEntity.ok(doctorDto);
	}
	
	/**
	 * Update an existing Doctor object, given the data provided
	 * 
	 * @param detailsdoctor a JSON representation of an Doctor object
	 * @param doctorId a Long id of an Doctor object
	 * @return the updated object, and HTTP status of the request (CREATED, when the object is update. BAD REQUESt, when it is not)
	 */
	@PutMapping("/{id}")
	public ResponseEntity<DoctorDTO> update(@RequestBody DoctorDTO detailsDoctor, @PathVariable(value="id")Long doctorId) {
		Optional<Doctor> doctor = doctorServices.findById(doctorId);
		
		if(!doctor.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Doctor doctorCopy = new Doctor();
		BeanUtils.copyProperties(detailsDoctor, doctorCopy);
		doctorCopy.setId(doctorId);
		doctorCopy = doctorServices.save(doctorCopy);
		BeanUtils.copyProperties(doctorCopy, detailsDoctor);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(detailsDoctor);
	}
	
	/**
	 * Delete an doctor object
	 * 
	 * @param doctorId a Long representation of an Doctor's primary key on the database
	 * @return a HTTP response. NOT FOUND, when the Doctor does not exist. OK, when it was already deleted
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable(value="id")Long doctorId) {
		Optional<Doctor> doctor = doctorServices.findById(doctorId);
		
		if(!doctor.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		doctorServices.deleteById(doctorId);
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Finds all Doctor objects
	 * 
	 * @return an list with all the Doctor objects
	 */
	@GetMapping
	public List<DoctorDTO> readAll() {
		List<Doctor> list = doctorServices.findAll();
		List<DoctorDTO> listDto = list.stream().map(doctor->  {
					DoctorDTO doctorDto = new DoctorDTO();
					BeanUtils.copyProperties(doctor, doctorDto);
					return doctorDto;
		}).collect(Collectors.toList());
		
		return listDto;
	}
}
