package com.smartcare.service.billing;

import com.smartcare.api.exception.DuplicateResourceException;
import com.smartcare.api.exception.ResourceNotFoundException;
import com.smartcare.dto.BillRequestDto;
import com.smartcare.dto.BillResponseDto;
import com.smartcare.entity.Appointment;
import com.smartcare.entity.Bill;
import com.smartcare.entity.Patient;
import com.smartcare.entity.PaymentMethod;
import com.smartcare.entity.PaymentStatus;
import com.smartcare.repository.AppointmentRepository;
import com.smartcare.repository.BillRepository;
import com.smartcare.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public BillServiceImpl(
            BillRepository billRepository,
            PatientRepository patientRepository,
            AppointmentRepository appointmentRepository) {

        this.billRepository = billRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // =====================================================
    // CREATE BILL - POST
    // =====================================================

    @Override
    @Transactional
    public BillResponseDto createBill(
            BillRequestDto requestDto) {

        // Check duplicate bill ID
        if (billRepository.existsById(
                requestDto.billId())) {

            throw new DuplicateResourceException(
                    "Bill already exists: "
                            + requestDto.billId()
            );
        }

        // Find patient
        Patient patient = patientRepository.findById(
                requestDto.patientId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Patient not found: "
                                + requestDto.patientId()
                )
        );

        // Find appointment
        Appointment appointment =
                appointmentRepository.findById(
                        requestDto.appointmentId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found: "
                                        + requestDto.appointmentId()
                        )
                );

        // Get charges
        BigDecimal consultation =
                zeroIfNull(
                        requestDto.consultationCharges()
                );

        BigDecimal room =
                zeroIfNull(
                        requestDto.roomCharges()
                );

        BigDecimal lab =
                zeroIfNull(
                        requestDto.labCharges()
                );

        BigDecimal medicine =
                zeroIfNull(
                        requestDto.medicineCharges()
                );

        // Calculate total
        BigDecimal total =
                consultation
                        .add(room)
                        .add(lab)
                        .add(medicine);

        // Create Bill entity
        Bill bill = new Bill();

        bill.setBillId(
                requestDto.billId()
        );

        bill.setPatient(
                patient
        );

        bill.setAppointment(
                appointment
        );

        bill.setBillDate(
                requestDto.billDate() != null
                        ? requestDto.billDate()
                        : LocalDate.now()
        );

        bill.setConsultationCharges(
                consultation
        );

        bill.setRoomCharges(
                room
        );

        bill.setLabCharges(
                lab
        );

        bill.setMedicineCharges(
                medicine
        );

        bill.setTotalAmount(
                total
        );

        bill.setPaymentStatus(
                requestDto.paymentStatus() != null
                        ? requestDto.paymentStatus()
                        : PaymentStatus.Unpaid
        );

        bill.setPaymentMethod(
                requestDto.paymentMethod()
        );

        // Save bill
        Bill savedBill =
                billRepository.save(bill);

        return mapToDto(savedBill);
    }


    // =====================================================
    // GET BILL BY ID
    // =====================================================

    @Override
    public BillResponseDto getBillById(
            String billId) {

        Bill bill =
                billRepository.findById(billId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Bill not found: "
                                                + billId
                                )
                        );

        return mapToDto(bill);
    }


    // =====================================================
    // GET ALL BILLS
    // =====================================================

    @Override
    public List<BillResponseDto> getAllBills() {

        return billRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    // =====================================================
    // GET BILLS BY PATIENT
    // =====================================================

    @Override
    public List<BillResponseDto> getBillsByPatient(
            String patientId) {

        return billRepository
                .findByPatient_Id(patientId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    // =====================================================
    // FULL UPDATE BILL - PUT
    // =====================================================

    @Override
    @Transactional
    public BillResponseDto updateBill(
            String billId,
            BillRequestDto requestDto) {

        // Find existing bill
        Bill bill =
                billRepository.findById(billId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Bill not found: "
                                                + billId
                                )
                        );

        // Find patient
        Patient patient =
                patientRepository.findById(
                        requestDto.patientId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found: "
                                        + requestDto.patientId()
                        )
                );

        // Find appointment
        Appointment appointment =
                appointmentRepository.findById(
                        requestDto.appointmentId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found: "
                                        + requestDto.appointmentId()
                        )
                );

        // Update patient
        bill.setPatient(
                patient
        );

        // Update appointment
        bill.setAppointment(
                appointment
        );

        // Update bill date
        if (requestDto.billDate() != null) {

            bill.setBillDate(
                    requestDto.billDate()
            );
        }

        // Get updated charges
        BigDecimal consultation =
                zeroIfNull(
                        requestDto.consultationCharges()
                );

        BigDecimal room =
                zeroIfNull(
                        requestDto.roomCharges()
                );

        BigDecimal lab =
                zeroIfNull(
                        requestDto.labCharges()
                );

        BigDecimal medicine =
                zeroIfNull(
                        requestDto.medicineCharges()
                );

        // Update charges
        bill.setConsultationCharges(
                consultation
        );

        bill.setRoomCharges(
                room
        );

        bill.setLabCharges(
                lab
        );

        bill.setMedicineCharges(
                medicine
        );

        // Recalculate total
        BigDecimal total =
                consultation
                        .add(room)
                        .add(lab)
                        .add(medicine);

        bill.setTotalAmount(
                total
        );

        // Update payment status
        if (requestDto.paymentStatus() != null) {

            bill.setPaymentStatus(
                    requestDto.paymentStatus()
            );
        }

        // Update payment method
        bill.setPaymentMethod(
                requestDto.paymentMethod()
        );

        // Save updated bill
        Bill updatedBill =
                billRepository.save(bill);

        return mapToDto(updatedBill);
    }


    // =====================================================
    // UPDATE PAYMENT - PATCH
    // =====================================================

    @Override
    @Transactional
    public BillResponseDto updatePaymentStatus(
            String billId,
            PaymentStatus status,
            PaymentMethod method) {

        Bill bill =
                billRepository.findById(billId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Bill not found: "
                                                + billId
                                )
                        );

        // Update payment status
        bill.setPaymentStatus(
                status
        );

        // Update payment method
        bill.setPaymentMethod(
                method
        );

        return mapToDto(
                billRepository.save(bill)
        );
    }


    // =====================================================
    // DELETE BILL
    // =====================================================

    @Override
    @Transactional
    public void deleteBill(
            String billId) {

        Bill bill =
                billRepository.findById(billId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Bill not found: "
                                                + billId
                                )
                        );

        billRepository.delete(bill);
    }


    // =====================================================
    // NULL VALUE HANDLER
    // =====================================================

    private BigDecimal zeroIfNull(
            BigDecimal value) {

        return value == null
                ? BigDecimal.ZERO
                : value;
    }


    // =====================================================
    // ENTITY -> RESPONSE DTO
    // =====================================================

    private BillResponseDto mapToDto(
            Bill bill) {

        return new BillResponseDto(

                bill.getBillId(),

                bill.getPatient() != null
                        ? bill.getPatient().getId()
                        : null,

                bill.getPatient() != null
                        ? bill.getPatient().getName()
                        : null,

                bill.getAppointment() != null
                        ? bill.getAppointment()
                        .getAppointmentId()
                        : null,

                bill.getBillDate(),

                bill.getConsultationCharges(),

                bill.getRoomCharges(),

                bill.getLabCharges(),

                bill.getMedicineCharges(),

                bill.getTotalAmount(),

                bill.getPaymentStatus(),

                bill.getPaymentMethod()
        );
    }
}