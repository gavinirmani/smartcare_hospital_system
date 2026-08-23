package com.smartcare.service.patient;

import com.smartcare.entity.Patient;
import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();
    Patient getPatientById(String id);
    Patient createPatient(Patient patient);
    Patient updatePatient(String id, Patient patientDetails);
    void deletePatient(String id);
}