package com.ra.service.user;

import com.ra.dto.request.user.*;
import com.ra.dto.response.user.CustomerResponse;
import com.ra.dto.response.user.JwtResponse;
import com.ra.dto.response.user.ManagementResponse;
import com.ra.dto.response.user.StaffResponse;
import com.ra.exception.CustomsException;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface IUserService {
    void registerCustomer(CustomerRegister userRegister) throws CustomsException;
    void registerAdmin(AdminRegister adminRegister) throws CustomsException;
    void registerManagement(ManagementRegister managementRegister) throws CustomsException;
    void registerStaff(StaffRegister staffRegister) throws CustomsException;

    JwtResponse login(UserLogin userLogin , HttpSession session) throws CustomsException;
    Page<CustomerResponse> findAllCustomer(String search, Pageable pageable) ;
    Page<ManagementResponse> findAllManagement(String search,Pageable pageable) ;
    Page<StaffResponse> findALlStaff(String search,Pageable pageable) ;
    CustomerResponse findCustomerById (Long id ) throws CustomsException;
    ManagementResponse findManagementById (Long id) throws CustomsException;
    StaffResponse findStaffById(Long id) throws CustomsException;


    CustomerResponse updateCustomer(Authentication authentication, Long id , CustomerResponse customerResponse) throws CustomsException;
    ManagementResponse updateManagement(Authentication authentication,Long id , ManagementResponse managementResponse) throws CustomsException;
    StaffResponse updateStaff(Authentication authentication,Long id , StaffResponse staffResponse) throws CustomsException;

    void changeStatusManagement(Authentication authentication,Long id ) ;
    void changeStatusStaff(Authentication authentication,Long id) ;
    void changeStatusCustomer(Authentication authentication,Long id) ;
}
