package com.smartcare.service.department;

import com.smartcare.dto.DepartmentRequestDto;
import com.smartcare.dto.DepartmentResponseDto;

import java.util.List;

public interface DepartmentService {
    DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto);
    DepartmentResponseDto updateDepartment(String id, DepartmentRequestDto requestDto);
    void deleteDepartment(String id);
    DepartmentResponseDto getDepartmentById(String id);
    List<DepartmentResponseDto> getAllDepartments();
}