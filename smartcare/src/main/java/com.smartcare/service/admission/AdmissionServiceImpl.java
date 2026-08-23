package com.smartcare.service.admission;

import com.smartcare.api.exception.BusinessRuleException;
import com.smartcare.api.exception.DuplicateResourceException;
import com.smartcare.api.exception.ResourceNotFoundException;
import com.smartcare.dto.AdmissionRequestDto;
import com.smartcare.dto.AdmissionResponseDto;
import com.smartcare.entity.*;
import com.smartcare.repository.AdmissionRepository;
import com.smartcare.repository.PatientRepository;
import com.smartcare.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdmissionServiceImpl implements AdmissionService {

 private final AdmissionRepository admissionRepository;
 private final PatientRepository patientRepository;
 private final RoomRepository roomRepository;

 public AdmissionServiceImpl(AdmissionRepository admissionRepository,
                             PatientRepository patientRepository,
                             RoomRepository roomRepository) {
  this.admissionRepository = admissionRepository;
  this.patientRepository = patientRepository;
  this.roomRepository = roomRepository;
 }

 @Override
 @Transactional
 public AdmissionResponseDto createAdmission(AdmissionRequestDto requestDto) {
  if (admissionRepository.existsById(requestDto.getAdmissionId())) {
   throw new DuplicateResourceException("Admission already exists with ID: " + requestDto.getAdmissionId());
  }

  Patient patient = patientRepository.findById(requestDto.getPatientId())
          .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + requestDto.getPatientId()));

  Room room = roomRepository.findById(requestDto.getRoomId())
          .orElseThrow(() -> new ResourceNotFoundException("Room not found: " + requestDto.getRoomId()));

  if (room.getRoomAvailability() == RoomAvailability.Occupied) {
   throw new BusinessRuleException("Room " + room.getRoomId() + " is currently occupied.");
  }

  room.setRoomAvailability(RoomAvailability.Occupied);
  roomRepository.save(room);

  Admission admission = new Admission();
  admission.setAdmissionId(requestDto.getAdmissionId());
  admission.setPatient(patient);
  admission.setRoom(room);
  admission.setAdmissionDate(requestDto.getAdmissionDate() != null ? requestDto.getAdmissionDate() : LocalDate.now());
  admission.setBedNo(requestDto.getBedNo());
  admission.setAdmissionStatus(AdmissionStatus.Admitted);
  admission.setDischargeDate(requestDto.getDischargeDate());

  return mapToDto(admissionRepository.save(admission));
 }

 @Override
 public AdmissionResponseDto getAdmissionById(String admissionId) {
  Admission admission = admissionRepository.findById(admissionId)
          .orElseThrow(() -> new ResourceNotFoundException("Admission not found with ID: " + admissionId));
  return mapToDto(admission);
 }

 @Override
 public List<AdmissionResponseDto> getAllAdmissions() {
  return admissionRepository.findAll().stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 public List<AdmissionResponseDto> getAdmissionsByPatient(String patientId) {
  return admissionRepository.findByPatient_Id(patientId).stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 @Transactional
 public AdmissionResponseDto dischargePatient(String admissionId) {
  Admission admission = admissionRepository.findById(admissionId)
          .orElseThrow(() -> new ResourceNotFoundException("Admission not found with ID: " + admissionId));

  if (admission.getAdmissionStatus() == AdmissionStatus.Discharged) {
   throw new BusinessRuleException("Patient has already been discharged.");
  }

  admission.setAdmissionStatus(AdmissionStatus.Discharged);
  admission.setDischargeDate(LocalDate.now());

  Room room = admission.getRoom();
  if (room != null) {
   room.setRoomAvailability(RoomAvailability.Available);
   roomRepository.save(room);
  }

  return mapToDto(admissionRepository.save(admission));
 }

 @Override
 @Transactional
 public void deleteAdmission(String admissionId) {
  Admission admission = admissionRepository.findById(admissionId)
          .orElseThrow(() -> new ResourceNotFoundException("Admission not found with ID: " + admissionId));
  admissionRepository.delete(admission);
 }

 @Override
 public Admission saveAdmission(Admission admission) {
  return null;
 }

 private AdmissionResponseDto mapToDto(Admission admission) {
  return new AdmissionResponseDto(
          admission.getAdmissionId(),
          admission.getPatient() != null ? admission.getPatient().getId() : null,
          admission.getPatient() != null ? admission.getPatient().getName() : null,
          admission.getRoom() != null ? admission.getRoom().getRoomId() : null,
          admission.getAdmissionDate(),
          admission.getBedNo(),
          admission.getAdmissionStatus(),
          admission.getDischargeDate()
  );
 }
}
