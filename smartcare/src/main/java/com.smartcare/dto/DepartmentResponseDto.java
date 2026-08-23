package com.smartcare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentResponseDto {

    private String departmentId;
    private String departmentName;
    private String location;
    private String headDoctor;

    public DepartmentResponseDto() {
    }

    public DepartmentResponseDto(
            String departmentId,
            String departmentName,
            String location,
            String headDoctor) {

        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.location = location;
        this.headDoctor = headDoctor;
    }

}