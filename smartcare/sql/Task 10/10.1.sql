DELIMITER //

CREATE TRIGGER trg_after_patient_admitted
AFTER INSERT ON admission
FOR EACH ROW
BEGIN
    UPDATE room 
    SET room_availability = 'Occupied' 
    WHERE room_id = NEW.room_id;
END //

DELIMITER ;


INSERT INTO admission (admission_id, patient_id, room_id, admission_date, bed_no, admission_status, discharge_date)
VALUES ('ADM6', 'P4', 'ICU-02', '2026-08-08', 'Bed-1', 'Admitted', NULL);

SELECT room_id, category, room_availability FROM room WHERE room_id = 'ICU-02';