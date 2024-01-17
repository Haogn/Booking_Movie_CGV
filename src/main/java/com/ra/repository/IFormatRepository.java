package com.ra.repository;

import com.ra.entity.Format;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFormatRepository extends JpaRepository<Format, Long> {
    List<Format> findAllByIsDeletedFalseAndFormatNameContainingIgnoreCase(String search, Pageable pageable);
    List<Format> findAllByIsDeletedFalse(Pageable pageable) ;
    Boolean existsByFormatName(String name) ;

}
