package com.smartcare.repository;

import com.smartcare.entity.Bill;
import com.smartcare.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, String> {
    List<Bill> findByPatient_Id(String patientId);
    List<Bill> findByAppointment_AppointmentId(String appointmentId);
    List<Bill> findByPaymentStatus(PaymentStatus paymentStatus);
}