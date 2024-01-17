package com.ra.service.room;

import com.ra.constant.ChairType;
import com.ra.constant.RoomType;
import com.ra.dto.request.room.RoomRequest;
import com.ra.dto.request.room.RoomUpdateRequest;
import com.ra.dto.response.room.RoomResponse;
import com.ra.entity.Chair;
import com.ra.entity.Room;
import com.ra.entity.Theater;
import com.ra.exception.CustomsException;
import com.ra.mapper.RoomMapper;
import com.ra.repository.IChairRepository;
import com.ra.repository.IRoomRepository;
import com.ra.repository.ITheaterRepository;
import com.ra.security.user_principel.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomService implements IRoomService {
    private IRoomRepository roomRepository ;
    private RoomMapper roomMapper;
    private IChairRepository chairRepository ;
    private ITheaterRepository theaterRepository ;
    @Override
    public Page<RoomResponse> findAll(String search, Pageable pageable) {
        Page<Room> roomPage ;
        if (search.isEmpty()) {
            roomPage = roomRepository.findAllByIsDeletedFalse( pageable) ;
        } else  {
            roomPage = roomRepository.findAllByRoomNameContainingIgnoreCaseAndIsDeletedFalse(search, pageable);
        }
        return roomPage.map(item -> roomMapper.toResponse(item));
    }

    @Override
    public RoomResponse findById(Long id) throws CustomsException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new CustomsException("Room Not Found")) ;
        return roomMapper.toResponse(room);
    }

    @Override
    public RoomResponse save(Authentication authentication, RoomRequest roomRequest) throws  CustomsException {
        if (roomRepository.existsByRoomName(roomRequest.getRoomName())){
            throw new CustomsException("Exits RoomName") ;
        }
        if (roomRequest.getNumberOfSeatsInARow() < 0 && roomRequest.getNumberOfSeatsInAColumn() < 0 ) {
            throw new CustomsException("The number of seats cannot be negative") ;
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int numberOfRows = Math.min(roomRequest.getNumberOfSeatsInARow(), string.length());

        Room room = roomMapper.toEntity(roomRequest);
        room.setCreateTime(LocalDateTime.now());
        room.setUpdateTime(LocalDateTime.now());
        room.setCreateUser(userPrincipal.getUsername());
        roomRepository.save(room);



        for (int i = 0; i < numberOfRows; i++) {
            String chairNameOf = String.valueOf(string.charAt(i));
            for (int j = 0; j < roomRequest.getNumberOfSeatsInAColumn(); j++) {
                Chair chair = new Chair();
                chair.setChairName(chairNameOf + (j + 1));
                // Sử dụng điều kiện để xác định loại ghế
                chair.setChairType(i < 5 ? ChairType.NORMAL : ChairType.VIP);
                chair.setRoom(room);
                chair.setIsDeleted(false);
                chair.setCreateTime(LocalDateTime.now());
                chair.setUpdateTime(LocalDateTime.now());
                chair.setCreateUser(userPrincipal.getUsername());
                chairRepository.save(chair);
            }
        }
        return roomMapper.toResponse(room);
    }



    @Override
    public RoomResponse update(Authentication authentication, Long id, RoomUpdateRequest roomUpdateRequest) throws CustomsException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new CustomsException("Room Not Found"));
        Theater theater = theaterRepository.findById(roomUpdateRequest.getTheaterId()).orElseThrow(()-> new CustomsException("Theater Not Found"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        RoomType roomType = switch (roomUpdateRequest.getRoomType()) {
            case "2D" -> RoomType.TWO_D;
            case "3D" -> RoomType.THREE_D;
            case "4D" -> RoomType.FOUR_D;
            default -> throw new CustomsException("RoomType Not Found");
        };
        room.setId(id);
        room.setRoomName(roomUpdateRequest.getRoomName());
        room.setRoomType(roomType);
        room.setTheater(theater);
        room.setUpdateTime(LocalDateTime.now());
        room.setUpdateUser(userPrincipal.getUsername());
        roomRepository.save(room);
        return roomMapper.toResponse(room);
    }

    @Override
    public void isDelete(Authentication authentication,Long id) throws CustomsException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Room room = roomRepository.findById(id).orElseThrow(() -> new CustomsException("Room Not Found"));
        room.setIsDeleted(!room.getIsDeleted());
        room.setUpdateTime(LocalDateTime.now());
        room.setUpdateUser(userPrincipal.getUsername());
        roomRepository.save(room);
    }

    @Override
    @Scheduled(cron = "0 0 0 1 * ?")
    public void delete() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<Room> list = roomRepository.findAllByIsDeletedTrueAndUpdateTimeBefore(oneMonthAgo);
        roomRepository.deleteAll(list);
    }
}
