DELIMITER //

CREATE TRIGGER trg_before_appointment_insert
BEFORE INSERT ON appointment
FOR EACH ROW
BEGIN
    IF NEW.appointment_date < CURDATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: Cannot book appointments for past dates!';
    END IF;
END //

DELIMITER ;

INSERT INTO appointment (appointment_id, patient_id, doctor_id, appointment_date, appointment_time, consultation_room, appointment_status)
VALUES ('A17', 'P2', 'DO1', '2025-01-01', '09:00:00', 'CR-101', 'Scheduled');