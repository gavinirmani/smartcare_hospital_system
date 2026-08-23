SELECT 
    p.patient_id,
    p.full_name AS patient_name,
    p.contact_number,
    COUNT(a.appointment_id) AS total_appointments
FROM patient p
JOIN appointment a ON p.patient_id = a.patient_id
GROUP BY p.patient_id, p.full_name, p.contact_number
HAVING COUNT(a.appointment_id) > 1;