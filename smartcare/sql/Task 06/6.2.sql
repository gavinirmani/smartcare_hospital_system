SELECT 
    d.department_name,
    doc.doctor_id,
    doc.doctor_name,
    doc.specialization,
    doc.contact_number
FROM doctor doc
JOIN department d ON doc.department_id = d.department_id
ORDER BY d.department_name, doc.doctor_name;