package com.smartcare.service.doctor;

import com.smartcare.dto.DoctorRequestDto;
import com.smartcare.dto.DoctorResponseDto;
import com.smartcare.entity.Department;
import com.smartcare.entity.Doctor;
import com.smartcare.repository.DepartmentRepository;
import com.smartcare.repository.DoctorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;

    public DoctorServiceImpl(
            DoctorRepository doctorRepository,
            DepartmentRepository departmentRepository) {

        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
    }



    @Override
    public DoctorResponseDto addDoctor(
            DoctorRequestDto requestDto) {

        // Check whether Doctor ID already exists
        if (doctorRepository.existsById(requestDto.getDoctorId())) {
            throw new RuntimeException(
                    "Doctor ID already exists: "
                            + requestDto.getDoctorId()
            );
        }

        // Find Department
        Department department = departmentRepository
                .findById(requestDto.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Department not found: "
                                        + requestDto.getDepartmentId()
                        )
                );

        // Create Doctor
        Doctor doctor = new Doctor();

        doctor.setDoctorId(requestDto.getDoctorId());
        doctor.setDoctorName(requestDto.getDoctorName());
        doctor.setSpecialization(
                requestDto.getSpecialization()
        );
        doctor.setQualification(
                requestDto.getQualification()
        );
        doctor.setContactNumber(
                requestDto.getContactNumber()
        );
        doctor.setConsultationFee(
                requestDto.getConsultationFee()
        );
        doctor.setDepartment(department);

        // Save Doctor
        Doctor savedDoctor = doctorRepository.save(doctor);

        return convertToResponse(savedDoctor);
    }


    @Override
    public DoctorResponseDto getDoctorById(
            String doctorId) {

        Doctor doctor = doctorRepository
                .findById(doctorId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found: " + doctorId
                        )
                );

        return convertToResponse(doctor);
    }



    @Override
    public List<DoctorResponseDto> getAllDoctors() {

        return doctorRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }



    @Override
    public List<DoctorResponseDto> getDoctorsBySpecialization(
            String specialization) {

        return doctorRepository
                .findBySpecializationIgnoreCase(specialization)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }



    @Override
    public List<DoctorResponseDto> getDoctorsByDepartment(
            String departmentId) {

        // Make sure department exists
        departmentRepository.findById(departmentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Department not found: "
                                        + departmentId
                        )
                );

        return doctorRepository
                .findByDepartment_DepartmentId(departmentId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }



    @Override
    public List<DoctorResponseDto> searchDoctors(
            String doctorName) {

        return doctorRepository
                .findByDoctorNameContainingIgnoreCase(doctorName)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }



    @Override
    public DoctorResponseDto updateDoctor(
            String doctorId,
            DoctorRequestDto requestDto) {

        Doctor doctor = doctorRepository
                .findById(doctorId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found: " + doctorId
                        )
                );

        Department department = departmentRepository
                .findById(requestDto.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Department not found: "
                                        + requestDto.getDepartmentId()
                        )
                );


        doctor.setDoctorName(
                requestDto.getDoctorName()
        );

        doctor.setSpecialization(
                requestDto.getSpecialization()
        );

        doctor.setQualification(
                requestDto.getQualification()
        );

        doctor.setContactNumber(
                requestDto.getContactNumber()
        );

        doctor.setConsultationFee(
                requestDto.getConsultationFee()
        );

        doctor.setDepartment(department);

        Doctor updatedDoctor =
                doctorRepository.save(doctor);

        return convertToResponse(updatedDoctor);
    }

    // =========================
    // ASSIGN DOCTOR TO DEPARTMENT
    // =========================

    @Override
    public void assignDoctorToDepartment(
            String doctorId,
            String departmentId) {

        Doctor doctor = doctorRepository
                .findById(doctorId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found: " + doctorId
                        )
                );

        Department department = departmentRepository
                .findById(departmentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Department not found: "
                                        + departmentId
                        )
                );

        doctor.setDepartment(department);

        Doctor updatedDoctor =
                doctorRepository.save(doctor);

        convertToResponse(updatedDoctor);
    }

    @Override
    public void deleteDoctor(String doctorId) {

        Doctor doctor = doctorRepository
                .findById(doctorId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found: " + doctorId
                        )
                );

        doctorRepository.delete(doctor);
    }

    @Override
    public DoctorResponseDto createDoctor(DoctorRequestDto requestDto) {
        return addDoctor(requestDto);
    }

    private DoctorResponseDto convertToResponse(
            Doctor doctor) {

        String departmentId = null;

        if (doctor.getDepartment() != null) {
            departmentId =
                    doctor.getDepartment().getDepartmentId();
        }

        return new DoctorResponseDto(
                doctor.getDoctorId(),
                doctor.getDoctorName(),
                doctor.getSpecialization(),
                doctor.getQualification(),
                doctor.getContactNumber(),
                doctor.getConsultationFee(),
                departmentId
        );
    }
}