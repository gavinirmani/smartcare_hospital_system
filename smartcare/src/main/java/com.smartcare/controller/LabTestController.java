package com.smartcare.controller;

import com.smartcare.dto.LabTestRequestDto;
import com.smartcare.dto.LabTestResponseDto;
import com.smartcare.service.laboratory.LabTestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab-tests")
@CrossOrigin(origins = "*")
public class LabTestController {

    private final LabTestService labTestService;

    public LabTestController(LabTestService labTestService) {
        this.labTestService = labTestService;
    }

    // =========================
    // CREATE LAB TEST
    // =========================
    @PostMapping
    public ResponseEntity<LabTestResponseDto> createLabTest(
            @RequestBody LabTestRequestDto requestDto) {

        LabTestResponseDto response =
                labTestService.createLabTest(requestDto);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    // =========================
    // GET ALL LAB TESTS
    // =========================
    @GetMapping
    public ResponseEntity<List<LabTestResponseDto>> getAllLabTests() {

        return ResponseEntity.ok(
                labTestService.getAllLabTests()
        );
    }

    // =========================
    // UPDATE LAB TEST
    // =========================
    @PutMapping("/{labTestId}")
    public ResponseEntity<LabTestResponseDto> updateLabTest(
            @PathVariable String labTestId,
            @RequestBody LabTestRequestDto requestDto) {

        return ResponseEntity.ok(
                labTestService.updateLabTest(
                        labTestId,
                        requestDto
                )
        );
    }

    // =========================
    // GET LAB TEST BY ID
    // =========================
    @GetMapping("/{labTestId}")
    public ResponseEntity<LabTestResponseDto> getLabTestById(
            @PathVariable String labTestId) {

        return ResponseEntity.ok(
                labTestService.getLabTestById(labTestId)
        );
    }

    // =========================
    // GET LAB TESTS BY PATIENT
    // =========================
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<LabTestResponseDto>> getByPatient(
            @PathVariable String patientId) {

        return ResponseEntity.ok(
                labTestService.getLabTestsByPatient(patientId)
        );
    }

    // =========================
    // GET LAB TESTS BY DOCTOR
    // =========================
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<LabTestResponseDto>> getByDoctor(
            @PathVariable String doctorId) {

        return ResponseEntity.ok(
                labTestService.getLabTestsByDoctor(doctorId)
        );
    }

    // =========================
    // DELETE LAB TEST
    // =========================
    @DeleteMapping("/{labTestId}")
    public ResponseEntity<Map<String, String>> deleteLabTest(
            @PathVariable String labTestId) {

        labTestService.deleteLabTest(labTestId);

        Map<String, String> response = new HashMap<>();
        response.put(
                "message",
                "Lab test " + labTestId + " deleted successfully"
        );

        return ResponseEntity.ok(response);
    }
}