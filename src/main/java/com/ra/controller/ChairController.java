package com.ra.controller;

import com.ra.dto.request.chair.ChairRequest;
import com.ra.dto.request.chair.ChairTypeRequest;
import com.ra.exception.CustomsException;
import com.ra.service.chair.IChairService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking/v1/chair")
@AllArgsConstructor
public class ChairController {
    private IChairService chairService ;

    @GetMapping()
    public ResponseEntity<?> getAll () {
        return new ResponseEntity<>(chairService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomsException {
        return new ResponseEntity<>(chairService.findById(id), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid Authentication authentication, @PathVariable Long id, @RequestBody ChairRequest chairRequest) throws CustomsException {
        return new ResponseEntity<>(chairService.update(authentication,id, chairRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> changeChairType(@Valid Authentication authentication, @PathVariable Long id, @RequestBody ChairTypeRequest chairTypeRequest) throws CustomsException {
        return new ResponseEntity<>(chairService.changeChairType(authentication,id,chairTypeRequest), HttpStatus.OK);
    }


    @PatchMapping("/delete/{id}")
    public ResponseEntity<?> isDelete(@Valid Authentication authentication,@PathVariable Long id) throws CustomsException {
        chairService.isDelete(authentication,id);
        String success = "Chair Deleted" ;
        return new ResponseEntity<>(success,HttpStatus.OK);
    }

//    @DeleteMapping()
//    public ResponseEntity<?> delete(){
//       chairService.delete();
//        String success = "Chair Deleted" ;
//        return new ResponseEntity<>(success,HttpStatus.OK);
//    }
}
