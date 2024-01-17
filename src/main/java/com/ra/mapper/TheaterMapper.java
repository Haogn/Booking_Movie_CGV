package com.ra.mapper;

import com.ra.dto.request.theater.TheaterRequest;
import com.ra.dto.response.theater.TheaterResponse;
import com.ra.entity.Location;
import com.ra.entity.Theater;
import com.ra.exception.CustomsException;
import com.ra.repository.ILocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TheaterMapper {
    private ILocationRepository locationRepository ;
    public TheaterResponse toResponse(Theater theater) {
        return TheaterResponse.builder()
                .id(theater.getId())
                .theaterName(theater.getTheaterName())
                .isDeleted(theater.getIsDeleted())
                .locationName(theater.getLocation().getLocationName())
                .build();
    }

    public Theater toEntity(TheaterRequest theaterRequestDto) throws CustomsException {
        Location location = locationRepository.findById(theaterRequestDto.getLocationId()).orElseThrow(()-> new CustomsException("Location Not Found"));
        return Theater.builder()
                .theaterName(theaterRequestDto.getTheaterName())
                .isDeleted(theaterRequestDto.getIsDeleted())
                .location(location)
                .build();
    }
}
