DELIMITER //

CREATE PROCEDURE sp_book_appointment(
    IN p_appointment_id VARCHAR(10),
    IN p_patient_id VARCHAR(10),
    IN p_doctor_id VARCHAR(10),
    IN p_date DATE,
    IN p_time TIME,
    IN p_room VARCHAR(20)
)
BEGIN
    INSERT INTO appointment (
        appointment_id, patient_id, doctor_id, 
        appointment_date, appointment_time, consultation_room, appointment_status
    )
    VALUES (
        p_appointment_id, p_patient_id, p_doctor_id, 
        p_date, p_time, p_room, 'Scheduled'
    );
END //

DELIMITER ;

CALL sp_book_appointment('A16', 'P1', 'DO2', '2026-08-15', '10:00:00', 'CR-101');
SELECT * FROM appointment WHERE appointment_id = 'A16';