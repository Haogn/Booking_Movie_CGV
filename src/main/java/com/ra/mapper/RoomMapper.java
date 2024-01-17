package com.ra.mapper;

import com.ra.constant.RoomType;
import com.ra.dto.request.room.RoomRequest;
import com.ra.dto.response.room.RoomResponse;
import com.ra.entity.Room;
import com.ra.entity.Theater;
import com.ra.exception.CustomsException;
import com.ra.repository.ITheaterRepository;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    private ITheaterRepository theaterRepository ;
    public RoomResponse toResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .numberOfSeatsInAColumn(room.getNumberOfSeatsInAColumn())
                .numberOfSeatsInARow(room.getNumberOfSeatsInARow())
                .roomType(room.getRoomType().name())
                .isDeleted(room.getIsDeleted())
                .theaterName(room.getTheater().getTheaterName())
                .build();
    }

    public Room toEntity(RoomRequest roomRequest) throws CustomsException {
        Theater theater  = theaterRepository.findById(roomRequest.getTheaterId()).orElseThrow(() -> new CustomsException("Theater Not Found"));
        RoomType roomType = switch (roomRequest.getRoomType()) {
            case "2D" -> RoomType.TWO_D;
            case "3D" -> RoomType.THREE_D;
            case "4D" -> RoomType.FOUR_D;
            default -> throw new CustomsException("RoomType Not Found");
        };
        return Room.builder()
                .roomName(roomRequest.getRoomName())
                .numberOfSeatsInARow(roomRequest.getNumberOfSeatsInARow())
                .numberOfSeatsInAColumn(roomRequest.getNumberOfSeatsInAColumn())
                .roomType(roomType)
                .isDeleted(roomRequest.getIsDeleted())
                .theater(theater)
                .build();
    }
}
