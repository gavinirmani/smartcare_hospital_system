package com.smartcare.controller;

import com.smartcare.dto.BillRequestDto;
import com.smartcare.dto.BillResponseDto;
import com.smartcare.service.billing.BillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/billing")
@CrossOrigin(origins = "*")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseEntity<BillResponseDto> createBill(
            @RequestBody BillRequestDto requestDto) {

        return new ResponseEntity<>(
                billService.createBill(requestDto),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<BillResponseDto>> getAllBills() {
        return ResponseEntity.ok(
                billService.getAllBills()
        );
    }

    @GetMapping("/{billId}")
    public ResponseEntity<BillResponseDto> getBillById(
            @PathVariable String billId) {

        return ResponseEntity.ok(
                billService.getBillById(billId)
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<BillResponseDto>> getBillsByPatient(
            @PathVariable String patientId) {

        return ResponseEntity.ok(
                billService.getBillsByPatient(patientId)
        );
    }


    @PutMapping("/{billId}")
    public ResponseEntity<BillResponseDto> updateBill(
            @PathVariable String billId,
            @RequestBody BillRequestDto requestDto) {

        return ResponseEntity.ok(
                billService.updateBill(
                        billId,
                        requestDto
                )
        );

    }

    @DeleteMapping("/{billId}")
    public ResponseEntity<Map<String, String>> deleteBill(
            @PathVariable String billId) {

        billService.deleteBill(billId);

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Bill " + billId + " deleted successfully"
                )
        );
    }
}