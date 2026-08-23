package com.smartcare.repository;

import com.smartcare.entity.TreatmentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentRecordRepository extends JpaRepository<TreatmentRecord, String> {
    List<TreatmentRecord> findByPatient_Id(String patientId);
    List<TreatmentRecord> findByDoctor_Id(String doctorId);
}