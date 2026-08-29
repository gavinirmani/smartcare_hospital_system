package com.smartcare.controller;

import com.smartcare.dto.AdmissionRequestDto;
import com.smartcare.dto.AdmissionResponseDto;
import com.smartcare.service.admission.AdmissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admissions")
@CrossOrigin(origins = "*")
public class AdmissionController {

    private final AdmissionService admissionService;

    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @PostMapping
    public ResponseEntity<AdmissionResponseDto> createAdmission(
            @RequestBody AdmissionRequestDto requestDto) {

        return new ResponseEntity<>(
                admissionService.createAdmission(requestDto),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<AdmissionResponseDto>> getAllAdmissions() {
        return ResponseEntity.ok(admissionService.getAllAdmissions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdmissionResponseDto> getAdmissionById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                admissionService.getAdmissionById(id)
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AdmissionResponseDto>> getAdmissionsByPatient(
            @PathVariable String patientId) {

        return ResponseEntity.ok(
                admissionService.getAdmissionsByPatient(patientId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdmissionResponseDto> updateAdmission(
            @PathVariable String id,
            @RequestBody AdmissionRequestDto requestDto) {

        return ResponseEntity.ok(
                admissionService.updateAdmission(
                        id,
                        requestDto
                )
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAdmission(
            @PathVariable String id) {

        admissionService.deleteAdmission(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Admission " + id + " deleted successfully");

        return ResponseEntity.ok(response);
    }
}