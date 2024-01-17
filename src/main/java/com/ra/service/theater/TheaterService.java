package com.ra.service.theater;

import com.ra.dto.request.theater.TheaterRequest;
import com.ra.dto.response.theater.TheaterResponse;
import com.ra.entity.Location;
import com.ra.entity.Theater;
import com.ra.exception.CustomsException;
import com.ra.mapper.TheaterMapper;
import com.ra.repository.ILocationRepository;
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
public class TheaterService implements ITheaterService{
    private ITheaterRepository theaterRepository ;
    private TheaterMapper theaterMapper ;
    private ILocationRepository locationRepository ;

    @Override
    public Page<TheaterResponse> findAll(String search, Pageable pageable) {
        Page<Theater> theaterPage;
        if (search.isEmpty()) {
            theaterPage = theaterRepository.findAllByIsDeletedFalse( pageable);
        } else {
            theaterPage = theaterRepository.findAllByTheaterNameContainingIgnoreCaseAndIsDeletedFalse(search,  pageable);
        }
        return theaterPage.map(item -> theaterMapper.toResponse(item));
    }

    @Override
    public TheaterResponse findById(Long id) throws CustomsException {
        Theater theater = theaterRepository.findById(id).orElseThrow(()-> new CustomsException("Theater Not Found")) ;
        return theaterMapper.toResponse(theater);
    }

    @Override
    public TheaterResponse save(Authentication authentication, TheaterRequest theaterRequest) throws CustomsException{
        if (theaterRepository.existsByTheaterName(theaterRequest.getTheaterName())){
            throw new CustomsException("Exits TheaterName") ;
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Theater theater  =theaterMapper.toEntity(theaterRequest);
        theater.setCreateTime(LocalDateTime.now());
        theater.setUpdateTime(LocalDateTime.now());
        theater.setCreateUser(userPrincipal.getUsername());
        theaterRepository.save(theater);
        return theaterMapper.toResponse(theater);
    }

    @Override
    public TheaterResponse update(Authentication authentication,Long id, TheaterRequest theaterRequest) throws CustomsException {
        Theater theater = theaterRepository.findById(id).orElseThrow(() -> new CustomsException("Theater Not Found"));
        Location location = locationRepository.findById(theaterRequest.getLocationId()).orElseThrow(() -> new CustomsException("Location Not Found")) ;
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        theater.setId(id);
        theater.setTheaterName(theaterRequest.getTheaterName());
        theater.setLocation(location);
        theater.setCreateTime(LocalDateTime.now());
        theater.setUpdateTime(LocalDateTime.now());
        theater.setUpdateUser(userPrincipal.getUsername());
        theater.setIsDeleted(theaterRequest.getIsDeleted());
        theaterRepository.save(theater);
        return theaterMapper.toResponse(theater);
    }

    @Override
    public void isDelete(Authentication authentication,Long id) throws CustomsException {
        Theater theater = theaterRepository.findById(id).orElseThrow(() -> new CustomsException("Theater Not Found"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        theater.setIsDeleted(!theater.getIsDeleted());
        theater.setUpdateTime(LocalDateTime.now());
        theater.setUpdateUser(userPrincipal.getUsername());
        theaterRepository.save(theater);

    }

    @Override
    @Scheduled(cron = "0 0 0 1 * ?")
    public void delete() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<Theater> list = theaterRepository.findAllByIsDeletedTrueAndUpdateTimeBefore(oneMonthAgo);
        theaterRepository.deleteAll(list);
    }
}
