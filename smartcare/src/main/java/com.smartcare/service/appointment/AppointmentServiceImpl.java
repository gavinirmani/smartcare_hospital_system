package com.smartcare.service.appointment;

import com.smartcare.dto.AppointmentRequestDto;
import com.smartcare.dto.AppointmentResponseDto;
import com.smartcare.entity.Appointment;
import com.smartcare.entity.Doctor;
import com.smartcare.entity.Patient;
import com.smartcare.entity.AppointmentStatus;
import com.smartcare.api.exception.BusinessRuleException;
import com.smartcare.api.exception.DuplicateResourceException;
import com.smartcare.api.exception.ResourceNotFoundException;
import com.smartcare.repository.AppointmentRepository;
import com.smartcare.repository.DoctorRepository;
import com.smartcare.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

 private final AppointmentRepository appointmentRepository;
 private final PatientRepository patientRepository;
 private final DoctorRepository doctorRepository;

 public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                               PatientRepository patientRepository,
                               DoctorRepository doctorRepository) {
  this.appointmentRepository = appointmentRepository;
  this.patientRepository = patientRepository;
  this.doctorRepository = doctorRepository;
 }

 @Override
 @Transactional
 public AppointmentResponseDto createAppointment(AppointmentRequestDto requestDto) {
  if (appointmentRepository.existsById(requestDto.getAppointmentId())) {
   throw new DuplicateResourceException("Appointment already exists with ID: " + requestDto.getAppointmentId());
  }

  if (appointmentRepository.existsByDoctor_IdAndAppointmentDateAndAppointmentTime(
          requestDto.getDoctorId(), requestDto.getAppointmentDate(), requestDto.getAppointmentTime())) {
   throw new BusinessRuleException("Doctor is already booked at this date and time.");
  }

  Patient patient = patientRepository.findById(requestDto.getPatientId())
          .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + requestDto.getPatientId()));

  Doctor doctor = doctorRepository.findById(requestDto.getDoctorId())
          .orElseThrow(() -> new ResourceNotFoundException("Doctor not found: " + requestDto.getDoctorId()));

  Appointment appointment = new Appointment();
  appointment.setAppointmentId(requestDto.getAppointmentId());
  appointment.setPatient(patient);
  appointment.setDoctor(doctor);
  appointment.setAppointmentDate(requestDto.getAppointmentDate());
  appointment.setAppointmentTime(requestDto.getAppointmentTime());
  appointment.setConsultationRoom(requestDto.getConsultationRoom());
  appointment.setAppointmentStatus(requestDto.getAppointmentStatus() != null ? requestDto.getAppointmentStatus() : AppointmentStatus.Scheduled);

  return mapToDto(appointmentRepository.save(appointment));
 }

 @Override
 public AppointmentResponseDto getAppointmentById(String appointmentId) {
  Appointment appointment = appointmentRepository.findById(appointmentId)
          .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + appointmentId));
  return mapToDto(appointment);
 }

 @Override
 public List<AppointmentResponseDto> getAllAppointments() {
  return appointmentRepository.findAll().stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 public List<AppointmentResponseDto> getAppointmentsByPatient(String patientId) {
  return appointmentRepository.findByPatient_Id(patientId).stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 public List<AppointmentResponseDto> getAppointmentsByDoctor(String doctorId) {
  return appointmentRepository.findByDoctor_Id(doctorId).stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 @Transactional
 public AppointmentResponseDto updateAppointment(
         String appointmentId,
         AppointmentRequestDto requestDto) {

  Appointment appointment = appointmentRepository.findById(appointmentId)
          .orElseThrow(() ->
                  new ResourceNotFoundException(
                          "Appointment not found with ID: " + appointmentId));

  Patient patient = patientRepository.findById(requestDto.getPatientId())
          .orElseThrow(() ->
                  new ResourceNotFoundException(
                          "Patient not found: " + requestDto.getPatientId()));

  Doctor doctor = doctorRepository.findById(requestDto.getDoctorId())
          .orElseThrow(() ->
                  new ResourceNotFoundException(
                          "Doctor not found: " + requestDto.getDoctorId()));

  // Check doctor availability only if date/time is being changed
  boolean dateChanged =
          !appointment.getAppointmentDate()
                  .equals(requestDto.getAppointmentDate());

  boolean timeChanged =
          !appointment.getAppointmentTime()
                  .equals(requestDto.getAppointmentTime());

  if (dateChanged || timeChanged) {

   boolean alreadyBooked =
           appointmentRepository
                   .existsByDoctor_IdAndAppointmentDateAndAppointmentTime(
                           requestDto.getDoctorId(),
                           requestDto.getAppointmentDate(),
                           requestDto.getAppointmentTime());

   if (alreadyBooked) {
    throw new BusinessRuleException(
            "Doctor is already booked at this date and time.");
   }
  }

  appointment.setPatient(patient);
  appointment.setDoctor(doctor);
  appointment.setAppointmentDate(requestDto.getAppointmentDate());
  appointment.setAppointmentTime(requestDto.getAppointmentTime());
  appointment.setConsultationRoom(requestDto.getConsultationRoom());

  if (requestDto.getAppointmentStatus() != null) {
   appointment.setAppointmentStatus(
           requestDto.getAppointmentStatus());
  }

  return mapToDto(appointmentRepository.save(appointment));
 }

 @Override
 public AppointmentResponseDto updateAppointmentStatus(String appointmentId, String status) {
  return null;
 }

 @Override
 @Transactional
 public void deleteAppointment(String appointmentId) {
  Appointment appointment = appointmentRepository.findById(appointmentId)
          .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + appointmentId));
  appointmentRepository.delete(appointment);
 }

 private AppointmentResponseDto mapToDto(Appointment appointment) {
  return new AppointmentResponseDto(
          appointment.getAppointmentId(),
          appointment.getPatient() != null ? appointment.getPatient().getId() : null,
          appointment.getPatient() != null ? appointment.getPatient().getName() : null,
          appointment.getDoctor() != null ? appointment.getDoctor().getId() : null,
          appointment.getDoctor() != null ? appointment.getDoctor().getName() : null,
          appointment.getAppointmentDate(),
          appointment.getAppointmentTime(),
          appointment.getConsultationRoom(),
          appointment.getAppointmentStatus()
  );
 }
}