package com.smartcare.repository;

import com.smartcare.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository
        extends JpaRepository<Bill, String> {

    List<Bill> findByPatient_Id(String patientId);
}