package com.ra.controller;

import com.ra.dto.request.user.*;
import com.ra.exception.CustomsException;
import com.ra.service.user.IUserService;
import jakarta.servlet.http.HttpSession;
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
@RequestMapping("/api/booking/v1/auth/")
@AllArgsConstructor
public class UserController {
    private IUserService userService ;

    @GetMapping("/getAllCustomer")
    public ResponseEntity<?> getAllCustomer(@RequestParam(defaultValue = "") String search,
                                            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {
        return new ResponseEntity<>(userService.findAllCustomer(search, pageable), HttpStatus.OK);
    }

    @GetMapping("/getAllManagement")
    public ResponseEntity<?> getAllManagement(@RequestParam(defaultValue = "") String search,
                                            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {
        return new ResponseEntity<>(userService.findAllManagement(search, pageable), HttpStatus.OK);
    }

    @GetMapping("/getAllStaff")
    public ResponseEntity<?> getAllStaff(@RequestParam(defaultValue = "") String search,
                                              @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {
        return new ResponseEntity<>(userService.findALlStaff(search, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}/customer")
    public ResponseEntity<?> getCustomerById (@PathVariable Long id ) throws CustomsException {
        return new ResponseEntity<>(userService.findCustomerById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/management")
    public ResponseEntity<?> getManagementById (@PathVariable Long id ) throws CustomsException {
        return new ResponseEntity<>(userService.findManagementById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/staff")
    public ResponseEntity<?> getStaffById (@PathVariable Long id ) throws CustomsException {
        return new ResponseEntity<>(userService.findStaffById(id), HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerRegister customerRegister) throws CustomsException {
        userService.registerCustomer(customerRegister) ;
        String success ="Registered successfully" ;
        return new ResponseEntity<>(success, HttpStatus.CREATED);
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<?> registerAd(@Valid @RequestBody AdminRegister adminRegister) throws CustomsException {
        userService.registerAdmin(adminRegister); ;
        String success ="Registered successfully" ;
        return new ResponseEntity<>(success, HttpStatus.CREATED);
    }

    @PostMapping("/registerManagement")
    public ResponseEntity<?> registerManagement(@Valid @RequestBody ManagementRegister managementRegister) throws CustomsException {
        userService.registerManagement(managementRegister); ;
        String success ="Registered successfully" ;
        return new ResponseEntity<>(success, HttpStatus.CREATED);
    }

    @PostMapping("/registerStaff")
    public ResponseEntity<?> registerStaff(@Valid @RequestBody StaffRegister staffRegister) throws CustomsException {
        userService.registerStaff(staffRegister);
        String success ="Registered successfully" ;
        return new ResponseEntity<>(success, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid HttpSession session,  @RequestBody UserLogin userLogin) throws CustomsException {
        return new ResponseEntity<>(userService.login(userLogin, session), HttpStatus.OK);
    }
}
