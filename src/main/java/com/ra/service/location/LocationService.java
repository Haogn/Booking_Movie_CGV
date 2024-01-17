package com.ra.service.location;

import com.ra.dto.request.location.LocationRequest;
import com.ra.dto.response.location.LocationResponse;
import com.ra.entity.Location;
import com.ra.exception.CustomsException;
import com.ra.mapper.LocationMapper;
import com.ra.repository.ILocationRepository;
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
public class LocationService implements ILocationService {

    private ILocationRepository locationRepository ;
    private LocationMapper locationMapper ;

    @Override
    public Page<LocationResponse> findAll(String search, Pageable pageable) {
        Page<Location> locationPage ;
        if (search.isEmpty()) {
            locationPage = locationRepository.findAllByIsDeleteFalse( pageable);
        } else  {
            locationPage = locationRepository.findAllByLocationNameContainingIgnoreCaseAndIsDeleteFalse(search,  pageable);
        }
        return locationPage.map(item -> locationMapper.toResponse(item));
    }

    @Override
    public LocationResponse findById(Long id) throws CustomsException {
        Location location = locationRepository.findById(id).orElseThrow(() -> new CustomsException("Location Not Fount"));
        return locationMapper.toResponse(location);
    }

    @Override
    public LocationResponse save(Authentication authentication, LocationRequest locationRequest) throws CustomsException {
        if (locationRepository.existsByLocationName(locationRequest.getLocationName())){
            throw new CustomsException("Exits LocationName");
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Location location = locationMapper.toEntity(locationRequest);
        location.setCreateTime(LocalDateTime.now());
        location.setUpdateTime(LocalDateTime.now());
        location.setCreateUser(userPrincipal.getUsername());
        locationRepository.save(location);
        return locationMapper.toResponse(location);
    }

    @Override
    public LocationResponse update(Authentication authentication,Long id, LocationRequest locationRequest) throws CustomsException {
        Location location = locationRepository.findById(id).orElseThrow(() -> new CustomsException("Location Not Fount"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        location.setId(id);
        location.setLocationName(locationRequest.getLocationName());
        location.setUpdateTime(LocalDateTime.now());
        location.setUpdateUser(userPrincipal.getUsername());
        location.setIsDelete(locationRequest.getIsDelete());
        locationRepository.save(location);
        return locationMapper.toResponse(location);
    }

    @Override
    public void isDelete(Authentication authentication,Long id) throws CustomsException {
        Location location = locationRepository.findById(id).orElseThrow(() -> new CustomsException("Location Not Fount"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        location.setIsDelete(!location.getIsDelete());
        location.setUpdateTime(LocalDateTime.now());
        location.setUpdateUser(userPrincipal.getUsername());
        locationRepository.save(location);
    }

    @Override
    @Scheduled(cron = "0 0 0 1 * ?")
    public void delete() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<Location> list = locationRepository.findAllByIsDeleteTrueAndUpdateTimeBefore(oneMonthAgo);
        locationRepository.deleteAll(list);
    }
}
