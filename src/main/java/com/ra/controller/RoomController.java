package com.ra.controller;

import com.ra.dto.request.room.RoomRequest;
import com.ra.dto.request.room.RoomUpdateRequest;
import com.ra.exception.CustomsException;
import com.ra.service.room.IRoomService;
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
@RequestMapping("/api/booking/v1/room")
@AllArgsConstructor
public class RoomController {
    private IRoomService roomService ;

    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "") String search ,
                                    @PageableDefault(size = 6, page = 0, sort = "id" , direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(roomService.findAll(search,pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomsException {
        return new ResponseEntity<>(roomService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid Authentication authentication, @RequestBody RoomRequest roomRequest) throws CustomsException {
        return new ResponseEntity<>(roomService.save(authentication,roomRequest),HttpStatus.CREATED);
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<?> update(@Valid Authentication authentication,@PathVariable Long id, @RequestBody RoomUpdateRequest roomUpdateRequest) throws CustomsException {
        return new ResponseEntity<>(roomService.update(authentication,id,roomUpdateRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> isDelete(Authentication authentication,@PathVariable Long id) throws CustomsException {
        roomService.isDelete(authentication,id);
        String success = "Room Deleted" ;
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

}
