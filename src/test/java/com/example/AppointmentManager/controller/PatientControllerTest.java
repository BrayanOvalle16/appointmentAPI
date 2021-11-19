package com.example.AppointmentManager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
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

import com.example.AppointmentManager.dto.PatientDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(
		locations = "classpath:/resorces/application.properties"
		)
class PatientControllerTest {
	
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/patientSQL/getSQL.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void update() {
		
		PatientDTO patient = new PatientDTO();
		patient.setId(1L);
		patient.setIdentification("112316");
		patient.setDateOfBirth(new Date(1012521600000L));
		patient.setFullName("Jose");
		patient.setHealthcareProvider("Colsanitas");
		patient.setClinicHistory("Pie Roto");
		patient.setTypeIdentification("Cedula");

		ResponseEntity<PatientDTO> response = testRestTemplate.exchange("/api/patient/1",  HttpMethod.PUT,
											createHttpEntity(patient), PatientDTO.class);
		
		Map<String, Object> patientdto = jdbcTemplate.queryForMap("SELECT * FROM PATIENT WHERE ID=?", 1);
		
		assertEquals(1L, patientdto.get("ID"));
		assertEquals("Jose", patientdto.get("FULL_NAME"));
		assertEquals("Cedula", patientdto.get("TYPE_IDENTIFICATION"));
		assertEquals("Colsanitas", patientdto.get("HEALTHCARE_PROVIDER"));
		assertEquals("112316", patientdto.get("IDENTIFICATION"));	
		assertEquals("Pie Roto", patientdto.get("CLINIC_HISTORY"));
		assertEquals(200, response.getStatusCodeValue());
	}

	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/patientSQL/getSQL.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void getPatientById() {
		
		ResponseEntity<PatientDTO> response = testRestTemplate.getForEntity("/api/patient/1", PatientDTO.class);

		assertEquals(1, response.getBody().getId());
		assertEquals("Jose", response.getBody().getFullName());
		assertEquals("122323232", response.getBody().getIdentification());
		assertEquals("Cedula", response.getBody().getTypeIdentification());
		assertEquals("Famisanar", response.getBody().getHealthcareProvider());
		assertEquals("Brazo Roto", response.getBody().getClinicHistory());	
		assertEquals(200, response.getStatusCodeValue());
		
		response = testRestTemplate.getForEntity("/api/patient/2", PatientDTO.class);
		
		assertEquals(2, response.getBody().getId());
		assertEquals("Brayan", response.getBody().getFullName());
		assertEquals("122323232", response.getBody().getIdentification());
		assertEquals("Cedula", response.getBody().getTypeIdentification());
		assertEquals("Compensar", response.getBody().getHealthcareProvider());
		assertEquals("Pie Roto", response.getBody().getClinicHistory());	
		assertEquals(200, response.getStatusCodeValue());
		
		response = testRestTemplate.getForEntity("/api/patient/123", PatientDTO.class);
		assertEquals(404, response.getStatusCodeValue());
		
	}

	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/patientSQL/getSQL.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void getPatientList() {
		
		ResponseEntity<List<PatientDTO>> response = testRestTemplate.exchange("/api/patient",  HttpMethod.GET, null,
													new ParameterizedTypeReference<List<PatientDTO>>() {} );
		
		List<PatientDTO> listPatient = response.getBody();
		
		assertEquals(1, response.getBody().get(0).getId());
		assertEquals("Jose", listPatient.get(0).getFullName());
		assertEquals("Cedula", listPatient.get(0).getTypeIdentification());
		assertEquals("Famisanar", listPatient.get(0).getHealthcareProvider());
		assertEquals("Brazo Roto", listPatient.get(0).getClinicHistory());	
		assertEquals(200, response.getStatusCodeValue());
	
		assertEquals(2, listPatient.get(1).getId());
		assertEquals("Brayan", listPatient.get(1).getFullName());
		assertEquals("Cedula", listPatient.get(1).getTypeIdentification());
		assertEquals("Compensar", listPatient.get(1).getHealthcareProvider());
		assertEquals("Pie Roto", listPatient.get(1).getClinicHistory());	
		assertEquals(200, response.getStatusCodeValue());
		
		assertEquals(3, listPatient.get(2).getId());
		assertEquals("Carlos", listPatient.get(2).getFullName());
		assertEquals("Cedula Extranjera", listPatient.get(2).getTypeIdentification());
		assertEquals("Colsanitas", listPatient.get(2).getHealthcareProvider());
		assertEquals("Clavicula Roto", listPatient.get(2).getClinicHistory());	
		assertEquals(200, response.getStatusCodeValue());
		
		assertEquals(4, listPatient.get(3).getId());
		assertEquals("Rodolfo", listPatient.get(3).getFullName());
		assertEquals("Cedula", listPatient.get(3).getTypeIdentification());
		assertEquals("Compensar", listPatient.get(3).getHealthcareProvider());
		assertEquals("Pie Roto", listPatient.get(3).getClinicHistory());	
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/patientSQL/postSQL.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void create() {
		
		PatientDTO patient = new PatientDTO();
		patient.setIdentification("112316");
		patient.setDateOfBirth(new Date(1012521600000L));
		patient.setFullName("Rodrigo");
		patient.setHealthcareProvider("Famisanar");
		patient.setTypeIdentification("Cedula");
		
		ResponseEntity<PatientDTO> response = testRestTemplate.postForEntity("/api/patient", patient,PatientDTO.class);
		Map<String, Object> patientdto = jdbcTemplate.queryForMap("SELECT * FROM PATIENT WHERE ID=?", 2);
		
		assertEquals(2L, patientdto.get("ID"));
		assertEquals("Rodrigo", patientdto.get("FULL_NAME"));
		assertEquals("Cedula", patientdto.get("TYPE_IDENTIFICATION"));
		assertEquals("Famisanar", patientdto.get("HEALTHCARE_PROVIDER"));
		assertEquals("112316", patientdto.get("IDENTIFICATION"));	
		assertEquals(201, response.getStatusCodeValue());

	}
	
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts ="/resorces/doctorSQL/postSQL.sql")
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts ="/resorces/after.sql")
	@Test
	public void deleteById() {
		
		ResponseEntity<HttpStatus> response =testRestTemplate.exchange("/api/doctor/1",  HttpMethod.DELETE, null,
											HttpStatus.class );
		
		int count= jdbcTemplate.queryForObject("SELECT COUNT(*) FROM DOCTOR WHERE id=1", Integer.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0, count);
		
		response =testRestTemplate.exchange("/api/doctor/2",  HttpMethod.DELETE, null,
				HttpStatus.class );
		
		count= jdbcTemplate.queryForObject("SELECT COUNT(*) FROM DOCTOR WHERE id=2", Integer.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0, count);
		
		response =testRestTemplate.exchange("/api/doctor/1",  HttpMethod.DELETE, null,
				new ParameterizedTypeReference<HttpStatus>() {} );
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
	}
	
	private HttpEntity<PatientDTO> createHttpEntity(PatientDTO patient) {
		return new HttpEntity<>(patient, createJsonHeader());
	}
	
	private static HttpHeaders createJsonHeader() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return httpHeaders;
	}
	
}

