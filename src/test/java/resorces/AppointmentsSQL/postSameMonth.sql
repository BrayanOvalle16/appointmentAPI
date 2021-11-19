INSERT INTO PATIENT 
				(  FULL_NAME, DATE_OF_BIRTH, IDENTIFICATION, TYPE_IDENTIFICATION, HEALTHCARE_PROVIDER, CLINIC_HISTORY) VALUES 
				("Jose", "2002-02-01", "122323232", "Cedula", "Famisanar", "Pie Roto");
				
INSERT INTO DOCTOR (final_attention_time, first_name, identification, last_name, professional_card_number, 
					specialism, start_attention_time, type_identification, `years_of_experience`) VALUES 
					('19:00:00', 'Jose', '1232323', 'Ovalle', 'DUT234', 'Ocomentra', '07:00:00', 'Cedula', '15');

INSERT INTO APPOINTMENT (date_appointment, start_time, doctor_id, patient_id) VALUES 
						('2021-11-02', '8:00:00', '1', '1');

INSERT INTO APPOINTMENT (date_appointment, start_time, doctor_id, patient_id) VALUES 
						('2021-11-03', '8:00:00', '1', '1');