CREATE VIEW vw_patient_admissions AS
SELECT 
    a.admission_id, 
    p.patient_id,
    p.full_name AS patient_name, 
    r.room_id, 
    r.category, 
    a.admission_date, 
    a.discharge_date,
    a.admission_status
FROM admission a
JOIN patient p ON a.patient_id = p.patient_id
JOIN room r ON a.room_id = r.room_id;

SELECT * FROM vw_patient_admissions;
