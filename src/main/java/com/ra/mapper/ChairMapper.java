package com.ra.mapper;

import com.ra.constant.ChairType;
import com.ra.dto.request.chair.ChairRequest;
import com.ra.dto.response.chair.ChairResponse;
import com.ra.entity.Chair;
import com.ra.entity.Room;
import com.ra.exception.CustomsException;
import com.ra.repository.IRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChairMapper {
    private IRoomRepository roomRepository ;
    public ChairResponse toResponse(Chair chair) {
        return ChairResponse.builder()
                .id(chair.getId())
                .chairName(chair.getChairName())
                .chairType(chair.getChairType().name())
                .roomName(chair.getRoom().getRoomName())
                .isDeleted(chair.getIsDeleted())
                .build();
    }

    public Chair toEntity(ChairRequest chairRequest) throws CustomsException {
        Room room = roomRepository.findById(chairRequest.getRoomId()).orElseThrow(()-> new CustomsException("Room Not Found"));
        ChairType chairType = switch (chairRequest.getChairType()) {
            case "normal" -> ChairType.NORMAL;
            case "VIP" -> ChairType.VIP;
            default -> throw new CustomsException(chairRequest.getChairType() + " Not Found");
        };
        return Chair.builder()
                .chairName(chairRequest.getChairName())
                .chairType(chairType)
                .isDeleted(chairRequest.getIsDeleted())
                .room(room)
                .build();
    }
}
