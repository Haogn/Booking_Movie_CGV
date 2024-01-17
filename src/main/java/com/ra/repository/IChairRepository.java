package com.ra.repository;

import com.ra.entity.Chair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IChairRepository extends JpaRepository<Chair, Long> {
    List<Chair> findAllByIsDeletedFalse() ;

    List<Chair> findAllByIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime) ;
    List<Chair> findAllByRoomIsDeletedTrueAndUpdateTimeBefore(LocalDateTime localDateTime) ;

    Boolean existsByChairNameAndRoomId(String name , Long idRoom) ;
}
