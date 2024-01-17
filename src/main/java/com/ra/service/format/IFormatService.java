package com.ra.service.format;

import com.ra.dto.request.format.FormatRequest;
import com.ra.dto.response.format.FormatResponse;
import com.ra.exception.CustomsException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IFormatService {
    List<FormatResponse> findAll(String search, Pageable pageable);
    FormatResponse findById(Long id) throws CustomsException;
    FormatResponse save(Authentication authentication, FormatRequest formatRequest) throws CustomsException;
    FormatResponse update(Authentication authentication,Long id,  FormatRequest formatRequest) throws CustomsException;
    void isDelete(Authentication authentication, Long id) throws CustomsException;
}
