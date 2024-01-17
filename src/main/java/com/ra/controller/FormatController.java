package com.ra.controller;

import com.ra.dto.request.format.FormatRequest;
import com.ra.exception.CustomsException;
import com.ra.service.format.IFormatService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking/v1/format")
@AllArgsConstructor
public class FormatController {
    private IFormatService formatService;
    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "") String search,
                                    @PageableDefault(size = 10, page = 0,sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {
        return new ResponseEntity<>(formatService.findAll(search,pageable), HttpStatus.OK);
    }
   @GetMapping("/{id}")
   public ResponseEntity<?> getById(@PathVariable Long id ) throws CustomsException {
        return new ResponseEntity<>(formatService.findById(id), HttpStatus.OK);
   }

   @PostMapping()
    public ResponseEntity<?> create(Authentication authentication, @RequestBody FormatRequest formatRequest) throws CustomsException {
        return new ResponseEntity<>(formatService.save(authentication,formatRequest), HttpStatus.CREATED);
   }

   @PutMapping("/{id}")
    public ResponseEntity<?> update(Authentication authentication, @PathVariable Long id, @RequestBody FormatRequest formatRequest) throws CustomsException {
        return new ResponseEntity<>(formatService.update(authentication, id, formatRequest), HttpStatus.OK);
   }

   @PatchMapping("/{id}")
    public ResponseEntity<?> isDelete(Authentication authentication, @PathVariable Long id ) throws CustomsException {
       formatService.isDelete(authentication,id);
       String success = "Format Deleted" ;
        return new ResponseEntity<>(success, HttpStatus.OK);
   }
}
