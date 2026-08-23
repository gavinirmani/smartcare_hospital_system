CREATE DATABASE smartcare_db;
USE smartcare_db;


CREATE TABLE department (
    department_id VARCHAR(10) PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    head_doctor VARCHAR(10) NULL
    );


CREATE TABLE doctor (
    doctor_id VARCHAR(10) PRIMARY KEY,
    doctor_name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    qualification VARCHAR(100) NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    consultation_fee DECIMAL(10,2) CHECK (consultation_fee > 0),
    department_id VARCHAR(10) NOT NULL,
    FOREIGN KEY (department_id) REFERENCES department(department_id) ON DELETE CASCADE
);

ALTER TABLE department
ADD CONSTRAINT fk_dept_head_doctor
FOREIGN KEY (head_doctor) REFERENCES doctor(doctor_id) ON DELETE SET NULL;


CREATE TABLE patient (
    patient_id VARCHAR(10) PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    dob DATE NOT NULL,
    gender ENUM ('Male', 'Female', 'Other') NOT NULL,
    address VARCHAR(255) NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    blood_group ENUM ('A+', 'A-','B+','B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    emergency_contact VARCHAR(15) NOT NULL
);

CREATE TABLE appointment (
    appointment_id VARCHAR(10) PRIMARY KEY,
    patient_id VARCHAR(10) NOT NULL,
    doctor_id VARCHAR(10) NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    consultation_room VARCHAR(20) NOT NULL,
    appointment_status ENUM('Scheduled', 'Completed', 'Canceled') DEFAULT 'Scheduled',
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id) ON DELETE CASCADE,
    CONSTRAINT unique_doctor_schedule UNIQUE (doctor_id, appointment_date, appointment_time)
);
    

CREATE TABLE room (
    room_id VARCHAR(10) PRIMARY KEY,
    category ENUM('General Ward', 'Private Room', 'ICU') NOT NULL , 
    room_availability ENUM('Available', 'Occupied') NOT NULL DEFAULT 'Available', 
    charge_per_day DECIMAL(10,2) DEFAULT 0.00
);

CREATE TABLE admission (
    admission_id VARCHAR(10) PRIMARY KEY,
    patient_id VARCHAR(10) NOT NULL,
    room_id VARCHAR(10) NOT NULL,
    admission_date DATE NOT NULL,
    bed_no VARCHAR(10) NOT NULL,
    admission_status ENUM('Admitted', 'Discharged') NOT NULL DEFAULT 'Admitted',
    discharge_date DATE NULL,
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES room(room_id) ON DELETE CASCADE
);


CREATE TABLE treatment_record (
    treatment_id VARCHAR(10) PRIMARY KEY,
    patient_id VARCHAR(10) NOT NULL,
    doctor_id VARCHAR(10) NOT NULL,
    diagnosis VARCHAR(255) NOT NULL,
    prescription_details VARCHAR(255),
    treatment_notes VARCHAR(255),
    treatment_date DATE NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id) ON DELETE CASCADE
);


CREATE TABLE lab_test (
    lab_test_id VARCHAR(10) PRIMARY KEY,
    patient_id VARCHAR(10) NOT NULL,
    doctor_id VARCHAR(10) NOT NULL,
    test_name VARCHAR(100) NOT NULL,
    test_date DATE NOT NULL,
    test_result VARCHAR(100) NULL,
    technician_name VARCHAR(100),
    lab_test_status ENUM('Pending', 'Completed') NOT NULL DEFAULT 'Pending',
    test_charge DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id) ON DELETE CASCADE
    );


CREATE TABLE bill (
    bill_id VARCHAR(10) PRIMARY KEY,
    patient_id VARCHAR(10) NOT NULL,
    appointment_id VARCHAR(10) NOT NULL,
    bill_date DATE NULL,
    consultation_charges DECIMAL(10,2) DEFAULT 0.00,
    room_charges DECIMAL(10,2) DEFAULT 0.00,
    lab_charges DECIMAL(10,2) DEFAULT 0.00,
    medicine_charges DECIMAL(10,2) DEFAULT 0.00,
    total_amount DECIMAL(10,2) CHECK (total_amount >= 0),
    payment_status ENUM('Paid', 'Unpaid', 'Pending') NOT NULL DEFAULT 'Unpaid',
    payment_method  ENUM('Cash', 'Card', 'Online') NULL,
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id) ON DELETE CASCADE
);

