CREATE VIEW vw_unpaid_bills AS
SELECT 
    b.bill_id, 
    p.full_name AS patient_name, 
    p.contact_number, 
    b.total_amount, 
    b.bill_date,
    b.payment_status
FROM bill b
JOIN patient p ON b.patient_id = p.patient_id
WHERE b.payment_status = 'Unpaid';

SELECT * FROM vw_unpaid_bills;