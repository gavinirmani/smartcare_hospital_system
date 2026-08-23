package com.smartcare.service.laboratory;

import com.smartcare.dto.LabTestRequestDto;
import com.smartcare.dto.LabTestResponseDto;
import com.smartcare.entity.Doctor;
import com.smartcare.entity.LabTest;
import com.smartcare.entity.Patient;
import com.smartcare.entity.LabTestStatus;
import com.smartcare.api.exception.DuplicateResourceException;
import com.smartcare.api.exception.ResourceNotFoundException;
import com.smartcare.repository.DoctorRepository;
import com.smartcare.repository.LabTestRepository;
import com.smartcare.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LabTestServiceImpl implements LabTestService {

 private final LabTestRepository labTestRepository;
 private final PatientRepository patientRepository;
 private final DoctorRepository doctorRepository;

 public LabTestServiceImpl(LabTestRepository labTestRepository,
                           PatientRepository patientRepository,
                           DoctorRepository doctorRepository) {
  this.labTestRepository = labTestRepository;
  this.patientRepository = patientRepository;
  this.doctorRepository = doctorRepository;
 }

 @Override
 @Transactional
 public LabTestResponseDto createLabTest(LabTestRequestDto requestDto) {
  if (labTestRepository.existsById(requestDto.labTestId())) {
   throw new DuplicateResourceException("Lab test already exists with ID: " + requestDto.labTestId());
  }

  Patient patient = patientRepository.findById(requestDto.patientId())
          .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + requestDto.patientId()));

  Doctor doctor = doctorRepository.findById(requestDto.doctorId())
          .orElseThrow(() -> new ResourceNotFoundException("Doctor not found: " + requestDto.doctorId()));

  LabTest labTest = new LabTest();
  labTest.setLabTestId(requestDto.labTestId());
  labTest.setPatient(patient);
  labTest.setDoctor(doctor);
  labTest.setTestName(requestDto.testName());
  labTest.setTestDate(requestDto.testDate() != null ? requestDto.testDate() : LocalDate.now());
  labTest.setTestResult(requestDto.testResult());
  labTest.setTechnicianName(requestDto.technicianName());
  labTest.setLabTestStatus(requestDto.labTestStatus() != null ? requestDto.labTestStatus() : LabTestStatus.Pending);
  labTest.setTestCharge(requestDto.testCharge());

  return mapToDto(labTestRepository.save(labTest));
 }

 @Override
 public LabTestResponseDto getLabTestById(String labTestId) {
  LabTest labTest = labTestRepository.findById(labTestId)
          .orElseThrow(() -> new ResourceNotFoundException("Lab test not found with ID: " + labTestId));
  return mapToDto(labTest);
 }

 @Override
 public List<LabTestResponseDto> getAllLabTests() {
  return labTestRepository.findAll().stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 public List<LabTestResponseDto> getLabTestsByPatient(String patientId) {
  return labTestRepository.findByPatient_Id(patientId).stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 public List<LabTestResponseDto> getLabTestsByDoctor(String doctorId) {
  return labTestRepository.findByDoctor_Id(doctorId).stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 @Transactional
 public LabTestResponseDto updateLabTestResult(String labTestId, String result, String technicianName) {
  LabTest labTest = labTestRepository.findById(labTestId)
          .orElseThrow(() -> new ResourceNotFoundException("Lab test not found with ID: " + labTestId));

  labTest.setTestResult(result);
  labTest.setTechnicianName(technicianName);
  labTest.setLabTestStatus(LabTestStatus.Completed);

  return mapToDto(labTestRepository.save(labTest));
 }

 @Override
 @Transactional
 public void deleteLabTest(String labTestId) {
  LabTest labTest = labTestRepository.findById(labTestId)
          .orElseThrow(() -> new ResourceNotFoundException("Lab test not found with ID: " + labTestId));
  labTestRepository.delete(labTest);
 }

 private LabTestResponseDto mapToDto(LabTest labTest) {
  return new LabTestResponseDto(
          labTest.getLabTestId(),
          labTest.getPatient() != null ? labTest.getPatient().getId() : null,
          labTest.getPatient() != null ? labTest.getPatient().getName() : null,
          labTest.getDoctor() != null ? labTest.getDoctor().getId() : null,
          labTest.getDoctor() != null ? labTest.getDoctor().getName() : null,
          labTest.getTestName(),
          labTest.getTestDate(),
          labTest.getTestResult(),
          labTest.getTechnicianName(),
          labTest.getLabTestStatus(),
          labTest.getTestCharge()
  );
 }
}