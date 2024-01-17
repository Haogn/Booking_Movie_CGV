package com.ra.mapper;

import com.ra.dto.request.timeSlot.TimeSLotRequest;
import com.ra.dto.response.timeSlot.TimeSlotResponse;
import com.ra.entity.Movie;
import com.ra.entity.Room;
import com.ra.entity.TimeSlot;
import com.ra.exception.CustomsException;
import com.ra.repository.IMovieRepository;
import com.ra.repository.IRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TimeSlotMapper {
    private IMovieRepository movieRepository ;
    private IRoomRepository roomRepository ;

    public TimeSlotResponse toResponse (TimeSlot timeSlot) {
        return TimeSlotResponse.builder()
                .id(timeSlot.getId())
                .movieName(timeSlot.getMovie().getMovieName())
                .roomName(timeSlot.getRoom().getRoomName())
                .startTime(timeSlot.getStartTime())
                .build();
    }

    public TimeSlot toEntity(TimeSLotRequest timeSLotRequest) throws CustomsException {
        Movie movie = movieRepository.findById(timeSLotRequest.getMovieId()).orElseThrow(() -> new CustomsException("Movie Not Found"));
        Room room = roomRepository.findById(timeSLotRequest.getRoomId()).orElseThrow(() -> new CustomsException("Room Not Found"));
        return TimeSlot.builder()
                .movie(movie)
                .room(room)
                .startTime(timeSLotRequest.getStartTime())
                .isDeleted(timeSLotRequest.getIsDeleted())
                .build();
    }
}
