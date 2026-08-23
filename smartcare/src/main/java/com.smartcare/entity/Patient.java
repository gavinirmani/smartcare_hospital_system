package com.smartcare.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "patient")
@DynamicUpdate
public class Patient extends Person {

    @Id
    @Column(name = "patient_id", length = 10)
    private String patientId;

    @Column(name = "blood_group", nullable = false)
    @JsonProperty("blood_group")
    private String bloodGroup;

    @Column(name = "emergency_contact", nullable = false, length = 15)
    private String emergencyContact;

    public Patient() {
        super();
    }

    public Patient(
            String patientId,
            String fullName,
            LocalDate dob,
            Gender gender,
            String address,
            String contactNumber,
            String bloodGroup,
            String emergencyContact
    ) {
        super(fullName, dob, gender, address, contactNumber);
        this.patientId = patientId;
        this.bloodGroup = bloodGroup;
        this.emergencyContact = emergencyContact;
    }

    @JsonIgnore
    public String getId() {
        return patientId;
    }

    @JsonIgnore
    public String getName() {
        return getFullName();
    }
}