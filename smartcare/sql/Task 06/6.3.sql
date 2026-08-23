SELECT 
    a.appointment_id,
    p.patient_id,
    p.full_name AS patient_name,
    a.appointment_date,
    a.appointment_time,
    a.consultation_room,
    a.appointment_status
FROM appointment a
JOIN patient p ON a.patient_id = p.patient_id
WHERE a.doctor_id = 'DO1' AND a.appointment_status = 'Scheduled';