package com.smartcare.service.impl.test;

import com.smartcare.api.exception.DuplicateResourceException;
import com.smartcare.api.exception.ResourceNotFoundException;
import com.smartcare.entity.Gender;
import com.smartcare.entity.Patient;
import com.smartcare.repository.PatientRepository;
import com.smartcare.service.patient.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient patient;
    private Patient requestDto;

    @BeforeEach
    void setUp() {
        // Setup standard patient data (Ensuring bloodGroup is NOT null)
        patient = new Patient();
        patient.setPatientId("P1");
        patient.setFullName("Kusal Mendis");
        patient.setDob(LocalDate.of(1995, 2, 16));
        patient.setGender(Gender.Male); // Assuming you have an enum
        patient.setAddress("Colombo");
        patient.setContactNumber("0771112223");
        patient.setBloodGroup("A+"); // Critical field
        patient.setEmergencyContact("0770000001");

        // Setup Request DTO matching the JSON body
        requestDto = new Patient();
        requestDto.setPatientId("P1");
        requestDto.setFullName("Kusal Mendis");
        requestDto.setDob(LocalDate.parse("1995-02-16"));
        requestDto.setGender(Gender.valueOf("Male"));
        requestDto.setAddress("Colombo");
        requestDto.setContactNumber("0771112223");
        requestDto.setBloodGroup("A+"); // Critical field
        requestDto.setEmergencyContact("0770000001");
    }

    // 1. Register Patient - Success
    @Test
    void registerPatient_success() {
        when(patientRepository.existsById("P1")).thenReturn(false);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient result = patientService.registerPatient(requestDto);

        assertNotNull(result);
        assertEquals("P1", result.getPatientId());
        assertEquals("Kusal Mendis", result.getFullName());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    // 2. Register Patient - Duplicate ID
    @Test
    void registerPatient_duplicateId_throwsException() {
        when(patientRepository.existsById("P1")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> patientService.registerPatient(requestDto));
        verify(patientRepository, never()).save(any(Patient.class));
    }

    // 3. Get Patient By ID - Success
    @Test
    void getPatientById_success() {
        when(patientRepository.findById("P1")).thenReturn(Optional.of(patient));

        Patient result = patientService.getPatientById("P1");

        assertNotNull(result);
        assertEquals("A+", result.getBloodGroup()); // Ensures your NOT NULL field is mapped
    }

    // 4. Get Patient By ID - Not Found
    @Test
    void getPatientById_notFound_throwsException() {
        when(patientRepository.findById("P99")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.getPatientById("P99"));
    }

    // 5. Get All Patients - Success
    @Test
    void getAllPatients_success() {
        when(patientRepository.findAll()).thenReturn(List.of(patient));

        List<Patient> result = patientService.getAllPatients();

        assertEquals(1, result.size());
        assertEquals("P1", result.get(0).getPatientId());
    }

    // 6. Get All Patients - Empty List
    @Test
    void getAllPatients_emptyList() {
        when(patientRepository.findAll()).thenReturn(Collections.emptyList());

        List<Patient> result = patientService.getAllPatients();

        assertTrue(result.isEmpty());
    }

    // 7. Update Patient - Success (This specifically fixes your 'blood_group cannot be null' issue)
    @Test
    void updatePatient_success() {
        when(patientRepository.findById("P1")).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // Change some details in the DTO
        requestDto.setAddress("Kandy");
        requestDto.setBloodGroup("B-");

        Patient result = patientService.updatePatient("P1", requestDto);

        assertEquals("Kandy", result.getAddress());
        // This is the key assertion to make sure Blood Group is passed!
        assertEquals("B-", result.getBloodGroup());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    // 8. Update Patient - Not Found
    @Test
    void updatePatient_notFound_throwsException() {
        when(patientRepository.findById("P99")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.updatePatient("P99", requestDto));
        verify(patientRepository, never()).save(any(Patient.class));
    }

    // 9. Delete Patient - Success
    @Test
    void deletePatient_success() {
        when(patientRepository.existsById("P1")).thenReturn(true);

        // Ensure it does not throw an exception
        assertDoesNotThrow(() -> patientService.deletePatient("P1"));

        verify(patientRepository, times(1)).deleteById("P1");
    }

    // 10. Delete Patient - Not Found
    @Test
    void deletePatient_notFound_throwsException() {
        when(patientRepository.existsById("P99")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> patientService.deletePatient("P99"));
        verify(patientRepository, never()).deleteById(anyString());
    }
}
