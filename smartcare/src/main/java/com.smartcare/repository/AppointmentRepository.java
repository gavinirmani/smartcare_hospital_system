package com.smartcare.repository;

import com.smartcare.entity.Appointment;
import com.smartcare.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    boolean existsByDoctor_IdAndAppointmentDateAndAppointmentTime(String doctorId, LocalDate appointmentDate, LocalTime appointmentTime);
    List<Appointment> findByPatient_Id(String patientId);
    List<Appointment> findByDoctor_Id(String doctorId);
    List<Appointment> findByAppointmentStatus(AppointmentStatus status);
}