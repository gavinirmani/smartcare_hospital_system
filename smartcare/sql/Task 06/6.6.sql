SELECT 
    SUM(total_amount) AS total_hospital_revenue 
FROM bill 
WHERE payment_status = 'Paid';