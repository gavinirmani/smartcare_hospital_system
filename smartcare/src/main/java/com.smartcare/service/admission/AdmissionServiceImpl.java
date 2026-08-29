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

 public AdmissionServiceImpl(
         AdmissionRepository admissionRepository,
         PatientRepository patientRepository,
         RoomRepository roomRepository) {

  this.admissionRepository = admissionRepository;
  this.patientRepository = patientRepository;
  this.roomRepository = roomRepository;
 }

 @Override
 @Transactional
 public AdmissionResponseDto createAdmission(
         AdmissionRequestDto requestDto) {

  if (admissionRepository.existsById(
          requestDto.getAdmissionId())) {

   throw new DuplicateResourceException(
           "Admission already exists with ID: "
                   + requestDto.getAdmissionId()
   );
  }

  Patient patient = patientRepository.findById(
          requestDto.getPatientId()
  ).orElseThrow(() ->
          new ResourceNotFoundException(
                  "Patient not found: "
                          + requestDto.getPatientId()
          )
  );

  Room room = roomRepository.findById(
          requestDto.getRoomId()
  ).orElseThrow(() ->
          new ResourceNotFoundException(
                  "Room not found: "
                          + requestDto.getRoomId()
          )
  );

  // Check room availability
  if (room.getRoomAvailability()
          == RoomAvailability.OCCUPIED) {

   throw new BusinessRuleException(
           "Room " + room.getRoomId()
                   + " is currently occupied."
   );
  }

  // Occupy the selected room
  room.setRoomAvailability(
          RoomAvailability.OCCUPIED
  );

  roomRepository.save(room);

  Admission admission = new Admission();

  admission.setAdmissionId(
          requestDto.getAdmissionId()
  );

  admission.setPatient(patient);
  admission.setRoom(room);

  admission.setAdmissionDate(
          requestDto.getAdmissionDate() != null
                  ? requestDto.getAdmissionDate()
                  : LocalDate.now()
  );

  admission.setBedNo(
          requestDto.getBedNo()
  );

  admission.setAdmissionStatus(
          AdmissionStatus.Admitted
  );

  admission.setDischargeDate(
          requestDto.getDischargeDate()
  );

  return mapToDto(
          admissionRepository.save(admission)
  );
 }

 @Override
 public AdmissionResponseDto getAdmissionById(
         String admissionId) {

  Admission admission = admissionRepository
          .findById(admissionId)
          .orElseThrow(() ->
                  new ResourceNotFoundException(
                          "Admission not found: "
                                  + admissionId
                  )
          );

  return mapToDto(admission);
 }

 @Override
 public List<AdmissionResponseDto> getAllAdmissions() {

  return admissionRepository.findAll()
          .stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 public List<AdmissionResponseDto> getAdmissionsByPatient(
         String patientId) {

  return admissionRepository
          .findByPatient_Id(patientId)
          .stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 @Transactional
 public AdmissionResponseDto updateAdmission(
         String admissionId,
         AdmissionRequestDto requestDto) {

  Admission admission = admissionRepository
          .findById(admissionId)
          .orElseThrow(() ->
                  new ResourceNotFoundException(
                          "Admission not found: " + admissionId
                  )
          );

  Patient patient = patientRepository.findById(
          requestDto.getPatientId()
  ).orElseThrow(() ->
          new ResourceNotFoundException(
                  "Patient not found: "
                          + requestDto.getPatientId()
          )
  );

  Room newRoom = roomRepository.findById(
          requestDto.getRoomId()
  ).orElseThrow(() ->
          new ResourceNotFoundException(
                  "Room not found: "
                          + requestDto.getRoomId()
          )
  );

  Room oldRoom = admission.getRoom();

  // If changing to a different room
  if (oldRoom != null &&
          !oldRoom.getRoomId().equals(newRoom.getRoomId())) {

   if (newRoom.getRoomAvailability()
           == RoomAvailability.OCCUPIED) {

    throw new BusinessRuleException(
            "Room " + newRoom.getRoomId()
                    + " is currently occupied."
    );
   }

   // Make old room available
   oldRoom.setRoomAvailability(
           RoomAvailability.AVAILABLE
   );
   roomRepository.save(oldRoom);

   // Occupy new room
   newRoom.setRoomAvailability(
           RoomAvailability.OCCUPIED
   );
   roomRepository.save(newRoom);

   admission.setRoom(newRoom);

  } else if (oldRoom == null) {

   if (newRoom.getRoomAvailability()
           == RoomAvailability.OCCUPIED) {

    throw new BusinessRuleException(
            "Room " + newRoom.getRoomId()
                    + " is currently occupied."
    );
   }

   newRoom.setRoomAvailability(
           RoomAvailability.OCCUPIED
   );
   roomRepository.save(newRoom);

   admission.setRoom(newRoom);
  }

  admission.setPatient(patient);

  if (requestDto.getAdmissionDate() != null) {
   admission.setAdmissionDate(
           requestDto.getAdmissionDate()
   );
  }

  if (requestDto.getBedNo() != null) {
   admission.setBedNo(
           requestDto.getBedNo()
   );
  }

  if (requestDto.getDischargeDate() != null) {
   admission.setDischargeDate(
           requestDto.getDischargeDate()
   );
  }

  return mapToDto(
          admissionRepository.save(admission)
  );
 }


 @Override
 @Transactional
 public AdmissionResponseDto dischargePatient(
         String admissionId) {

  Admission admission = admissionRepository
          .findById(admissionId)
          .orElseThrow(() ->
                  new ResourceNotFoundException(
                          "Admission not found: "
                                  + admissionId
                  )
          );

  if (admission.getAdmissionStatus()
          == AdmissionStatus.Discharged) {

   throw new BusinessRuleException(
           "Patient is already discharged."
   );
  }

  admission.setAdmissionStatus(
          AdmissionStatus.Discharged
  );

  admission.setDischargeDate(
          LocalDate.now()
  );

  // Make room available again
  Room room = admission.getRoom();

  if (room != null) {
   room.setRoomAvailability(
           RoomAvailability.AVAILABLE
   );

   roomRepository.save(room);
  }

  return mapToDto(
          admissionRepository.save(admission)
  );
 }

 @Override
 @Transactional
 public void deleteAdmission(String admissionId) {

  Admission admission = admissionRepository
          .findById(admissionId)
          .orElseThrow(() ->
                  new ResourceNotFoundException(
                          "Admission not found: "
                                  + admissionId
                  )
          );

  // Release room before deleting admission
  Room room = admission.getRoom();

  if (room != null) {
   room.setRoomAvailability(
           RoomAvailability.AVAILABLE
   );

   roomRepository.save(room);
  }

  admissionRepository.delete(admission);
 }

 private AdmissionResponseDto mapToDto(
         Admission admission) {

  return new AdmissionResponseDto(
          admission.getAdmissionId(),
          admission.getPatient() != null
                  ? admission.getPatient().getId()
                  : null,
          admission.getRoom() != null
                  ? admission.getRoom().getRoomId()
                  : null,
          admission.getAdmissionDate(),
          admission.getBedNo(),
          admission.getAdmissionStatus(),
          admission.getDischargeDate()
  );
 }
}
