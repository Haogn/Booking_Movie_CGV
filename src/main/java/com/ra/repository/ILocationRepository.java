package com.ra.repository;

import com.ra.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ILocationRepository extends JpaRepository<Location , Long> {
    Page<Location> findAllByLocationNameContainingIgnoreCaseAndIsDeleteFalse(String search, Pageable pageable);
    List<Location> findAllByIsDeleteTrueAndUpdateTimeBefore(LocalDateTime localDateTime) ;
    Page<Location> findAllByIsDeleteFalse( Pageable pageable) ;
    Boolean existsByLocationName (String locationName) ;
}
