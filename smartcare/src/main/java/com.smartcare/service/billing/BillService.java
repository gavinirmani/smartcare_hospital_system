package com.smartcare.service.billing;

import com.smartcare.dto.BillRequestDto;
import com.smartcare.dto.BillResponseDto;
import com.smartcare.entity.PaymentMethod;
import com.smartcare.entity.PaymentStatus;

import java.util.List;

public interface BillService {
    BillResponseDto createBill(BillRequestDto requestDto);
    BillResponseDto getBillById(String billId);
    List<BillResponseDto> getAllBills();
    List<BillResponseDto> getBillsByPatient(String patientId);
    List<BillResponseDto> getBillsByAppointment(String appointmentId);
    List<BillResponseDto> getBillsByPaymentStatus(PaymentStatus status);
    BillResponseDto updatePaymentStatus(String billId, PaymentStatus status, PaymentMethod method);
    void deleteBill(String billId);
}