INSERT INTO department (department_id, department_name, location, head_doctor) VALUES 
('DE1','Cardiology', 'Heart Center - Level 3', NULL),
('DE2','Pediatrics','Block B - Level 2', NULL),
('DE3','Orthopedics', 'Block C - Level 1', NULL),
('DE4','Psychiatry', 'Mental Health Unit', NULL),
('DE5','Radiology', 'Ground Floor - Imaging Unit', NULL);

INSERT INTO doctor (doctor_id, doctor_name, specialization, qualification, contact_number, consultation_fee, department_id) VALUES 
('DO1','Dr. Nimal Perera', 'Cardiologist', 'MBBS, MD', '0771234567', 3500.00, 'DE1'),
('DO2','Dr. Sunethra Silva', 'Pediatrician', 'MBBS, DCH', '0712345678', 2500.00, 'DE2'),
('DO3','Dr. Kamal Fernando', 'Psychiatry', 'MBBS', '0753456789', 2000.00, 'DE4'),
('DO4','Dr. Anura Jayasinghe', 'Orthopedic Surgeon', 'MBBS, MS', '0784567890', 4000.00, 'DE3'),
('DO5','Dr. Chitra Gunawardena', 'Radiologist', 'MBBS, DMRD', '0765678901', 3000.00, 'DE5');

UPDATE department SET head_doctor = 'DO1' WHERE department_id = 'DE1'; 
UPDATE department SET head_doctor = 'DO2' WHERE department_id = 'DE2'; 
UPDATE department SET head_doctor = 'DO3' WHERE department_id = 'DE4'; 
UPDATE department SET head_doctor = 'DO4' WHERE department_id = 'DE3';
UPDATE department SET head_doctor = 'DO5' WHERE department_id = 'DE5';

INSERT INTO patient (patient_id, full_name, dob, gender, address, contact_number, blood_group, emergency_contact) VALUES 
('P1','Kusal Mendis', '1995-02-16', 'Male', 'Colombo', '0771112223', 'A+', '0770000001'),
('P2','Pathum Nissanka', '1998-05-18', 'Male', 'Kandy', '0772223334', 'B+', '0770000002'),
('P3','Charith Asalanka', '1997-06-29', 'Male', 'Galle', '0773334445', 'O+', '0770000003'),
('P4','Wanindu Hasaranga', '1997-07-29', 'Male', 'Matara', '0774445556', 'AB+', '0770000004'),
('P5','Dasun Shanaka', '1980-09-09', 'Male', 'Negombo', '0775556667', 'A-', '0770000005'),
('P6','Chamari Athapaththu', '1990-02-09', 'Female', 'Kurunegala', '0776667778', 'B-', '0770000006'),
('P7','Inoka Ranaweera', '1986-02-18', 'Female', 'Ratnapura', '0777778889', 'O-', '0770000007'),
('P8','Sadaf De Silva', '2000-11-12', 'Female', 'Colombo', '0778889990', 'A+', '0770000008'),
('P9','Maheesh Theekshana', '2000-08-01', 'Male', 'Gampaha', '0779990001', 'B+', '0770000009'),
('P10','Matheesha Pathirana', '2018-12-18', 'Male', 'Kandy', '0770001112', 'AB-', '0770000010'),
('P11','Dinesh Chandimal', '1989-11-18', 'Male', 'Kalutara', '0771113335', 'O+', '0770000011'),
('P12','Angelo Mathews', '1987-06-02', 'Male', 'Colombo', '0772224446', 'A+', '0770000012'),
('P13','Dhananjaya De Silva', '1991-09-06', 'Male', 'Panadura', '0773335557', 'B+', '0770000013'),
('P14','Harshitha Samarawickrama', '1998-06-29', 'Female', 'Galle', '0774446668', 'O-', '0770000014'),
('P15','Vishmi Gunaratne', '2005-08-22', 'Female', 'Ratnapura', '0775557779', 'AB+', '0770000015');


