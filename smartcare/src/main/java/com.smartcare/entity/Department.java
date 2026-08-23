package com.smartcare.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "department")
public class Department {

    @Id
    @Column(name = "department_id", length = 10)
    private String departmentId;

    @Column(name = "department_name", nullable = false, length = 100)
    private String departmentName;

    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Column(name = "head_doctor", length = 10)
    private String headDoctor;

    public Department() {
    }

    public Department(String departmentId, String departmentName, String location, String headDoctor) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.location = location;
        this.headDoctor = headDoctor;
    }


}