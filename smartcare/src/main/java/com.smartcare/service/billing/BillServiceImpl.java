package com.smartcare.service.billing;

import com.smartcare.dto.BillRequestDto;
import com.smartcare.dto.BillResponseDto;
import com.smartcare.entity.Appointment;
import com.smartcare.entity.Bill;
import com.smartcare.entity.Patient;
import com.smartcare.entity.PaymentMethod;
import com.smartcare.entity.PaymentStatus;
import com.smartcare.api.exception.DuplicateResourceException;
import com.smartcare.api.exception.ResourceNotFoundException;
import com.smartcare.repository.AppointmentRepository;
import com.smartcare.repository.BillRepository;
import com.smartcare.repository.PatientRepository;
import com.smartcare.service.payment.PaymentFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    private final PaymentFactory paymentFactory;

    @Transactional
    public BillResponseDto processBillPayment(String billId, PaymentMethod method) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found: " + billId));


        com.smartcare.service.payment.PaymentService paymentService = paymentFactory.getService(method);
        boolean success = paymentService.processPayment(bill.getBillId(), bill.getTotalAmount());

        if (success) {
            bill.setPaymentStatus(PaymentStatus.Paid);
            bill.setPaymentMethod(method);
        }

        return mapToDto(billRepository.save(bill));
    }

    private final BillRepository billRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public BillServiceImpl(PaymentFactory paymentFactory, BillRepository billRepository,
                           PatientRepository patientRepository,
                           AppointmentRepository appointmentRepository) {
        this.paymentFactory = paymentFactory;
        this.billRepository = billRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    @Transactional
    public BillResponseDto createBill(BillRequestDto requestDto) {
        if (billRepository.existsById(requestDto.billId())) {
            throw new DuplicateResourceException("Bill already exists with ID: " + requestDto.billId());
        }

        Patient patient = patientRepository.findById(requestDto.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + requestDto.patientId()));

        Appointment appointment = appointmentRepository.findById(requestDto.appointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found: " + requestDto.appointmentId()));

        BigDecimal consultation = requestDto.consultationCharges() != null ? requestDto.consultationCharges() : BigDecimal.ZERO;
        BigDecimal room = requestDto.roomCharges() != null ? requestDto.roomCharges() : BigDecimal.ZERO;
        BigDecimal lab = requestDto.labCharges() != null ? requestDto.labCharges() : BigDecimal.ZERO;
        BigDecimal medicine = requestDto.medicineCharges() != null ? requestDto.medicineCharges() : BigDecimal.ZERO;
        BigDecimal total = consultation.add(room).add(lab).add(medicine);

        Bill bill = new Bill();
        bill.setBillId(requestDto.billId());
        bill.setPatient(patient);
        bill.setAppointment(appointment);
        bill.setBillDate(requestDto.billDate());
        bill.setConsultationCharges(consultation);
        bill.setRoomCharges(room);
        bill.setLabCharges(lab);
        bill.setMedicineCharges(medicine);
        bill.setTotalAmount(total);
        bill.setPaymentStatus(requestDto.paymentStatus() != null ? requestDto.paymentStatus() : PaymentStatus.Unpaid);
        bill.setPaymentMethod(requestDto.paymentMethod());

        return mapToDto(billRepository.save(bill));
    }

    @Override
    public BillResponseDto getBillById(String billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with ID: " + billId));
        return mapToDto(bill);
    }

    @Override
    public List<BillResponseDto> getAllBills() {
        return billRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<BillResponseDto> getBillsByPatient(String patientId) {
        return billRepository.findByPatient_Id(patientId).stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<BillResponseDto> getBillsByAppointment(String appointmentId) {
        return billRepository.findByAppointment_AppointmentId(appointmentId).stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<BillResponseDto> getBillsByPaymentStatus(PaymentStatus status) {
        return billRepository.findByPaymentStatus(status).stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    @Transactional
    public BillResponseDto updatePaymentStatus(String billId, PaymentStatus status, PaymentMethod method) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with ID: " + billId));

        bill.setPaymentStatus(status);
        bill.setPaymentMethod(method);

        return mapToDto(billRepository.save(bill));
    }

    @Override
    @Transactional
    public void deleteBill(String billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with ID: " + billId));
        billRepository.delete(bill);
    }

    private BillResponseDto mapToDto(Bill bill) {
        return new BillResponseDto(
                bill.getBillId(),
                bill.getPatient() != null ? bill.getPatient().getId() : null,
                bill.getPatient() != null ? bill.getPatient().getName() : null,
                bill.getAppointment() != null ? bill.getAppointment().getAppointmentId() : null,
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