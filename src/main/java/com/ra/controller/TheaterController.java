package com.ra.controller;

import com.ra.dto.request.theater.TheaterRequest;
import com.ra.exception.CustomsException;
import com.ra.service.theater.ITheaterService;
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
@RequestMapping("/api/booking/v1/theater")
@AllArgsConstructor
public class TheaterController {
    private ITheaterService theaterService ;
    @GetMapping()
    public ResponseEntity<?> getAll (@RequestParam(defaultValue = "") String search ,
                                     @PageableDefault(size = 6, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(theaterService.findAll(search,pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomsException {
        return new ResponseEntity<>(theaterService.findById(id), HttpStatus.OK) ;
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid Authentication authentication, @RequestBody TheaterRequest theaterRequest) throws CustomsException {
        return new ResponseEntity<>(theaterService.save(authentication,theaterRequest), HttpStatus.CREATED) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid Authentication authentication, @PathVariable Long id, @RequestBody TheaterRequest theaterRequest) throws  CustomsException {
        return new ResponseEntity<>(theaterService.update(authentication,id, theaterRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> isDelete(Authentication authentication,@PathVariable Long id) throws CustomsException {
        theaterService.isDelete(authentication,id);
        String success = "Theater deleted" ;
        return new ResponseEntity<>(success, HttpStatus.OK);
    }
}
