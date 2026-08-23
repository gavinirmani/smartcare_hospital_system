package com.smartcare.service.treatment;

import com.smartcare.dto.TreatmentRecordRequestDto;
import com.smartcare.dto.TreatmentRecordResponseDto;
import com.smartcare.entity.Doctor;
import com.smartcare.entity.Patient;
import com.smartcare.entity.TreatmentRecord;
import com.smartcare.api.exception.DuplicateResourceException;
import com.smartcare.api.exception.ResourceNotFoundException;
import com.smartcare.repository.DoctorRepository;
import com.smartcare.repository.PatientRepository;
import com.smartcare.repository.TreatmentRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TreatmentRecordServiceImpl implements TreatmentRecordService {

 private final TreatmentRecordRepository treatmentRecordRepository;
 private final PatientRepository patientRepository;
 private final DoctorRepository doctorRepository;

 public TreatmentRecordServiceImpl(TreatmentRecordRepository treatmentRecordRepository,
                                   PatientRepository patientRepository,
                                   DoctorRepository doctorRepository) {
  this.treatmentRecordRepository = treatmentRecordRepository;
  this.patientRepository = patientRepository;
  this.doctorRepository = doctorRepository;
 }

 @Transactional
 @Override
 public TreatmentRecordResponseDto createTreatmentRecord(TreatmentRecordRequestDto requestDto) {
  if (treatmentRecordRepository.existsById(requestDto.treatmentId())) {
   throw new DuplicateResourceException("Treatment record already exists with ID: " + requestDto.treatmentId());
  }

  Patient patient = patientRepository.findById(requestDto.patientId())
          .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + requestDto.patientId()));

  Doctor doctor = doctorRepository.findById(requestDto.doctorId())
          .orElseThrow(() -> new ResourceNotFoundException("Doctor not found: " + requestDto.doctorId()));

  TreatmentRecord record = new TreatmentRecord();
  record.setTreatmentId(requestDto.treatmentId());
  record.setPatient(patient);
  record.setDoctor(doctor);
  record.setDiagnosis(requestDto.diagnosis());
  record.setPrescriptionDetails(requestDto.prescriptionDetails());
  record.setTreatmentNotes(requestDto.treatmentNotes());
  record.setTreatmentDate(requestDto.treatmentDate() != null ? requestDto.treatmentDate() : LocalDate.now());

  return mapToDto(treatmentRecordRepository.save(record));
 }

 @Override
 public TreatmentRecordResponseDto getTreatmentRecordById(String treatmentId) {
  TreatmentRecord record = treatmentRecordRepository.findById(treatmentId)
          .orElseThrow(() -> new ResourceNotFoundException("Treatment record not found with ID: " + treatmentId));
  return mapToDto(record);
 }

 @Override
 public List<TreatmentRecordResponseDto> getAllTreatmentRecords() {
  return treatmentRecordRepository.findAll().stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 public List<TreatmentRecordResponseDto> getTreatmentRecordsByPatient(String patientId) {
  return treatmentRecordRepository.findByPatient_Id(patientId).stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 public List<TreatmentRecordResponseDto> getTreatmentRecordsByDoctor(String doctorId) {
  return treatmentRecordRepository.findByDoctor_Id(doctorId).stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 @Transactional
 public TreatmentRecordResponseDto updateTreatmentRecord(String treatmentId, TreatmentRecordRequestDto requestDto) {
  TreatmentRecord record = treatmentRecordRepository.findById(treatmentId)
          .orElseThrow(() -> new ResourceNotFoundException("Treatment record not found with ID: " + treatmentId));

  record.setDiagnosis(requestDto.diagnosis());
  record.setPrescriptionDetails(requestDto.prescriptionDetails());
  record.setTreatmentNotes(requestDto.treatmentNotes());
  if (requestDto.treatmentDate() != null) {
   record.setTreatmentDate(requestDto.treatmentDate());
  }

  return mapToDto(treatmentRecordRepository.save(record));
 }

 @Override
 @Transactional
 public void deleteTreatmentRecord(String treatmentId) {
  TreatmentRecord record = treatmentRecordRepository.findById(treatmentId)
          .orElseThrow(() -> new ResourceNotFoundException("Treatment record not found with ID: " + treatmentId));
  treatmentRecordRepository.delete(record);
 }

 private TreatmentRecordResponseDto mapToDto(TreatmentRecord record) {
  return new TreatmentRecordResponseDto(
          record.getTreatmentId(),
          record.getPatient() != null ? record.getPatient().getId() : null,
          record.getPatient() != null ? record.getPatient().getName() : null,
          record.getDoctor() != null ? record.getDoctor().getId() : null,
          record.getDoctor() != null ? record.getDoctor().getName() : null,
          record.getDiagnosis(),
          record.getPrescriptionDetails(),
          record.getTreatmentNotes(),
          record.getTreatmentDate()
  );
 }
}