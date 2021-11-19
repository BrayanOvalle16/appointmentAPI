package com.example.AppointmentManager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.example.AppointmentManager.dto.DoctorDTO;
import com.example.AppointmentManager.entities.Doctor;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(
		locations = "classpath:/resorces/application.properties"
		)
class DoctorControllerTest {
	
	private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/doctorSQL/getSQL.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void getDoctorById() {
		
		ResponseEntity<DoctorDTO> response = testRestTemplate.getForEntity("/api/doctor/1", DoctorDTO.class);
		
		assertEquals(1, response.getBody().getId());
		assertEquals("Jose" , response.getBody().getFirstName());
		assertEquals("Ovalle", response.getBody().getLastName());
		assertEquals(new Time(43200000L), response.getBody().getStartAttentionTime());
		assertEquals("1232323", response.getBody().getIdentification());
		assertEquals("Cedula", response.getBody().getTypeIdentification());
		assertEquals(15, response.getBody().getYearsOfExperience());
		assertEquals(new Time(86400000L), response.getBody().getFinalAttentionTime());
		assertEquals("DUT234", response.getBody().getProfessionalCardNumber());	
		assertEquals(200, response.getStatusCodeValue());
		
		response = testRestTemplate.getForEntity("/api/doctor/2", DoctorDTO.class);
		
		assertEquals(2, response.getBody().getId());
		assertEquals("Brayan" , response.getBody().getFirstName());
		assertEquals("Ovalle", response.getBody().getLastName());
		assertEquals(new Time(43200000L), response.getBody().getStartAttentionTime());
		assertEquals("1232323", response.getBody().getIdentification());
		assertEquals("Cedula Extranjera", response.getBody().getTypeIdentification());
		assertEquals(15, response.getBody().getYearsOfExperience());
		assertEquals(new Time(86400000L), response.getBody().getFinalAttentionTime());
		assertEquals("DUT234", response.getBody().getProfessionalCardNumber());	
		assertEquals(200, response.getStatusCodeValue());
		
		response = testRestTemplate.getForEntity("/api/doctor/123", DoctorDTO.class);
		assertEquals(404, response.getStatusCodeValue());
		
	}

	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/doctorSQL/getSQL.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void getDoctorList() {
		
		ResponseEntity<List<DoctorDTO>> response = testRestTemplate.exchange("/api/doctor",  HttpMethod.GET, null,
													new ParameterizedTypeReference<List<DoctorDTO>>() {} );
		
		List<DoctorDTO> listDoctor = response.getBody();
		
		assertEquals(1, listDoctor.get(0).getId());
		assertEquals("Jose" , listDoctor.get(0).getFirstName());
		assertEquals("Ovalle", listDoctor.get(0).getLastName());
		assertEquals(new Time(43200000L), listDoctor.get(0).getStartAttentionTime());
		assertEquals("1232323", listDoctor.get(0).getIdentification());
		assertEquals("Cedula", listDoctor.get(0).getTypeIdentification());
		assertEquals(15, listDoctor.get(0).getYearsOfExperience());
		assertEquals(new Time(86400000L), listDoctor.get(0).getFinalAttentionTime());
		assertEquals("DUT234", listDoctor.get(0).getProfessionalCardNumber());	
		
		assertEquals(2, listDoctor.get(1).getId());
		assertEquals("Brayan" , listDoctor.get(1).getFirstName());
		assertEquals("Ovalle", listDoctor.get(1).getLastName());
		assertEquals(new Time(43200000L), listDoctor.get(1).getStartAttentionTime());
		assertEquals("1232323", listDoctor.get(1).getIdentification());
		assertEquals("Cedula Extranjera", listDoctor.get(1).getTypeIdentification());
		assertEquals(15, listDoctor.get(1).getYearsOfExperience());
		assertEquals(new Time(86400000L), listDoctor.get(1).getFinalAttentionTime());
		assertEquals("DUT234", listDoctor.get(1).getProfessionalCardNumber());	

		assertEquals(3, listDoctor.get(2).getId());
		assertEquals("Carlos" , listDoctor.get(2).getFirstName());
		assertEquals("Jimenez", listDoctor.get(2).getLastName());
		assertEquals(new Time(43200000L), listDoctor.get(2).getStartAttentionTime());
		assertEquals("1232323", listDoctor.get(2).getIdentification());
		assertEquals("Cedula", listDoctor.get(2).getTypeIdentification());
		assertEquals(20, listDoctor.get(2).getYearsOfExperience());
		assertEquals(new Time(86400000L), listDoctor.get(2).getFinalAttentionTime());
		assertEquals("DUT234", listDoctor.get(1).getProfessionalCardNumber());	

		assertEquals(4, listDoctor.get(3).getId());
		assertEquals("Brayan" , listDoctor.get(3).getFirstName());
		assertEquals("Gomez", listDoctor.get(3).getLastName());
		assertEquals(new Time(43200000L), listDoctor.get(3).getStartAttentionTime());
		assertEquals("1000350807", listDoctor.get(3).getIdentification());
		assertEquals("Pasaporte", listDoctor.get(3).getTypeIdentification());
		assertEquals(15, listDoctor.get(3).getYearsOfExperience());
		assertEquals(new Time(86400000L), listDoctor.get(3).getFinalAttentionTime());
		assertEquals("SDT323", listDoctor.get(3).getProfessionalCardNumber());	

	}
	
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/doctorSQL/POSTSQL.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void create() {
		
		Doctor doctor = new Doctor();
		doctor.setFirstName("Raul");
		doctor.setLastName("Caseres");
		doctor.setIdentification("1212121");
		doctor.setStartAttentionTime(new Time(25200000L));
		doctor.setFinalAttentionTime(new Time(68400000L));
		doctor.setTypeIdentification("Cedula Extranjera");
		doctor.setProfessionalCardNumber("DUT234");
		doctor.setYearsOfExperience(20);
		
		ResponseEntity<DoctorDTO> response = testRestTemplate.postForEntity("/api/doctor", doctor,DoctorDTO.class);
		
		assertEquals(3, response.getBody().getId());
		assertEquals("Raul" , response.getBody().getFirstName());
		assertEquals("Caseres", response.getBody().getLastName());
		assertEquals(new Time(25200000L), response.getBody().getStartAttentionTime());
		assertEquals("1212121", response.getBody().getIdentification());
		assertEquals("Cedula Extranjera", response.getBody().getTypeIdentification());
		assertEquals(20, response.getBody().getYearsOfExperience());
		assertEquals(new Time(68400000L), response.getBody().getFinalAttentionTime());
		assertEquals("DUT234", response.getBody().getProfessionalCardNumber());	
		assertEquals(201, response.getStatusCodeValue());
		
		Map<String, Object> doctorDTO = jdbcTemplate.queryForMap("SELECT * FROM doctor WHERE ID=?", 3);
		assertEquals(3L, doctorDTO.get("ID"));
		assertEquals("Raul" , doctorDTO.get("FIRST_NAME"));
		assertEquals("Caseres", doctorDTO.get("LAST_NAME"));
		assertEquals(new Time(25200000L),doctorDTO.get("START_ATTENTION_TIME"));
		assertEquals("1212121", doctorDTO.get("IDENTIFICATION"));
		assertEquals("Cedula Extranjera", doctorDTO.get("TYPE_IDENTIFICATION"));
		assertEquals(20, doctorDTO.get("YEARS_OF_EXPERIENCE"));
		assertEquals(new Time(68400000L),doctorDTO.get("FINAL_ATTENTION_TIME"));
		assertEquals("DUT234", doctorDTO.get("PROFESSIONAL_CARD_NUMBER"));	
	}
	
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/doctorSQL/POSTSQL.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void update() {
		
		DoctorDTO doctor = new DoctorDTO();
		doctor.setId(2L);
		doctor.setFirstName("Carlos");
		doctor.setLastName("Rodriguez");
		doctor.setIdentification("1212121");
		doctor.setStartAttentionTime(new Time(25200000L));
		doctor.setFinalAttentionTime(new Time(68400000L));
		doctor.setTypeIdentification("Cedula");
		doctor.setProfessionalCardNumber("DUT234");
		doctor.setYearsOfExperience(20);

		ResponseEntity<DoctorDTO> response = testRestTemplate.exchange("/api/doctor/2",  HttpMethod.PUT,
											createHttpEntity(doctor), DoctorDTO.class);
		
		assertEquals(2, response.getBody().getId());
		assertEquals("Carlos" , response.getBody().getFirstName());
		assertEquals("Rodriguez", response.getBody().getLastName());
		assertEquals(new Time(25200000L), response.getBody().getStartAttentionTime());
		assertEquals("1212121", response.getBody().getIdentification());
		assertEquals("Cedula", response.getBody().getTypeIdentification());
		assertEquals(20, response.getBody().getYearsOfExperience());
		assertEquals(new Time(68400000L), response.getBody().getFinalAttentionTime());
		assertEquals("DUT234", response.getBody().getProfessionalCardNumber());	
		assertEquals(201, response.getStatusCodeValue());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		Map<String, Object> doctorDTO = jdbcTemplate.queryForMap("SELECT * FROM doctor WHERE ID=?", 2);
		assertEquals(2L, doctorDTO.get("ID"));
		assertEquals("Carlos" , doctorDTO.get("FIRST_NAME"));
		assertEquals("Rodriguez", doctorDTO.get("LAST_NAME"));
		assertEquals(new Time(25200000L),doctorDTO.get("START_ATTENTION_TIME"));
		assertEquals("1212121", doctorDTO.get("IDENTIFICATION"));
		assertEquals("Cedula", doctorDTO.get("TYPE_IDENTIFICATION"));
		assertEquals(20, doctorDTO.get("YEARS_OF_EXPERIENCE"));
		assertEquals(new Time(68400000L),doctorDTO.get("FINAL_ATTENTION_TIME"));
		assertEquals("DUT234", doctorDTO.get("PROFESSIONAL_CARD_NUMBER"));	
	}
	

	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/doctorSQL/postSQL.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void deleteById() {
		
		ResponseEntity<HttpStatus> response =testRestTemplate.exchange("/api/doctor/1",  HttpMethod.DELETE, null,
											new ParameterizedTypeReference<HttpStatus>() {} );
		
		int count= jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PATIENT WHERE id=1", Integer.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0, count);
		
		response =testRestTemplate.exchange("/api/doctor/1",  HttpMethod.DELETE, null,
				new ParameterizedTypeReference<HttpStatus>() {} );
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
		
		response =testRestTemplate.exchange("/api/doctor/2",  HttpMethod.DELETE, null,
				new ParameterizedTypeReference<HttpStatus>() {} );
		count= jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PATIENT WHERE id=2", Integer.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0, count);
	}
	
	private HttpEntity<DoctorDTO> createHttpEntity(DoctorDTO doctor) {
		return new HttpEntity<>(doctor, createJsonHeader());
	}
	
	private static HttpHeaders createJsonHeader() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return httpHeaders;
		
	}
}
