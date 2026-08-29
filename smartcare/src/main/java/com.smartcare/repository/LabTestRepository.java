package com.smartcare.repository;

import com.smartcare.entity.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabTestRepository
        extends JpaRepository<LabTest, String> {

    List<LabTest> findByPatient_Id(String patientId);

    List<LabTest> findByDoctor_Id(String doctorId);
}