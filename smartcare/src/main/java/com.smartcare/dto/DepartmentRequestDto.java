package com.smartcare.dto;

import lombok.Data;

@Data
public class DepartmentRequestDto {
    private String departmentId;
    private String departmentName;
    private String location;
    private String headDoctor;
}