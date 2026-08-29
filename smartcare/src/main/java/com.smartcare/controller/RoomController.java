package com.smartcare.controller;

import com.smartcare.dto.RoomRequestDto;
import com.smartcare.dto.RoomResponseDto;
import com.smartcare.service.room.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    // CREATE
    @PostMapping
    public ResponseEntity<RoomResponseDto> createRoom(
            @Valid @RequestBody RoomRequestDto requestDto
    ) {

        return new ResponseEntity<>(
                roomService.createRoom(requestDto),
                HttpStatus.CREATED
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> getAllRooms() {

        return ResponseEntity.ok(
                roomService.getAllRooms()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDto> getRoomById(
            @PathVariable String id
    ) {

        return ResponseEntity.ok(
                roomService.getRoomById(id)
        );
    }

    // GET BY CATEGORY
    @GetMapping("/category/{category}")
    public ResponseEntity<List<RoomResponseDto>> getRoomsByCategory(
            @PathVariable String category
    ) {

        return ResponseEntity.ok(
                roomService.getRoomsByCategory(category)
        );
    }

    // GET BY AVAILABILITY
    @GetMapping("/availability/{availability}")
    public ResponseEntity<List<RoomResponseDto>> getRoomsByAvailability(
            @PathVariable String availability
    ) {

        return ResponseEntity.ok(
                roomService.getRoomsByAvailability(availability)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDto> updateRoom(
            @PathVariable String id,
            @Valid @RequestBody RoomRequestDto requestDto
    ) {

        return ResponseEntity.ok(
                roomService.updateRoom(id, requestDto)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteRoom(
            @PathVariable String id
    ) {

        roomService.deleteRoom(id);

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Room with ID " + id +
                                " deleted successfully"
                )
        );
    }
}