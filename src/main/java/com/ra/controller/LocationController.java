package com.ra.controller;

import com.ra.dto.request.location.LocationRequest;
import com.ra.exception.CustomsException;
import com.ra.service.location.ILocationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class LocationController {
    private ILocationService locationService ;
    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(name = "search",required = false, defaultValue = "") String search,
                                    @PageableDefault(size = 6, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(locationService.findAll(search, pageable), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id ) throws CustomsException {
        return new ResponseEntity<>(locationService.findById(id), HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<?> create(@Valid Authentication authentication, @RequestBody LocationRequest locationRequest) throws CustomsException {
        return new ResponseEntity<>(locationService.save(authentication,locationRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid Authentication authentication, @PathVariable Long id , @RequestBody LocationRequest locationRequest) throws CustomsException {
        return new ResponseEntity<>(locationService.update(authentication,id, locationRequest), HttpStatus.OK) ;
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> isDelete(Authentication authentication,@PathVariable Long id) throws CustomsException {
        locationService.isDelete(authentication,id) ;
        String success = "Location Deleted" ;
        return new ResponseEntity<>(success, HttpStatus.OK);
    }
}
