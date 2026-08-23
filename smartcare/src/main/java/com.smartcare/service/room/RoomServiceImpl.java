package com.smartcare.service.room;

import com.smartcare.dto.RoomRequestDto;
import com.smartcare.dto.RoomResponseDto;
import com.smartcare.entity.Room;
import com.smartcare.entity.RoomAvailability;
import com.smartcare.entity.RoomCategory;
import com.smartcare.api.exception.DuplicateResourceException;
import com.smartcare.api.exception.ResourceNotFoundException;
import com.smartcare.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

 private final RoomRepository roomRepository;

 public RoomServiceImpl(RoomRepository roomRepository) {
  this.roomRepository = roomRepository;
 }

 @Override
 @Transactional
 public RoomResponseDto createRoom(RoomRequestDto requestDto) {
  if (roomRepository.existsById(requestDto.getRoomId())) {
   throw new DuplicateResourceException("Room already exists with ID: " + requestDto.getRoomId());
  }
  Room room = mapToEntity(requestDto);
  if (room.getRoomAvailability() == null) {
   room.setRoomAvailability(RoomAvailability.Available);
  }
  return mapToDto(roomRepository.save(room));
 }

 @Override
 public RoomResponseDto getRoomById(String roomId) {
  Room room = roomRepository.findById(roomId)
          .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));
  return mapToDto(room);
 }

 @Override
 public List<RoomResponseDto> getAllRooms() {
  return roomRepository.findAll().stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 public List<RoomResponseDto> getAvailableRooms() {
  return roomRepository.findByRoomAvailability(RoomAvailability.Available).stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 public List<RoomResponseDto> getRoomsByCategory(RoomCategory category) {
  return roomRepository.findByCategory(category).stream()
          .map(this::mapToDto)
          .toList();
 }

 @Override
 @Transactional
 public RoomResponseDto updateRoom(String roomId, RoomRequestDto requestDto) {
  Room room = roomRepository.findById(roomId)
          .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));

  room.setCategory(requestDto.getCategory());
  room.setRoomAvailability(requestDto.getRoomAvailability());
  room.setChargePerDay(requestDto.getChargePerDay());

  return mapToDto(roomRepository.save(room));
 }

 @Override
 @Transactional
 public void deleteRoom(String roomId) {
  Room room = roomRepository.findById(roomId)
          .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));
  roomRepository.delete(room);
 }

 private Room mapToEntity(RoomRequestDto dto) {
  Room room = new Room();
  room.setRoomId(dto.getRoomId());
  room.setCategory(dto.getCategory());
  room.setRoomAvailability(dto.getRoomAvailability());
  room.setChargePerDay(dto.getChargePerDay());
  return room;
 }

 private RoomResponseDto mapToDto(Room room) {
  return new RoomResponseDto(
          room.getRoomId(),
          room.getCategory(),
          room.getRoomAvailability(),
          room.getChargePerDay()
  );
 }
}