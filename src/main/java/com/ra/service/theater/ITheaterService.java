package com.ra.service.theater;

import com.ra.dto.request.theater.TheaterRequest;
import com.ra.dto.response.theater.TheaterResponse;
import com.ra.exception.CustomsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface ITheaterService {
    Page<TheaterResponse> findAll(String search , Pageable pageable) ;

    TheaterResponse findById (Long id ) throws CustomsException;

    TheaterResponse save(Authentication authentication, TheaterRequest theaterRequest) throws CustomsException;

    TheaterResponse update(Authentication authentication,Long id , TheaterRequest theaterRequest) throws CustomsException;

    void isDelete(Authentication authentication,Long id) throws CustomsException;

    void delete() ;
}
