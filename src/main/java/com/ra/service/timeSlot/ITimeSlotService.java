package com.ra.service.timeSlot;

import com.ra.dto.request.timeSlot.TimeSLotRequest;
import com.ra.dto.response.timeSlot.TimeSlotResponse;
import com.ra.exception.CustomsException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ITimeSlotService {
    List<TimeSlotResponse> findAll();
    TimeSlotResponse findById (Long id) throws CustomsException;
    TimeSlotResponse save(Authentication authentication , TimeSLotRequest timeSLotRequest) throws CustomsException;
    TimeSlotResponse update(Authentication authentication, Long id,TimeSLotRequest timeSLotRequest) throws CustomsException;
    void isDelete(Authentication authentication , Long id) throws CustomsException;
    void delete();
}
