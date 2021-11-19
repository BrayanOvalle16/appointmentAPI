package com.example.AppointmentManager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.example.AppointmentManager.dto.AppointmentDTO;
import com.example.AppointmentManager.dto.DoctorDTO;
import com.example.AppointmentManager.dto.PatientDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(
		locations = "classpath:resorces/application.properties"
		)
class AppointmentControllerTest {

	private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/AppointmentsSQL/getSQL.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void getAppointmentById() {
		
		ResponseEntity<AppointmentDTO> response = testRestTemplate.getForEntity("/api/appointment/2", AppointmentDTO.class);
		
		assertEquals(2L, response.getBody().getId());
		assertEquals(new Date(1630627200000L), response.getBody().getDate());
		assertEquals(2, response.getBody().getDoctor().getId());
		assertEquals( 2 , response.getBody().getPatient().getId());
		assertEquals(new Time(46800000L), response.getBody().getStartTime());	
		assertEquals(200, response.getStatusCodeValue());
		
		response = testRestTemplate.getForEntity("/api/appointment/3", AppointmentDTO.class);
		assertEquals(3L, response.getBody().getId());
		assertEquals(new Date(1638403200000L), response.getBody().getDate());
		assertEquals(1, response.getBody().getDoctor().getId());
		assertEquals( 1 , response.getBody().getPatient().getId());
		assertEquals(new Time(46800000L), response.getBody().getStartTime());	
		assertEquals(200, response.getStatusCodeValue());
		
		response = testRestTemplate.getForEntity("/api/appointment/123", AppointmentDTO.class);
		assertEquals(404, response.getStatusCodeValue());
	}
	
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/AppointmentsSQL/getSQL.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test 
	public void update() {
		
		PatientDTO patient = new PatientDTO();
		DoctorDTO doctor= new DoctorDTO();
		doctor.setId(1L);
		patient.setId(1L);
		
		AppointmentDTO appointment = new AppointmentDTO();
		appointment.setId(1L);
		appointment.setDate(new Date(1635724800000L));
		appointment.setDoctorDto(doctor);
		appointment.setPatientDto(patient);
		appointment.setStartTime(new Time(46800000));
		
		ResponseEntity<AppointmentDTO> response = testRestTemplate.exchange("/api/appointment/1",  HttpMethod.PUT,
											createHttpEntity(appointment), AppointmentDTO.class);
		
		assertEquals(appointment.getId(), response.getBody().getId());
		assertEquals(appointment.getDoctor().getId(), response.getBody().getDoctor().getId());
		assertEquals( appointment.getPatient().getId() , response.getBody().getPatient().getId());
		assertEquals(new Time(46800000), response.getBody().getStartTime());	
		assertEquals(201, response.getStatusCodeValue());
		
		Map<String, Object> doctorDTO = jdbcTemplate.queryForMap("SELECT * FROM APPOINTMENT WHERE ID=?", 1);
		assertEquals(appointment.getId(), doctorDTO.get("ID"));
		assertEquals(appointment.getDoctor().getId(), doctorDTO.get("DOCTOR_ID"));
		assertEquals( appointment.getPatient().getId() , doctorDTO.get("PATIENT_ID"));
		assertEquals(new Time(46800000), doctorDTO.get("START_TIME"));
		
	}

	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/AppointmentsSQL/getSQL.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void getAppointmentList() {
		
		ResponseEntity<List<AppointmentDTO>> response = testRestTemplate.exchange("/api/appointment",  HttpMethod.GET, null,
														new ParameterizedTypeReference<List<AppointmentDTO>>() {} );
		
		List<AppointmentDTO> listAppointment = response.getBody();
		
		assertEquals(1L, listAppointment.get(0).getId());
		assertEquals(new Date(1635811200000L), listAppointment.get(0).getDate());
		assertEquals(1, listAppointment.get(0).getDoctor().getId());
		assertEquals( 1 , listAppointment.get(0).getPatient().getId());
		assertEquals(new Time(46800000), listAppointment.get(0).getStartTime());	
		
		assertEquals(2L, listAppointment.get(1).getId());
		assertEquals(new Date(1630627200000L), listAppointment.get(1).getDate());
		assertEquals(2, listAppointment.get(1).getPatient().getId());
		assertEquals( 2 , listAppointment.get(1).getDoctor().getId());
		assertEquals(new Time(46800000), listAppointment.get(1).getStartTime());	
		assertEquals(200, response.getStatusCodeValue());
		
		assertEquals(3L, listAppointment.get(2).getId());
		assertEquals(new Date(1638403200000L), listAppointment.get(2).getDate());
		assertEquals(1, listAppointment.get(2).getPatient().getId());
		assertEquals( 1 , listAppointment.get(2).getDoctor().getId());
		assertEquals(new Time(46800000), listAppointment.get(2).getStartTime());	
		assertEquals(200, response.getStatusCodeValue());
		
		assertEquals(4L, listAppointment.get(3).getId());
		assertEquals(new Date(1627948800000L), listAppointment.get(3).getDate());
		assertEquals(2, listAppointment.get(3).getPatient().getId());
		assertEquals( 2 , listAppointment.get(3).getDoctor().getId());
		assertEquals(new Time(46800000), listAppointment.get(3).getStartTime());	
		assertEquals(200, response.getStatusCodeValue());
		
	}
	
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/AppointmentsSQL/post.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void Create() {
		
		PatientDTO patient = new PatientDTO();
		DoctorDTO doctor= new DoctorDTO();
		doctor.setId(1L);
		patient.setId(1L);
		
		AppointmentDTO appointment = new AppointmentDTO();
		appointment.setDate(new Date(1635724800000L));
		appointment.setDoctorDto(doctor);
		appointment.setPatientDto(patient);
		appointment.setStartTime(new Time(46800000));
		
		ResponseEntity<AppointmentDTO> response = testRestTemplate.postForEntity("/api/appointment", appointment,AppointmentDTO.class);
		
		assertEquals(2, response.getBody().getId());
		assertEquals( appointment.getPatient().getId() , response.getBody().getPatient().getId());
		assertEquals(new Time(46800000), response.getBody().getStartTime());	
		assertEquals(201, response.getStatusCodeValue());
		
		Map<String, Object> doctorDTO = jdbcTemplate.queryForMap("SELECT * FROM APPOINTMENT WHERE ID=?", 2);
		assertEquals(2L, doctorDTO.get("ID"));
		assertEquals(appointment.getDoctor().getId(), doctorDTO.get("DOCTOR_ID"));
		assertEquals( appointment.getPatient().getId() , doctorDTO.get("PATIENT_ID"));
		assertEquals(appointment.getStartTime(), doctorDTO.get("START_TIME"));
		
	}
	
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/AppointmentsSQL/getSQL.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void deleteById() {
		
		ResponseEntity<HttpStatus> response =testRestTemplate.exchange("/api/appointment/1",  HttpMethod.DELETE, null,
											new ParameterizedTypeReference<HttpStatus>() {} );
		
		int count= jdbcTemplate.queryForObject("SELECT COUNT(*) FROM APPOINTMENT WHERE id=1", Integer.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0, count);
		
		response =testRestTemplate.exchange("/api/appointment/2",  HttpMethod.DELETE, null,
				new ParameterizedTypeReference<HttpStatus>() {} );
		
		count= jdbcTemplate.queryForObject("SELECT COUNT(*) FROM APPOINTMENT WHERE id=1", Integer.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0, count);
		
		response =testRestTemplate.exchange("/api/appointment/2",  HttpMethod.DELETE, null,
				new ParameterizedTypeReference<HttpStatus>() {} );
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/AppointmentsSQL/post.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void CreateWhenAppointmentDuplicate() {
		
		PatientDTO patient = new PatientDTO();
		DoctorDTO doctor= new DoctorDTO();
		doctor.setId(1L);
		patient.setId(1L);
		
		AppointmentDTO appointment = new AppointmentDTO();
		appointment.setDate(new Date(1635897600000L));
		appointment.setDoctorDto(doctor);
		appointment.setPatientDto(patient);
		
		ResponseEntity<AppointmentDTO> response = testRestTemplate.postForEntity("/api/appointment", appointment,AppointmentDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/AppointmentsSQL/getSQL.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void CreateWhenAppointmentOutOfDoctorSchedule() {
		
		PatientDTO patient = new PatientDTO();
		DoctorDTO doctor= new DoctorDTO();
		doctor.setId(1L);
		patient.setId(1L);
		
		AppointmentDTO appointment = new AppointmentDTO();
		appointment.setId(1L);
		appointment.setDate(new Date(1635724800000L));
		appointment.setDoctorDto(doctor);
		appointment.setPatientDto(patient);
		appointment.setStartTime(new Time(40000));
		
		ResponseEntity<AppointmentDTO> response = testRestTemplate.postForEntity("/api/appointment", appointment,AppointmentDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/AppointmentsSQL/postSameMonth.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void CreateWhenPatientHasMoreTwoAppointmentsSameMonth() {

		PatientDTO patient = new PatientDTO();
		DoctorDTO doctor= new DoctorDTO();
		doctor.setId(1L);
		patient.setId(1L);
		
		AppointmentDTO appointment = new AppointmentDTO();
		appointment.setId(1L);
		appointment.setDate(new Date(1637452800000L));
		appointment.setDoctorDto(doctor);
		appointment.setPatientDto(patient);
		appointment.setStartTime(new Time(40000));
		
		ResponseEntity<AppointmentDTO> response = testRestTemplate.postForEntity("/api/appointment", appointment,AppointmentDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	private HttpEntity<AppointmentDTO> createHttpEntity(AppointmentDTO appointment) {
		return new HttpEntity<>(appointment, createJsonHeader());
	}
	
	private static HttpHeaders createJsonHeader() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return httpHeaders;
		
	}
}
