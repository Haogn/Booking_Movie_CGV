package com.ra.repository;

import com.ra.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGenreRepository extends JpaRepository<Genre,Long> {
    Page<Genre> findAllByIsDeletedFalseAndGenreNameContainingIgnoreCase(String search, Pageable pageable) ;
    Page<Genre> findAllByIsDeletedFalse(Pageable pageable) ;
    Boolean existsByGenreName(String  name) ;
}
