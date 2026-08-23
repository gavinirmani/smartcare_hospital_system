package com.smartcare.controller;

import com.smartcare.dto.BillRequestDto;
import com.smartcare.dto.BillResponseDto;
import com.smartcare.entity.PaymentMethod;
import com.smartcare.entity.PaymentStatus;
import com.smartcare.service.billing.BillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseEntity<BillResponseDto> createBill(@RequestBody BillRequestDto requestDto) {
        BillResponseDto created = billService.createBill(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillResponseDto> getBillById(@PathVariable("id") String billId) {
        return ResponseEntity.ok(billService.getBillById(billId));
    }

    @GetMapping
    public ResponseEntity<List<BillResponseDto>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<BillResponseDto>> getBillsByPatient(@PathVariable("patientId") String patientId) {
        return ResponseEntity.ok(billService.getBillsByPatient(patientId));
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<BillResponseDto>> getBillsByAppointment(@PathVariable("appointmentId") String appointmentId) {
        return ResponseEntity.ok(billService.getBillsByAppointment(appointmentId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BillResponseDto>> getBillsByPaymentStatus(@PathVariable("status") PaymentStatus status) {
        return ResponseEntity.ok(billService.getBillsByPaymentStatus(status));
    }

    @PatchMapping("/{id}/payment")
    public ResponseEntity<BillResponseDto> updatePaymentStatus(
            @PathVariable("id") String billId,
            @RequestParam("status") PaymentStatus status,
            @RequestParam(value = "method", required = false) PaymentMethod method) {
        return ResponseEntity.ok(billService.updatePaymentStatus(billId, status, method));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable("id") String billId) {
        billService.deleteBill(billId);
        return ResponseEntity.noContent().build();
    }
}