package com.smartcare.repository;

import com.smartcare.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {

    List<Doctor> findBySpecializationIgnoreCase(String specialization);

    List<Doctor> findByDepartment_DepartmentId(String departmentId);

    List<Doctor> findByDoctorNameContainingIgnoreCase(String doctorName);
}