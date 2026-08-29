package com.smartcare.repository;

import com.smartcare.entity.Room;
import com.smartcare.entity.RoomAvailability;
import com.smartcare.entity.RoomCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    List<Room> findByCategory(RoomCategory category);

    List<Room> findByRoomAvailability(RoomAvailability roomAvailability);

    List<Room> findByCategoryAndRoomAvailability(
            RoomCategory category,
            RoomAvailability roomAvailability
    );
}