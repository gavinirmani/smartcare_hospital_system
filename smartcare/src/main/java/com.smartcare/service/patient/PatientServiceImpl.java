package com.smartcare.service.patient;

import com.smartcare.entity.Patient;
import com.smartcare.api.exception.DuplicateResourceException;
import com.smartcare.api.exception.ResourceNotFoundException;
import com.smartcare.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(String id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));
    }

    @Override
    public Patient createPatient(Patient patient) {
        if (patient.getPatientId() == null || patient.getPatientId().trim().isEmpty()) {
            throw new IllegalArgumentException("Patient ID cannot be empty");
        }
        if (patientRepository.existsById(patient.getPatientId())) {
            throw new DuplicateResourceException("Patient already exists with ID: " + patient.getPatientId());
        }
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(String id, Patient patientDetails) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));

        existingPatient.setFullName(patientDetails.getFullName());
        existingPatient.setDob(patientDetails.getDob());
        existingPatient.setGender(patientDetails.getGender());
        existingPatient.setAddress(patientDetails.getAddress());
        existingPatient.setContactNumber(patientDetails.getContactNumber());
        existingPatient.setBloodGroup(patientDetails.getBloodGroup());
        existingPatient.setEmergencyContact(patientDetails.getEmergencyContact());

        return patientRepository.save(existingPatient);
    }

    @Override
    public void deletePatient(String id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with ID: " + id);
        }
        patientRepository.deleteById(id);
    }

    public Patient registerPatient(Patient patient) {
        return createPatient(patient);
    }
}