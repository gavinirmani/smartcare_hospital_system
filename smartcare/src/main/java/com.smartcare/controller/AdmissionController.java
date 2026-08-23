package com.smartcare.controller;

import com.smartcare.dto.AdmissionResponseDto;
import com.smartcare.entity.Admission;
import com.smartcare.service.admission.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admissions")
@CrossOrigin(origins = "*")
public class AdmissionController {

    @Autowired
    private AdmissionService admissionService;

    @GetMapping
    public List<AdmissionResponseDto> getAllAdmissions() {
        return admissionService.getAllAdmissions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdmissionResponseDto> getAdmissionById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                admissionService.getAdmissionById(id)
        );
    }

    @PostMapping
    public Admission createAdmission(
            @RequestBody Admission admission) {

        return admissionService.saveAdmission(admission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAdmission(
            @PathVariable String id) {

        admissionService.deleteAdmission(id);

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Admission with ID " + id +
                                " deleted successfully"
                )
        );
    }
}