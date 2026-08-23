DELIMITER //

CREATE PROCEDURE sp_discharge_patient(
    IN p_admission_id VARCHAR(10)
)
BEGIN
    DECLARE v_room_id VARCHAR(10);
    

    SELECT room_id INTO v_room_id FROM admission WHERE admission_id = p_admission_id;
    

    UPDATE admission 
    SET discharge_date = CURDATE(), admission_status = 'Discharged' 
    WHERE admission_id = p_admission_id;
    
    UPDATE room 
    SET room_availability = 'Available' 
    WHERE room_id = v_room_id;
END //

DELIMITER ;


SELECT admission_id, patient_id, room_id, admission_status, discharge_date 
FROM admission 
WHERE admission_id = 'ADM1';

SELECT room_id, category, room_availability 
FROM room 
WHERE room_id = 'PR-101';


CALL sp_discharge_patient('ADM1');

SELECT admission_id, patient_id, room_id, admission_status, discharge_date 
FROM admission 
WHERE admission_id = 'ADM1';

SELECT room_id, category, room_availability 
FROM room 
WHERE room_id = 'PR-101';