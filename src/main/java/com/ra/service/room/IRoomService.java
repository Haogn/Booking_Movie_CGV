package com.ra.service.room;

import com.ra.dto.request.room.RoomRequest;
import com.ra.dto.request.room.RoomUpdateRequest;
import com.ra.dto.response.room.RoomResponse;
import com.ra.exception.CustomsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface IRoomService {
    Page<RoomResponse> findAll(String search, Pageable pageable) ;

    RoomResponse findById(Long id) throws CustomsException;

    RoomResponse save (Authentication authentication, RoomRequest roomRequest) throws  CustomsException;


    RoomResponse update(Authentication authentication, Long id, RoomUpdateRequest roomUpdateRequest) throws CustomsException;

    void isDelete (Authentication authentication, Long id) throws CustomsException;
    void delete();
}
