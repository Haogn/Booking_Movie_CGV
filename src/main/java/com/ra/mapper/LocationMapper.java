package com.ra.mapper;

import com.ra.dto.request.location.LocationRequest;
import com.ra.dto.response.location.LocationResponse;
import com.ra.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public LocationResponse toResponse(Location location) {
        return LocationResponse.builder()
                .id(location.getId())
                .locationName(location.getLocationName())
                .isDelete(location.getIsDelete())
                .build();
    }

    public Location toEntity(LocationRequest locationRequest) {
        return Location.builder()
                .locationName(locationRequest.getLocationName())
                .isDelete(locationRequest.getIsDelete())
                .build();
    }
}
