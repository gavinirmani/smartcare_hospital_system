package com.smartcare.controller;

import com.smartcare.dto.DoctorRequestDto;
import com.smartcare.dto.DoctorResponseDto;
import com.smartcare.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    // 1. Add doctors
    @PostMapping
    public ResponseEntity<DoctorResponseDto> createDoctor(@RequestBody DoctorRequestDto requestDto) {
        return new ResponseEntity<>(doctorService.createDoctor(requestDto), HttpStatus.CREATED);
    }

    // 2. Update doctor details
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> updateDoctor(@PathVariable String id, @RequestBody DoctorRequestDto requestDto) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, requestDto));
    }

    // 3. Delete doctors (Returns JSON message format)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteDoctor(@PathVariable String id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok(Map.of("message", "Doctor with ID " + id + " deleted successfully"));
    }

    // 4. Search doctors by ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable String id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    // 4. Search doctors by Specialization
    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<DoctorResponseDto>> getDoctorsBySpecialization(@PathVariable String specialization) {
        return ResponseEntity.ok(doctorService.getDoctorsBySpecialization(specialization));
    }

    // 5. Get all doctors

    @GetMapping
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    // 6. Assign doctors to departments
    @PutMapping("/{doctorId}/department/{departmentId}")
    public ResponseEntity<Map<String, String>> assignDoctorToDepartment(
            @PathVariable String doctorId,
            @PathVariable String departmentId) {

        doctorService.assignDoctorToDepartment(doctorId, departmentId);

        return ResponseEntity.ok(Map.of(
                "message", "Doctor " + doctorId + " successfully assigned to department " + departmentId
        ));
    }
}