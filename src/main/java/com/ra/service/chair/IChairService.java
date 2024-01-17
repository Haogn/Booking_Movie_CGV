package com.ra.service.chair;

import com.ra.dto.request.chair.ChairRequest;
import com.ra.dto.request.chair.ChairTypeRequest;
import com.ra.dto.response.chair.ChairResponse;
import com.ra.exception.CustomsException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IChairService {
    List<ChairResponse> findAll() ;
    ChairResponse findById(Long id) throws CustomsException;

    ChairResponse changeChairType (Authentication authentication, Long id , ChairTypeRequest chairTypeRequest) throws CustomsException;
    ChairResponse update(Authentication authentication,Long id , ChairRequest chairRequest) throws CustomsException;
    void isDelete(Authentication authentication,Long id) throws CustomsException;
    void delete();
}
