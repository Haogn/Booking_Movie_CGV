package com.ra.service.genre;

import com.ra.dto.request.genre.GenreRequest;
import com.ra.dto.response.genre.GenreResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface IGenreService {
    Page<GenreResponse> findAll(String search , Pageable pageable) ;
    GenreResponse findById(Long id) ;
    GenreResponse save(Authentication authentication, GenreRequest genreRequest) ;
}
