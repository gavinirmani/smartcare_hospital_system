package com.smartcare.service.room;

import com.smartcare.dto.RoomRequestDto;
import com.smartcare.dto.RoomResponseDto;
import com.smartcare.entity.Room;
import com.smartcare.entity.RoomAvailability;
import com.smartcare.entity.RoomCategory;
import com.smartcare.repository.RoomRepository;
import com.smartcare.api.exception.DuplicateResourceException;
import com.smartcare.api.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

 private final RoomRepository roomRepository;

 @Override
 public RoomResponseDto createRoom(RoomRequestDto requestDto) {

  if (roomRepository.existsById(requestDto.getRoomId())) {
   throw new DuplicateResourceException(
           "Room already exists with ID: "
                   + requestDto.getRoomId()
   );
  }

  Room room = new Room();

  room.setRoomId(requestDto.getRoomId());
  room.setCategory(requestDto.getCategory());

  if (requestDto.getRoomAvailability() == null) {
   room.setRoomAvailability(RoomAvailability.AVAILABLE);
  } else {
   room.setRoomAvailability(
           requestDto.getRoomAvailability()
   );
  }

  room.setChargePerDay(requestDto.getChargePerDay());

  Room savedRoom = roomRepository.save(room);

  return convertToResponse(savedRoom);
 }

 @Override
 @Transactional(readOnly = true)
 public RoomResponseDto getRoomById(String roomId) {

  Room room = roomRepository.findById(roomId)
          .orElseThrow(() ->
                  new ResourceNotFoundException(
                          "Room not found with ID: " + roomId
                  )
          );

  return convertToResponse(room);
 }

 @Override
 @Transactional(readOnly = true)
 public List<RoomResponseDto> getAllRooms() {

  return roomRepository.findAll()
          .stream()
          .map(this::convertToResponse)
          .collect(Collectors.toList());
 }

 @Override
 @Transactional(readOnly = true)
 public List<RoomResponseDto> getRoomsByCategory(
         String category
 ) {

  RoomCategory roomCategory;

  try {
   roomCategory =
           RoomCategory.fromValue(category);
  } catch (IllegalArgumentException e) {
   throw new IllegalArgumentException(
           "Invalid room category: " + category
   );
  }

  return roomRepository
          .findByCategory(roomCategory)
          .stream()
          .map(this::convertToResponse)
          .collect(Collectors.toList());
 }

 @Override
 @Transactional(readOnly = true)
 public List<RoomResponseDto> getRoomsByAvailability(
         String availability
 ) {

  RoomAvailability roomAvailability;

  try {
   roomAvailability =
           RoomAvailability.fromValue(availability);
  } catch (IllegalArgumentException e) {
   throw new IllegalArgumentException(
           "Invalid room availability: "
                   + availability
   );
  }

  return roomRepository
          .findByRoomAvailability(roomAvailability)
          .stream()
          .map(this::convertToResponse)
          .collect(Collectors.toList());
 }

 @Override
 public RoomResponseDto updateRoom(
         String roomId,
         RoomRequestDto requestDto
 ) {

  Room room = roomRepository.findById(roomId)
          .orElseThrow(() ->
                  new ResourceNotFoundException(
                          "Room not found with ID: " + roomId
                  )
          );

  room.setCategory(requestDto.getCategory());
  room.setRoomAvailability(
          requestDto.getRoomAvailability()
  );
  room.setChargePerDay(
          requestDto.getChargePerDay()
  );

  Room updatedRoom =
          roomRepository.save(room);

  return convertToResponse(updatedRoom);
 }

 @Override
 public void deleteRoom(String roomId) {

  Room room = roomRepository.findById(roomId)
          .orElseThrow(() ->
                  new ResourceNotFoundException(
                          "Room not found with ID: " + roomId
                  )
          );

  roomRepository.delete(room);
 }

 private RoomResponseDto convertToResponse(Room room) {

  String category = null;
  String availability = null;

  if (room.getCategory() != null) {
   category =
           room.getCategory().getValue();
  }

  if (room.getRoomAvailability() != null) {
   availability =
           room.getRoomAvailability().getValue();
  }

  return new RoomResponseDto(
          room.getRoomId(),
          category,
          availability,
          room.getChargePerDay()
  );
 }
}