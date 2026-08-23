SELECT 
    room_id,
    category,
    charge_per_day,
    room_availability
FROM room
ORDER BY room_availability, category;