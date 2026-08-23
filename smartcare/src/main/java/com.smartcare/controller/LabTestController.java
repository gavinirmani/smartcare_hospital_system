package com.smartcare.controller;

import com.smartcare.dto.LabTestRequestDto;
import com.smartcare.dto.LabTestResponseDto;
import com.smartcare.service.laboratory.LabTestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lab-tests")
public class LabTestController {

    private final LabTestService labTestService;

    public LabTestController(LabTestService labTestService) {
        this.labTestService = labTestService;
    }

    @PostMapping
    public ResponseEntity<LabTestResponseDto> createLabTest(@RequestBody LabTestRequestDto requestDto) {
        LabTestResponseDto created = labTestService.createLabTest(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabTestResponseDto> getLabTestById(@PathVariable("id") String labTestId) {
        return ResponseEntity.ok(labTestService.getLabTestById(labTestId));
    }

    @GetMapping
    public ResponseEntity<List<LabTestResponseDto>> getAllLabTests() {
        return ResponseEntity.ok(labTestService.getAllLabTests());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<LabTestResponseDto>> getLabTestsByPatient(@PathVariable("patientId") String patientId) {
        return ResponseEntity.ok(labTestService.getLabTestsByPatient(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<LabTestResponseDto>> getLabTestsByDoctor(@PathVariable("doctorId") String doctorId) {
        return ResponseEntity.ok(labTestService.getLabTestsByDoctor(doctorId));
    }

    @PatchMapping("/{id}/result")
    public ResponseEntity<LabTestResponseDto> updateResult(
            @PathVariable("id") String labTestId,
            @RequestParam("result") String result,
            @RequestParam("technicianName") String technicianName) {
        return ResponseEntity.ok(labTestService.updateLabTestResult(labTestId, result, technicianName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabTest(@PathVariable("id") String labTestId) {
        labTestService.deleteLabTest(labTestId);
        return ResponseEntity.noContent().build();
    }
}