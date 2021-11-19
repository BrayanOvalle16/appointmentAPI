package com.example.AppointmentManager.controllers;

import java.sql.Date;
import java.sql.Time;
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

import com.example.AppointmentManager.dto.AppointmentDTO;
import com.example.AppointmentManager.entities.Appointment;
import com.example.AppointmentManager.services.AppointmentServicesI;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

	private AppointmentServicesI apointmentServices;

	public AppointmentController(AppointmentServicesI apointmentServices) {
		this.apointmentServices =  apointmentServices;
	};
	
	/**
	 * Creates a new appointment object, given the data provided
	 * 
	 * @param appointment a JSON representation of an Appointment object
	 * @return the created object, and HTTP status of the request (CREATED, when the object is created. BAD REQUESt, when it is not)
	 */
	@PostMapping
	public ResponseEntity<AppointmentDTO> create(@RequestBody AppointmentDTO appointmentDto) {
		Appointment appointment = new Appointment();
		BeanUtils.copyProperties(appointmentDto, appointment);
		Date newDate = new Date(appointmentDto.getDate().getTime()+ (1000 * 60 * 60 * 24));
		appointment.setDate(newDate);
		appointment.setId(0L);
		if(apointmentServices.checkAvailability(appointment)) {
			appointment = apointmentServices.save(appointment);
			BeanUtils.copyProperties(appointment, appointmentDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(appointmentDto);
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	
	/**
	 * Finds an Appointment object, given the Appointment's id 
	 * 
	 * @param appointmentId a Long representation of an appointment's primary key on the database
	 * @return the appointment as a JSON representation, and the HTTP status of the request (NOT FOUND, when the object does not exist. OK, when it is found)
	 */
	@GetMapping("/{id}")
	public ResponseEntity<AppointmentDTO> read(@PathVariable(value="id")Long appointmentId) {
		Optional<Appointment> appointment = apointmentServices.findById(appointmentId);
		AppointmentDTO appointmentDto = new AppointmentDTO();
		
		if(!appointment.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(appointment.get(), appointmentDto);
		appointmentDto.setDoctor(appointment.get().getDoctor());
		appointmentDto.setPatient(appointment.get().getPatient());
		return ResponseEntity.ok(appointmentDto);
	}
	
	/**
	 * Update an existing appointment object, given the data provided
	 * 
	 * @param detailsAppointment a JSON representation of an Appointment object
	 * @param appointmentId a Long id of an Appointment object
	 * @return the updated object, and HTTP status of the request (CREATED, when the object is update. 
	 *         BAD REQUESt, when it is not)
	 */
	@PutMapping("/{id}")
	public ResponseEntity<AppointmentDTO> update(@RequestBody AppointmentDTO detailsAppointment, @PathVariable(value="id")Long appointmentId) {
		Optional<Appointment> findAppointment = apointmentServices.findById(appointmentId);
		Date newDate = new Date(detailsAppointment.getDate().getTime()+ (1000 * 60 * 60 * 24));
		detailsAppointment.setDate(newDate);
		
		if(!findAppointment.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Appointment appointment = new Appointment();
		BeanUtils.copyProperties(detailsAppointment, appointment);
		
		if(apointmentServices.checkAvailability(appointment)) {
			System.out.println(appointment.getId());
			apointmentServices.save(appointment);
			return ResponseEntity.status(HttpStatus.CREATED).body(detailsAppointment);
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	/**
	 * Finds all appointment objects
	 * 
	 * @return an list with all the appointment objects
	 */
	@GetMapping
	public ResponseEntity<List<AppointmentDTO>> readAll() {
		List<Appointment> list = apointmentServices.findAll();
		List<AppointmentDTO> listDto = list.stream().map(appointment->  {
					AppointmentDTO appointmentDto = new AppointmentDTO();
					BeanUtils.copyProperties(appointment, appointmentDto);
					return appointmentDto;
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok(listDto);
	}
	
	/**
	 * Delete an appointment object
	 * 
	 * @param appointmentId a Long representation of an appointment's primary key on the database
	 * @return a HTTP response. NOT FOUND, when the Appointment does not exist. OK, when it was already deleted
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable(value="id")Long appointmentId) {
		Optional<Appointment> appointment = apointmentServices.findById(appointmentId);
		
		if(!appointment.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		apointmentServices.deleteById(appointmentId);
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping("/appointmentByDay/{day}/{id}/{appointment_id}")
	public ResponseEntity<List<Time>> checkDoctorAppointment(@PathVariable("day")Date date, @PathVariable("id") Long id,
															@PathVariable("appointment_id") Long appointment_id) {
		return ResponseEntity.ok(this.apointmentServices.checkDoctorAppointment(id, date, appointment_id));
	}
}