INSERT INTO appointment (appointment_id, patient_id, doctor_id, appointment_date, appointment_time, consultation_room, appointment_status) VALUES 
('A1', 'P1', 'DO1', '2026-07-10', '09:00:00', 'CR-101', 'Scheduled'),
('A2', 'P2', 'DO2', '2026-07-10', '09:30:00', 'CR-101', 'Canceled'),
('A3', 'P3', 'DO4', '2026-07-10', '10:00:00', 'CR-102', 'Completed'),
('A4', 'P4', 'DO3', '2026-07-11', '08:00:00', 'CR-103', 'Scheduled'),
('A5', 'P5', 'DO3', '2026-07-11', '11:00:00', 'CR-104', 'Completed'),
('A6', 'P6', 'DO5', '2026-07-12', '10:00:00', 'CR-102', 'Completed'),
('A7', 'P7', 'DO5', '2026-07-12', '14:00:00', 'CR-105', 'Scheduled'),
('A8', 'P8', 'DO1', '2026-07-13', '08:30:00', 'CR-103', 'Completed'),
('A9', 'P9', 'DO1', '2026-07-13', '09:00:00', 'CR-101', 'Scheduled'),
('A10', 'P11', 'DO2', '2026-07-14', '10:30:00', 'CR-102', 'Canceled'),
('A11', 'P10', 'DO2', '2026-07-14', '11:30:00', 'CR-104', 'Scheduled'),
('A12', 'P13', 'DO1', '2026-07-15', '09:00:00', 'CR-103', 'Scheduled'),
('A13', 'P14', 'DO5', '2026-07-15', '09:30:00', 'CR-101', 'Scheduled'),
('A14', 'P15', 'DO4', '2026-07-16', '14:30:00', 'CR-105', 'Completed'),
('A15', 'P12', 'DO4', '2026-07-16', '11:00:00', 'CR-102', 'Scheduled');


INSERT INTO room (room_id, category, room_availability, charge_per_day) VALUES 
('ICU-01', 'ICU', 'Occupied', 10000.00),
('ICU-02', 'ICU','Available', 10000.00),
('PR-101', 'Private Room', 'Occupied', 5000.00),
('PR-102', 'Private Room', 'Available', 5000.00),
('GW-201', 'General Ward', 'Available', 3000.00);

 INSERT INTO admission (admission_id, patient_id, room_id, admission_date, bed_no, admission_status, discharge_date) VALUES 
('ADM1', 'P15', 'PR-101', '2026-07-20', 'Bed-1', 'Admitted', NULL),
('ADM2', 'P8', 'GW-201', '2026-07-15', 'Bed-A', 'Admitted', NULL),
('ADM3', 'P5', 'PR-102', '2026-07-17', 'Bed-2', 'Discharged', '2026-07-23'),
('ADM4', 'P2', 'ICU-01', '2026-07-10', 'Bed-1', 'Discharged', '2026-07-13'), 
('ADM5', 'P2', 'GW-201', '2026-07-13', 'Bed-C', 'Admitted', NULL);

INSERT INTO treatment_record (treatment_id, patient_id, doctor_id, treatment_date, diagnosis, prescription_details, treatment_notes) VALUES
('T1', 'P3', 'DO4', '2026-07-12', 'Joint Pain - Right Wrist', 'NSAIDs, Blood test ordered', 'Routine blood work ordered to rule out inflammatory arthritis.'),
('T2', 'P8', 'DO1', '2026-07-15', 'Hypertension', 'Amlodipine 5mg daily', 'Monitor blood pressure weekly'),
('T3', 'P10', 'DO2', '2026-07-18', 'Arrhythmia Evaluation', 'Beta-Blocker 25mg', 'ECG conducted; follow-up scheduled in 2 weeks'),
('T4', 'P6', 'DO5', '2026-07-15', 'Chest Radiograph Findings', 'N/A - Imaging Diagnostic', 'X-Ray confirmed minor pulmonary congestion; report forwarded to attending physician'),
('T5', 'P5', 'DO3', '2026-07-13', 'Mild Anxiety', 'Anxiolytic as needed', 'Therapy sessions recommended');

