package com.smartcare.controller;

import com.smartcare.dto.TreatmentRecordRequestDto;
import com.smartcare.dto.TreatmentRecordResponseDto;
import com.smartcare.service.treatment.TreatmentRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treatments")
public class TreatmentRecordController {

    private final TreatmentRecordService treatmentRecordService;

    public TreatmentRecordController(TreatmentRecordService treatmentRecordService) {
        this.treatmentRecordService = treatmentRecordService;
    }

    @PostMapping
    public ResponseEntity<TreatmentRecordResponseDto> createTreatmentRecord(@RequestBody TreatmentRecordRequestDto requestDto) {
        TreatmentRecordResponseDto created = treatmentRecordService.createTreatmentRecord(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentRecordResponseDto> getTreatmentRecordById(@PathVariable("id") String treatmentId) {
        return ResponseEntity.ok(treatmentRecordService.getTreatmentRecordById(treatmentId));
    }

    @GetMapping
    public ResponseEntity<List<TreatmentRecordResponseDto>> getAllTreatmentRecords() {
        return ResponseEntity.ok(treatmentRecordService.getAllTreatmentRecords());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<TreatmentRecordResponseDto>> getTreatmentRecordsByPatient(@PathVariable("patientId") String patientId) {
        return ResponseEntity.ok(treatmentRecordService.getTreatmentRecordsByPatient(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<TreatmentRecordResponseDto>> getTreatmentRecordsByDoctor(@PathVariable("doctorId") String doctorId) {
        return ResponseEntity.ok(treatmentRecordService.getTreatmentRecordsByDoctor(doctorId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentRecordResponseDto> updateTreatmentRecord(
            @PathVariable("id") String treatmentId,
            @RequestBody TreatmentRecordRequestDto requestDto) {
        return ResponseEntity.ok(treatmentRecordService.updateTreatmentRecord(treatmentId, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatmentRecord(@PathVariable("id") String treatmentId) {
        treatmentRecordService.deleteTreatmentRecord(treatmentId);
        return ResponseEntity.noContent().build();
    }
}