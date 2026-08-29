package com.smartcare.service.laboratory;

import com.smartcare.api.exception.DuplicateResourceException;
import com.smartcare.api.exception.ResourceNotFoundException;
import com.smartcare.dto.LabTestRequestDto;
import com.smartcare.dto.LabTestResponseDto;
import com.smartcare.entity.Doctor;
import com.smartcare.entity.LabTest;
import com.smartcare.entity.LabTestStatus;
import com.smartcare.entity.Patient;
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

 public LabTestServiceImpl(
         LabTestRepository labTestRepository,
         PatientRepository patientRepository,
         DoctorRepository doctorRepository) {

  this.labTestRepository = labTestRepository;
  this.patientRepository = patientRepository;
  this.doctorRepository = doctorRepository;
 }

 // =====================================================
 // CREATE LAB TEST
 // =====================================================

 @Override
 @Transactional
 public LabTestResponseDto createLabTest(
         LabTestRequestDto requestDto) {

  // Check duplicate ID
  if (labTestRepository.existsById(
          requestDto.labTestId())) {

   throw new DuplicateResourceException(
           "Lab test already exists: "
                   + requestDto.labTestId()
   );
  }

  // Find patient
  Patient patient = patientRepository.findById(
          requestDto.patientId()
  ).orElseThrow(() ->
          new ResourceNotFoundException(
                  "Patient not found: "
                          + requestDto.patientId()
          )
  );

  // Find doctor
  Doctor doctor = doctorRepository.findById(
          requestDto.doctorId()
  ).orElseThrow(() ->
          new ResourceNotFoundException(
                  "Doctor not found: "
                          + requestDto.doctorId()
          )
  );

  // Create LabTest entity
  LabTest labTest = new LabTest();

  labTest.setLabTestId(
          requestDto.labTestId()
  );

  labTest.setPatient(patient);

  labTest.setDoctor(doctor);

  labTest.setTestName(
          requestDto.testName()
  );

  labTest.setTestDate(
          requestDto.testDate() != null
                  ? requestDto.testDate()
                  : LocalDate.now()
  );

  labTest.setTestResult(
          requestDto.testResult()
  );

  labTest.setTechnicianName(
          requestDto.technicianName()
  );

  labTest.setLabTestStatus(
          requestDto.labTestStatus() != null
                  ? requestDto.labTestStatus()
                  : LabTestStatus.Pending
  );

  labTest.setTestCharge(
          requestDto.testCharge()
  );

  // Save
  LabTest savedLabTest =
          labTestRepository.save(labTest);

  return mapToDto(savedLabTest);
 }

 // =====================================================
 // GET LAB TEST BY ID
 // =====================================================

 @Override
 public LabTestResponseDto getLabTestById(
         String labTestId) {

  LabTest labTest =
          labTestRepository.findById(labTestId)
                  .orElseThrow(() ->
                          new ResourceNotFoundException(
                                  "Lab test not found: "
                                          + labTestId
                          )
                  );

  return mapToDto(labTest);
 }

 // =====================================================
 // GET ALL LAB TESTS
 // =====================================================

 @Override
 public List<LabTestResponseDto> getAllLabTests() {

  return labTestRepository.findAll()
          .stream()
          .map(this::mapToDto)
          .toList();
 }

 // =====================================================
 // GET LAB TESTS BY PATIENT
 // =====================================================

 @Override
 public List<LabTestResponseDto> getLabTestsByPatient(
         String patientId) {

  return labTestRepository
          .findByPatient_Id(patientId)
          .stream()
          .map(this::mapToDto)
          .toList();
 }

 // =====================================================
 // GET LAB TESTS BY DOCTOR
 // =====================================================

 @Override
 public List<LabTestResponseDto> getLabTestsByDoctor(
         String doctorId) {

  return labTestRepository
          .findByDoctor_Id(doctorId)
          .stream()
          .map(this::mapToDto)
          .toList();
 }

 // =====================================================
 // FULL UPDATE LAB TEST - PUT
 // =====================================================

 @Override
 @Transactional
 public LabTestResponseDto updateLabTest(
         String labTestId,
         LabTestRequestDto requestDto) {

  // Find existing lab test
  LabTest labTest =
          labTestRepository.findById(labTestId)
                  .orElseThrow(() ->
                          new ResourceNotFoundException(
                                  "Lab test not found: "
                                          + labTestId
                          )
                  );

  // Find patient
  Patient patient =
          patientRepository.findById(
                  requestDto.patientId()
          ).orElseThrow(() ->
                  new ResourceNotFoundException(
                          "Patient not found: "
                                  + requestDto.patientId()
                  )
          );

  // Find doctor
  Doctor doctor =
          doctorRepository.findById(
                  requestDto.doctorId()
          ).orElseThrow(() ->
                  new ResourceNotFoundException(
                          "Doctor not found: "
                                  + requestDto.doctorId()
                  )
          );

  // Update patient
  labTest.setPatient(patient);

  // Update doctor
  labTest.setDoctor(doctor);

  // Update test name
  labTest.setTestName(
          requestDto.testName()
  );

  // Update test date
  if (requestDto.testDate() != null) {
   labTest.setTestDate(
           requestDto.testDate()
   );
  }

  // Update test result
  labTest.setTestResult(
          requestDto.testResult()
  );

  // Update technician
  labTest.setTechnicianName(
          requestDto.technicianName()
  );

  // Update status
  if (requestDto.labTestStatus() != null) {
   labTest.setLabTestStatus(
           requestDto.labTestStatus()
   );
  }

  // Update charge
  labTest.setTestCharge(
          requestDto.testCharge()
  );

  // Save updated lab test
  LabTest updatedLabTest =
          labTestRepository.save(labTest);

  return mapToDto(updatedLabTest);
 }

 // =====================================================
 // UPDATE RESULT ONLY - PATCH
 // =====================================================

 @Override
 @Transactional
 public LabTestResponseDto updateLabTestResult(
         String labTestId,
         String result,
         String technicianName) {

  LabTest labTest =
          labTestRepository.findById(labTestId)
                  .orElseThrow(() ->
                          new ResourceNotFoundException(
                                  "Lab test not found: "
                                          + labTestId
                          )
                  );

  labTest.setTestResult(result);

  labTest.setTechnicianName(
          technicianName
  );

  labTest.setLabTestStatus(
          LabTestStatus.Completed
  );

  LabTest updatedLabTest =
          labTestRepository.save(labTest);

  return mapToDto(updatedLabTest);
 }

 // =====================================================
 // DELETE LAB TEST
 // =====================================================

 @Override
 @Transactional
 public void deleteLabTest(
         String labTestId) {

  LabTest labTest =
          labTestRepository.findById(labTestId)
                  .orElseThrow(() ->
                          new ResourceNotFoundException(
                                  "Lab test not found: "
                                          + labTestId
                          )
                  );

  labTestRepository.delete(labTest);
 }

 // =====================================================
 // ENTITY -> RESPONSE DTO
 // =====================================================

 private LabTestResponseDto mapToDto(
         LabTest labTest) {

  return new LabTestResponseDto(

          labTest.getLabTestId(),

          labTest.getPatient() != null
                  ? labTest.getPatient().getId()
                  : null,

          labTest.getDoctor() != null
                  ? labTest.getDoctor().getId()
                  : null,

          labTest.getTestName(),

          labTest.getTestDate(),

          labTest.getTestResult(),

          labTest.getTechnicianName(),

          labTest.getLabTestStatus(),

          labTest.getTestCharge()
  );
 }
}