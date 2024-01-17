package com.ra.service.location;

import com.ra.dto.request.location.LocationRequest;
import com.ra.dto.response.location.LocationResponse;
import com.ra.exception.CustomsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface ILocationService {
    Page<LocationResponse> findAll(String locationName , Pageable pageable) ;
    LocationResponse findById (Long id) throws CustomsException;

    LocationResponse save (Authentication authentication, LocationRequest locationRequest) throws CustomsException;

    LocationResponse update(Authentication authentication,Long id , LocationRequest locationRequest) throws CustomsException;

    void isDelete (Authentication authentication, Long id) throws CustomsException;
    void delete () ;
}
