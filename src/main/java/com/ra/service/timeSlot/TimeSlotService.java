package com.ra.service.timeSlot;

import com.ra.dto.request.timeSlot.TimeSLotRequest;
import com.ra.dto.response.timeSlot.TimeSlotResponse;
import com.ra.entity.Movie;
import com.ra.entity.Room;
import com.ra.entity.TimeSlot;
import com.ra.exception.CustomsException;
import com.ra.mapper.TimeSlotMapper;
import com.ra.repository.IMovieRepository;
import com.ra.repository.IRoomRepository;
import com.ra.repository.ITimeSlotRepository;
import com.ra.security.user_principel.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeSlotService implements ITimeSlotService{
    private ITimeSlotRepository timeSlotRepository ;
    private IMovieRepository movieRepository ;
    private IRoomRepository roomRepository ;
    private TimeSlotMapper timeSlotMapper ;

    @Override
    public List<TimeSlotResponse> findAll() {
        List<TimeSlot> list = timeSlotRepository.findAllByIsDeletedFalse();
        return list.stream().map(item -> timeSlotMapper.toResponse(item)).collect(Collectors.toList());
    }

    @Override
    public TimeSlotResponse findById(Long id) throws CustomsException {
        TimeSlot timeSlot = timeSlotRepository.findById(id).orElseThrow(() -> new CustomsException("TimeSlot Not Found"));
        return timeSlotMapper.toResponse(timeSlot);
    }

    @Override
    public TimeSlotResponse save(Authentication authentication , TimeSLotRequest timeSLotRequest) throws CustomsException {
        TimeSlot timeSlot = timeSlotRepository.save(timeSlotMapper.toEntity(timeSLotRequest));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        timeSlot.setCreateTime(LocalDateTime.now());
        timeSlot.setUpdateTime(LocalDateTime.now());
        timeSlot.setCreateUser(userPrincipal.getUsername());
        TimeSlot create = timeSlotRepository.save(timeSlot);
        return timeSlotMapper.toResponse(create);
    }

    @Override
    public TimeSlotResponse update(Authentication authentication ,Long id, TimeSLotRequest timeSLotRequest) throws CustomsException {
        TimeSlot timeSlot = timeSlotRepository.findById(id).orElseThrow(() -> new CustomsException("TimeSlot Not Found"));
        Movie movie = movieRepository.findById(timeSLotRequest.getMovieId()).orElseThrow(() -> new CustomsException("Movie Not Found"));
        Room room = roomRepository.findById(timeSLotRequest.getRoomId()).orElseThrow(() -> new CustomsException("Room Not Found"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        timeSlot.setId(id);
        timeSlot.setMovie(movie);
        timeSlot.setRoom(room);
        timeSlot.setStartTime(timeSLotRequest.getStartTime());
        timeSlot.setUpdateTime(LocalDateTime.now());
        timeSlot.setUpdateUser(userPrincipal.getUsername());
        timeSlotRepository.save(timeSlot);
        return timeSlotMapper.toResponse(timeSlot);
    }

    @Override
    public void isDelete(Authentication authentication, Long id) throws CustomsException {
        TimeSlot timeSlot = timeSlotRepository.findById(id).orElseThrow(() -> new CustomsException("TimeSlot Not Found"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        timeSlot.setIsDeleted(!timeSlot.getIsDeleted());
        timeSlot.setUpdateTime(LocalDateTime.now());
        timeSlot.setUpdateUser(userPrincipal.getUsername());
        timeSlotRepository.save(timeSlot);
    }

    @Override
    @Scheduled(cron = "0 0 0 1 * ?")
    public void delete() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<TimeSlot> listTime = timeSlotRepository.findAllByIsDeletedTrueAndUpdateTimeBefore(oneMonthAgo);
        timeSlotRepository.deleteAll(listTime);

        List<TimeSlot> listTimeRoom = timeSlotRepository.findAllByRoomIsDeletedTrueAndUpdateTimeBefore(oneMonthAgo);
        timeSlotRepository.deleteAll(listTimeRoom);

        List<TimeSlot> listTimeMovie = timeSlotRepository.findAllByMovieIsDeletedTrueAndUpdateTimeBefore(oneMonthAgo);
        timeSlotRepository.deleteAll(listTimeMovie);
    }
}
