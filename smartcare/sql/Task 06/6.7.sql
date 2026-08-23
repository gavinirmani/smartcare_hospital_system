SELECT 
    d.doctor_id,
    d.doctor_name,
    d.specialization,
    COUNT(a.appointment_id) AS total_visits
FROM doctor d
JOIN appointment a ON d.doctor_id = a.doctor_id
GROUP BY d.doctor_id, d.doctor_name, d.specialization
ORDER BY total_visits DESC
LIMIT 1;