SELECT 
    adm.admission_id,
    p.patient_id,
    p.full_name AS patient_name,
    r.room_id,
    r.category AS room_category,
    adm.bed_no,
    adm.admission_date,
    adm.admission_status
FROM admission adm
JOIN patient p ON adm.patient_id = p.patient_id
JOIN room r ON adm.room_id = r.room_id
WHERE r.category = 'ICU';