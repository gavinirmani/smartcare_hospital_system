package com.smartcare.service.billing;

import com.smartcare.dto.BillRequestDto;
import com.smartcare.dto.BillResponseDto;
import com.smartcare.entity.PaymentMethod;
import com.smartcare.entity.PaymentStatus;

import java.util.List;

public interface BillService {

    BillResponseDto createBill(
            BillRequestDto requestDto
    );

    BillResponseDto getBillById(
            String billId
    );

    List<BillResponseDto> getAllBills();

    List<BillResponseDto> getBillsByPatient(
            String patientId
    );

    // PUT - Full update
    BillResponseDto updateBill(
            String billId,
            BillRequestDto requestDto
    );

    // PATCH - Update payment only
    BillResponseDto updatePaymentStatus(
            String billId,
            PaymentStatus status,
            PaymentMethod method
    );

    void deleteBill(String billId);
}