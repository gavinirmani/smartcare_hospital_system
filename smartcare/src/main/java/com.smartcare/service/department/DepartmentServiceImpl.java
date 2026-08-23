package com.smartcare.service.department;

import com.smartcare.dto.DepartmentRequestDto;
import com.smartcare.dto.DepartmentResponseDto;
import com.smartcare.entity.Department;
import com.smartcare.entity.Doctor;
import com.smartcare.api.exception.ResourceNotFoundException;
import com.smartcare.repository.DepartmentRepository;
import com.smartcare.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) {

        Department department = new Department();

        department.setDepartmentId(requestDto.getDepartmentId());
        department.setDepartmentName(requestDto.getDepartmentName());
        department.setLocation(requestDto.getLocation());

        if (requestDto.getHeadDoctor() != null &&
                !requestDto.getHeadDoctor().trim().isEmpty()) {

            Doctor doctor = doctorRepository.findById(requestDto.getHeadDoctor())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Doctor not found with ID: " + requestDto.getHeadDoctor()
                    ));

            department.setHeadDoctor(doctor.getDoctorId());
        } else {
            department.setHeadDoctor(null);
        }

        Department savedDepartment = departmentRepository.save(department);

        return mapToDto(savedDepartment);
    }

    @Override
    public DepartmentResponseDto updateDepartment(String departmentId, DepartmentRequestDto requestDto) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + departmentId));

        department.setDepartmentName(requestDto.getDepartmentName());
        department.setLocation(requestDto.getLocation());

        if (requestDto.getHeadDoctor() != null && !requestDto.getHeadDoctor().trim().isEmpty()) {
            Doctor doctor = doctorRepository.findById(requestDto.getHeadDoctor())
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + requestDto.getHeadDoctor()));
            department.setHeadDoctor(doctor.getDoctorId());

        }

        Department updatedDepartment = departmentRepository.save(department);
        return mapToDto(updatedDepartment);
    }

    @Override
    public void deleteDepartment(String departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + departmentId));
        departmentRepository.delete(department);
    }

    @Override
    public DepartmentResponseDto getDepartmentById(String departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + departmentId));
        return mapToDto(department);
    }

    @Override
    public List<DepartmentResponseDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private DepartmentResponseDto mapToDto(Department department) {

        DepartmentResponseDto dto = new DepartmentResponseDto();

        dto.setDepartmentId(department.getDepartmentId());
        dto.setDepartmentName(department.getDepartmentName());
        dto.setLocation(department.getLocation());
        dto.setHeadDoctor(department.getHeadDoctor());

        return dto;
    }

}