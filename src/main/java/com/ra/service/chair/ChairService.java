package com.ra.service.chair;

import com.ra.constant.ChairType;
import com.ra.dto.request.chair.ChairRequest;
import com.ra.dto.request.chair.ChairTypeRequest;
import com.ra.dto.response.chair.ChairResponse;
import com.ra.entity.Chair;
import com.ra.entity.Room;
import com.ra.exception.CustomsException;
import com.ra.mapper.ChairMapper;
import com.ra.repository.IChairRepository;
import com.ra.repository.IRoomRepository;
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
public class ChairService implements IChairService {
    private IChairRepository chairRepository ;
    private IRoomRepository roomRepository ;
    private ChairMapper chairMapper ;

    @Override
    public List<ChairResponse> findAll() {
        List<Chair> list = chairRepository.findAllByIsDeletedFalse() ;
        return list.stream().map(item -> chairMapper.toResponse(item)).collect(Collectors.toList());
    }

    @Override
    public ChairResponse findById(Long id) throws CustomsException {
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new CustomsException("Chair Not Found")) ;
        return chairMapper.toResponse(chair);
    }

    @Override
    public ChairResponse changeChairType(Authentication authentication, Long id, ChairTypeRequest chairTypeRequest) throws CustomsException {
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new CustomsException("Chair Not Found")) ;
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        switch (chairTypeRequest.getChairType()) {
            case "normal":
                chair.setChairType(ChairType.NORMAL);
                break;
            case "VIP":
                chair.setChairType(ChairType.VIP);
                break;
            default:
                throw new CustomsException("ChairType Not Found");
        }
        chair.setUpdateTime(LocalDateTime.now());
        chair.setUpdateUser(userPrincipal.getUsername());
        chairRepository.save(chair);
        return chairMapper.toResponse(chair);
    }


    @Override
    public ChairResponse update(Authentication authentication,Long id, ChairRequest chairRequest) throws CustomsException {
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new CustomsException("Chair Not Found")) ;
        Room room = roomRepository.findById(chairRequest.getRoomId()).orElseThrow(()-> new CustomsException("Room Not Found")) ;
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        chair.setId(id);
        chair.setChairName(chairRequest.getChairName());
        switch (chairRequest.getChairType()) {
            case "normal":
                chair.setChairType(ChairType.NORMAL);
                break;
            case "VIP":
                chair.setChairType(ChairType.VIP);
                break;
            default:
                throw new CustomsException(chairRequest.getChairType() + " Not Found");
        }
        chair.setRoom(room);
        chair.setUpdateTime(LocalDateTime.now());
        chair.setUpdateUser(userPrincipal.getUsername());
        chair.setIsDeleted(chairRequest.getIsDeleted());
        chairRepository.save(chair);
        return chairMapper.toResponse(chair);
    }

    @Override
    public void isDelete(Authentication authentication,Long id) throws CustomsException {
        Chair chair = chairRepository.findById(id).orElseThrow(()-> new CustomsException("Chair Not Found")) ;
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        chair.setIsDeleted(!chair.getIsDeleted());
        chair.setUpdateTime(LocalDateTime.now());
        chair.setUpdateUser(userPrincipal.getUsername());
        chairRepository.save(chair);
    }

    @Override
    @Scheduled(cron = "0 0 0 1 * ?")
    public void delete() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<Chair> list = chairRepository.findAllByIsDeletedTrueAndUpdateTimeBefore(oneMonthAgo);
        chairRepository.deleteAll(list);
    }
}
