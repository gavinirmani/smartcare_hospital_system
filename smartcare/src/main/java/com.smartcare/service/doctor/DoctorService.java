package com.smartcare.service.doctor;

import com.smartcare.dto.DoctorRequestDto;
import com.smartcare.dto.DoctorResponseDto;

import java.util.List;

public interface DoctorService {

    DoctorResponseDto addDoctor(DoctorRequestDto requestDto);

    DoctorResponseDto getDoctorById(String doctorId);

    List<DoctorResponseDto> getAllDoctors();

    List<DoctorResponseDto> getDoctorsBySpecialization(
            String specialization);

    List<DoctorResponseDto> getDoctorsByDepartment(
            String departmentId);

    List<DoctorResponseDto> searchDoctors(
            String doctorName);

    DoctorResponseDto updateDoctor(
            String doctorId,
            DoctorRequestDto requestDto);

    void assignDoctorToDepartment(
            String doctorId,
            String departmentId);

    void deleteDoctor(String doctorId);

    DoctorResponseDto createDoctor(DoctorRequestDto requestDto);
}