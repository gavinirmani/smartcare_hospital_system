SELECT 
    lt.lab_test_id,
    p.full_name AS patient_name,
    lt.test_name,
    lt.test_date,
    lt.test_result,
    lt.technician_name,
    lt.lab_test_status
FROM lab_test lt
JOIN patient p ON lt.patient_id = p.patient_id
WHERE lt.lab_test_status = 'Completed' 
  AND lt.test_date BETWEEN '2026-07-01' AND '2026-07-31';