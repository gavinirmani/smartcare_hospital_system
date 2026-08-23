package com.smartcare.service.room;

import com.smartcare.dto.RoomRequestDto;
import com.smartcare.dto.RoomResponseDto;
import com.smartcare.entity.RoomCategory;

import java.util.List;

public interface RoomService {
    RoomResponseDto createRoom(RoomRequestDto requestDto);
    RoomResponseDto getRoomById(String roomId);
    List<RoomResponseDto> getAllRooms();
    List<RoomResponseDto> getAvailableRooms();
    List<RoomResponseDto> getRoomsByCategory(RoomCategory category);
    RoomResponseDto updateRoom(String roomId, RoomRequestDto requestDto);
    void deleteRoom(String roomId);
}