DELIMITER //

CREATE FUNCTION fn_calculate_total_bill(
    p_consultation DECIMAL(10,2),
    p_room DECIMAL(10,2),
    p_lab DECIMAL(10,2),
    p_medicine DECIMAL(10,2)
) 
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    RETURN IFNULL(p_consultation, 0.00) 
         + IFNULL(p_room, 0.00) 
         + IFNULL(p_lab, 0.00) 
         + IFNULL(p_medicine, 0.00);
END //

DELIMITER ;

SELECT 
    bill_id, 
    consultation_charges, 
    room_charges, 
    lab_charges, 
    medicine_charges,
    fn_calculate_total_bill(consultation_charges, room_charges, lab_charges, medicine_charges) AS calculated_total
FROM bill;