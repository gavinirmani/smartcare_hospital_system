package com.smartcare.controller;

import com.smartcare.entity.RoomCategory;
import com.smartcare.service.room.RoomService;
import com.smartcare.dto.RoomRequestDto;
import com.smartcare.dto.RoomResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<RoomResponseDto> createRoom(@RequestBody RoomRequestDto requestDto) {
        RoomResponseDto createdRoom = roomService.createRoom(requestDto);
        return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDto> getRoomById(@PathVariable("id") String roomId) {
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomResponseDto>> getAvailableRooms() {
        return ResponseEntity.ok(roomService.getAvailableRooms());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<RoomResponseDto>> getRoomsByCategory(@PathVariable("category") RoomCategory category) {
        return ResponseEntity.ok(roomService.getRoomsByCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDto> updateRoom(@PathVariable("id") String roomId, @RequestBody RoomRequestDto requestDto) {
        return ResponseEntity.ok(roomService.updateRoom(roomId, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") String roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }
}