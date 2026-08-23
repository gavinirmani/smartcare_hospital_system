package com.smartcare.service.appointment;

import com.smartcare.dto.AppointmentRequestDto;
import com.smartcare.dto.AppointmentResponseDto;

import java.util.List;

public interface AppointmentService {

    AppointmentResponseDto createAppointment(
            AppointmentRequestDto requestDto);

    AppointmentResponseDto getAppointmentById(
            String appointmentId);

    List<AppointmentResponseDto> getAllAppointments();

    List<AppointmentResponseDto> getAppointmentsByPatient(
            String patientId);

    List<AppointmentResponseDto> getAppointmentsByDoctor(
            String doctorId);

    AppointmentResponseDto updateAppointment(
            String appointmentId,
            AppointmentRequestDto requestDto);

    AppointmentResponseDto updateAppointmentStatus(
            String appointmentId,
            String status);

    void deleteAppointment(String appointmentId);
}