INSERT INTO lab_test (lab_test_id, patient_id, doctor_id, test_name, test_date, test_result, technician_name, lab_test_status, test_charge) VALUES 
('L1', 'P1', 'DO1', 'ECG', '2026-07-11', NULL, 'Saman Perera', 'Pending', 1500.00),
('L2', 'P3', 'DO4','ESR & Full Blood Count', '2026-07-12', 'Inflammatory Markers Normal', 'Ruwan Silva', 'Completed', 2000.00),
('L3', 'P14', 'DO5', 'X-Ray Chest', '2026-07-16', 'Clear', 'Kamal Fernando', 'Completed', 2500.00);

INSERT INTO bill (bill_id, patient_id, appointment_id, bill_date, consultation_charges, room_charges, lab_charges, medicine_charges, total_amount, payment_status, payment_method) VALUES 
('B1',  'P1',  'A1',  '2026-07-11', 3500.00, 0.00,     0.00,    2000.00,  5500.00,  'Unpaid',  NULL),
('B2',  'P3',  'A3',  '2026-07-12', 4000.00, 0.00,     2000.00, 2000.00,  8000.00,  'Paid',   'Card'),
('B3',  'P4',  'A4',  '2026-07-12', 2000.00, 0.00,     0.00,    500.00,   2500.00,  'Unpaid',  NULL), 
('B4',  'P5',  'A5',  '2026-07-13', 2000.00, 30000.00, 0.00,    5000.00,  37000.00, 'Unpaid',  NULL),
('B5',  'P6',  'A6',  '2026-07-15', 3000.00, 0.00,     0.00,    1200.00,  4200.00,  'Paid',   'Online'),
('B6',  'P7',  'A7',  '2026-07-13', 3000.00, 0.00,     0.00,    800.00,   3800.00,  'Paid',   'Cash'),
('B7',  'P8',  'A8',   NULL,        3500.00, 0.00,     0.00,    400.00,   3900.00,  'Pending', NULL),
('B8',  'P10', 'A11', '2026-07-15', 2500.00, 0.00,     0.00,    1100.00,  3600.00,  'Paid',   'Card'), 
('B9',  'P9',  'A9',  '2026-07-17', 3500.00, 0.00,     0.00,    900.00,   4400.00,  'Unpaid',  NULL),
('B10', 'P14', 'A13', '2026-07-16', 3000.00, 0.00,     2500.00, 1500.00,  7000.00,  'Paid',   'Online'),
('B11', 'P2',  'A2',  '2026-07-13', 0.00,    30000.00, 0.00,    15000.00, 45000.00, 'Paid',   'Cash'),
('B12', 'P2',  'A2',   NULL,        0.00,    0.00,     0.00,    2000.00,  2000.00,  'Pending', NULL);

UPDATE lab_test 
SET test_result = 'Normal Sinus Rhythm', 
    lab_test_status = 'Completed' 
WHERE lab_test_id = 'L1';


UPDATE admission 
SET admission_status = 'Discharged', 
    discharge_date = CURDATE() 
WHERE admission_id = 'ADM1';

UPDATE room 
SET room_availability = 'Available' 
WHERE room_id = 'PR-101';

UPDATE bill 
SET payment_status = 'Paid', 
    payment_method = 'Cash', 
    bill_date = CURDATE() 
WHERE bill_id = 'B7';

SHOW DATABASES LIKE 'smartcare_db';
USE smartcare_db;
SHOW TABLES;

DESCRIBE department;
DESCRIBE doctor;
DESCRIBE patient;
DESCRIBE admission;
DESCRIBE appointment;
DESCRIBE bill;
DESCRIBE lab_test;
DESCRIBE room;
DESCRIBE treatment_record;

SHOW CREATE TABLE doctor; 
SHOW CREATE TABLE department;
SHOW CREATE TABLE patient;
SHOW CREATE TABLE admission;
SHOW CREATE TABLE appointment;
SHOW CREATE TABLE bill;
SHOW CREATE TABLE lab_test;
SHOW CREATE TABLE room;
SHOW CREATE TABLE treatment_record;

SELECT * FROM department;
SELECT * FROM doctor;
SELECT * FROM patient;
SELECT * FROM appointment;
SELECT * FROM bill;
SELECT * FROM admission;
SELECT * FROM room;
SELECT * FROM lab_test;
SELECT * FROM treatment_record;

