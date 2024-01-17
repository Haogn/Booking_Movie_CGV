package com.ra.controller;

import com.ra.dto.request.timeSlot.TimeSLotRequest;
import com.ra.exception.CustomsException;
import com.ra.service.timeSlot.ITimeSlotService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/booking/v1/timeSlot")
@AllArgsConstructor
public class TimeSlotController {
    private ITimeSlotService timeSlotService ;

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(timeSlotService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomsException {
        return new ResponseEntity<>(timeSlotService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> create (@Valid Authentication authentication, @RequestBody TimeSLotRequest timeSLotRequest) throws CustomsException {
        return  new ResponseEntity<>(timeSlotService.save(authentication,timeSLotRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid Authentication authentication , @PathVariable Long id , @RequestBody TimeSLotRequest timeSLotRequest) throws CustomsException {
        return new ResponseEntity<>(timeSlotService.update(authentication, id, timeSLotRequest), HttpStatus.OK);
    }

//    @DeleteMapping()
//    public ResponseEntity<?> delete() {
//        timeSlotService.delete();
//        String success = "Theater deleted" ;
//        return new ResponseEntity<>(success, HttpStatus.OK);
//    }
}
