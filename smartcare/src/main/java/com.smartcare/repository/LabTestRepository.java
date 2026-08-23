package com.smartcare.repository;

import com.smartcare.entity.LabTest;
import com.smartcare.entity.LabTestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabTestRepository extends JpaRepository<LabTest, String> {
    List<LabTest> findByPatient_Id(String patientId);
    List<LabTest> findByDoctor_Id(String doctorId);
    List<LabTest> findByLabTestStatus(LabTestStatus labTestStatus);
}