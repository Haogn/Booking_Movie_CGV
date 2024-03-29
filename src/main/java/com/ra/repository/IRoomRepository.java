package com.ra.repository;

import com.ra.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IRoomRepository extends JpaRepository<Room , Long> {
    Page<Room> findAllByRoomNameContainingIgnoreCaseAndIsDeletedFalse(String search, Pageable pageable) ;
    Page<Room> findAllByIsDeletedFalse( Pageable pageable) ;
    List<Room> findAllByIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime);
    List<Room> findAllByTheaterIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime);
    Boolean existsByRoomName(String name) ;
}
