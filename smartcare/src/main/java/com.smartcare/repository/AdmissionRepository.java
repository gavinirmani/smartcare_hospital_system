package com.smartcare.repository;

import com.smartcare.entity.Admission;
import com.smartcare.entity.AdmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, String> {
    List<Admission> findByPatient_Id(String patientId);
    List<Admission> findByRoom_RoomId(String roomId);
    List<Admission> findByAdmissionStatus(AdmissionStatus admissionStatus);
}