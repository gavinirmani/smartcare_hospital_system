DELIMITER //

CREATE FUNCTION fn_get_doctor_appointment_count(p_doctor_id VARCHAR(10))
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE total INT;
    SELECT COUNT(*) INTO total FROM appointment WHERE doctor_id = p_doctor_id;
    RETURN total;
END //

DELIMITER ;


SELECT doctor_id, doctor_name, fn_get_doctor_appointment_count(doctor_id) AS total_appointments 
FROM doctor;