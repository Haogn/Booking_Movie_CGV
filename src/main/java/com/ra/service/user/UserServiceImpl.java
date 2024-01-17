package com.ra.service.user;

import com.ra.constant.MemberLeverName;
import com.ra.constant.RoleName;
import com.ra.dto.request.user.*;
import com.ra.dto.response.user.CustomerResponse;
import com.ra.dto.response.user.JwtResponse;
import com.ra.dto.response.user.ManagementResponse;
import com.ra.dto.response.user.StaffResponse;
import com.ra.entity.Roles;
import com.ra.entity.Theater;
import com.ra.entity.Users;
import com.ra.exception.CustomsException;
import com.ra.mapper.UserMapper;
import com.ra.repository.IRoleRepository;
import com.ra.repository.ITheaterRepository;
import com.ra.repository.IUsersRepository;
import com.ra.security.jwt.JwtProvider;
import com.ra.security.user_principel.UserPrincipal;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
    private IUsersRepository usersRepository;
    private IRoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;
    private AuthenticationProvider authenticationProvider;
    private ITheaterRepository theaterRepository;
    private UserMapper userMapper;


    @Override
    public void registerCustomer(CustomerRegister userRegister) throws CustomsException {
        if (usersRepository.existsByEmail(userRegister.getEmail())) {
            throw new CustomsException("Exist Email");
        }

        if (usersRepository.existsByUserName(userRegister.getUserName())) {
            throw new CustomsException("Exist UserName");
        }

        if (usersRepository.existsByPhone(userRegister.getPhone())) {
            throw new CustomsException("Exist Phone");
        }
        Set<Roles> roles = new HashSet<>();

        if (userRegister.getRoleName().equals("customer")) {
            roles.add(roleRepository.findRolesByRoleName(RoleName.ROLE_CUSTOMER));
        } else {
            throw new CustomsException("RoleName Not Found");
        }
        MemberLeverName memberLevers = null;
        // neu khong co hang dc them vao , mac dinh la hang bac
        if (userRegister.getMemberLever() == null || userRegister.getMemberLever().isEmpty()) {
            memberLevers = MemberLeverName.BRONZE;
        }
        usersRepository.save(Users.builder()
                .userName(userRegister.getUserName())
                .email(userRegister.getEmail())
                .phone(userRegister.getPhone())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .birthday(userRegister.getBirthDay())
                .roles(roles)
                .memberLeverName(memberLevers)
                .status(true)
                .scorePoints(0)
                .createTime(LocalDateTime.now())
                .createUser(userRegister.getUserName())
                .build());
    }

    @Override
    public void registerAdmin(AdminRegister adminRegister) throws CustomsException {
        if (usersRepository.existsByEmail(adminRegister.getEmail())) {
            throw new CustomsException("Exist Email");
        }

        if (usersRepository.existsByUserName(adminRegister.getUserName())) {
            throw new CustomsException("Exist UserName");
        }

        if (usersRepository.existsByPhone(adminRegister.getPhone())) {
            throw new CustomsException("Exist Phone");
        }
        Set<Roles> roles = new HashSet<>();

        if (adminRegister.getRoleName().equals("admin")) {
            roles.add(roleRepository.findRolesByRoleName(RoleName.ROLE_ADMIN));
            roles.add(roleRepository.findRolesByRoleName(RoleName.ROLE_MANAGEMENT));
            roles.add(roleRepository.findRolesByRoleName(RoleName.ROLE_STAFF));
        } else {
            throw new CustomsException("RoleName Not Found");
        }
        usersRepository.save(Users.builder()
                .userName(adminRegister.getUserName())
                .email(adminRegister.getEmail())
                .phone(adminRegister.getPhone())
                .password(passwordEncoder.encode(adminRegister.getPassword()))
                .roles(roles)
                .status(true)
                .createTime(LocalDateTime.now())
                .createUser(adminRegister.getUserName())
                .build());
    }

    @Override
    public void registerManagement(ManagementRegister managementRegister) throws CustomsException {
        if (usersRepository.existsByEmail(managementRegister.getEmail())) {
            throw new CustomsException("Exist Email");
        }

        if (usersRepository.existsByUserName(managementRegister.getUserName())) {
            throw new CustomsException("Exist UserName");
        }

        if (usersRepository.existsByPhone(managementRegister.getPhone())) {
            throw new CustomsException("Exist Phone");
        }
        Set<Roles> roles = new HashSet<>();

        if (managementRegister.getRoleName().equals("management")) {
            roles.add(roleRepository.findRolesByRoleName(RoleName.ROLE_MANAGEMENT));
            roles.add(roleRepository.findRolesByRoleName(RoleName.ROLE_STAFF));
        } else {
            throw new CustomsException("RoleName Not Found");
        }
        Theater theater = theaterRepository.findById(managementRegister.getTheaterId()).orElseThrow(() -> new CustomsException("Theater NOt Found"));
        usersRepository.save(Users.builder()
                .userName(managementRegister.getUserName())
                .email(managementRegister.getEmail())
                .phone(managementRegister.getPhone())
                .password(passwordEncoder.encode(managementRegister.getPassword()))
                .roles(roles)
                .status(true)
                .localTheater(theater.getId())
                .createTime(LocalDateTime.now())
                .createUser(managementRegister.getUserName())
                .build());
    }

    @Override
    public void registerStaff(StaffRegister staffRegister) throws CustomsException {
        if (usersRepository.existsByEmail(staffRegister.getEmail())) {
            throw new CustomsException("Exist Email");
        }

        if (usersRepository.existsByUserName(staffRegister.getUserName())) {
            throw new CustomsException("Exist UserName");
        }

        if (usersRepository.existsByPhone(staffRegister.getPhone())) {
            throw new CustomsException("Exist Phone");
        }
        Set<Roles> roles = new HashSet<>();

        if (staffRegister.getRoleName().equals("staff")) {
            roles.add(roleRepository.findRolesByRoleName(RoleName.ROLE_STAFF));
            roles.add(roleRepository.findRolesByRoleName(RoleName.ROLE_CUSTOMER));
        } else {
            throw new CustomsException("RoleName Not Found");
        }
        usersRepository.save(Users.builder()
                .userName(staffRegister.getUserName())
                .email(staffRegister.getEmail())
                .phone(staffRegister.getPhone())
                .password(passwordEncoder.encode(staffRegister.getPassword()))
                .roles(roles)
                .status(true)
                .createTime(LocalDateTime.now())
                .createUser(staffRegister.getUserName())
                .build());
    }

    @Override
    public JwtResponse login(UserLogin userLogin, HttpSession session) throws CustomsException {
        Authentication authentication;

        try {
            authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUserName(), userLogin.getPassword()));
        } catch (AuthenticationException e) {
            Integer errorLogin = (Integer) session.getAttribute("errorLogin");
            if (errorLogin == null) {
                session.setAttribute("errorLogin", 1);
            } else {
                // Không phải lần đầu tiên nhập sai
                if (errorLogin == 3) {
                    // Khi số lần nhập sai đạt mức 3, khoá tài khoản
                    Users users = usersRepository.findByUserName(userLogin.getUserName()).orElseThrow(() -> new CustomsException("user not found"));
                    users.setStatus(false);
                    usersRepository.save(users);
                    throw new CustomsException("your account is blocked");
                } else {
                    // Tăng số lần nhập sai
                    session.setAttribute("count", errorLogin + 1);
                }
            }
            throw new CustomsException("Username or Password is incorrect");
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();


        if (!userPrincipal.getStatus()) {
            throw new CustomsException("your account is blocked");
        }
        return JwtResponse.builder()
                .userName(userPrincipal.getUsername())
                .token(jwtProvider.generateToken(userPrincipal))
                .roles(userPrincipal.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Page<CustomerResponse> findAllCustomer(String search, Pageable pageable) {
        Page<Users> page;
        if (search.isEmpty()) {
            page = usersRepository.findAllByRoles(RoleName.ROLE_CUSTOMER, pageable);
        } else {
            page = usersRepository.findAllByRolesAndUserName(RoleName.ROLE_CUSTOMER, search, pageable);
        }
        return page.map(item -> userMapper.toCusResponse(item));
    }

    @Override
    public Page<ManagementResponse> findAllManagement(String search, Pageable pageable) {
        Page<Users> page;
        if (search.isEmpty()) {
            page = usersRepository.findAllByRoles(RoleName.ROLE_MANAGEMENT, pageable);
        } else {
            page = usersRepository.findAllByRolesAndUserName(RoleName.ROLE_MANAGEMENT, search, pageable);
        }
        return page.map(item -> userMapper.toManaResponse(item));
    }

    @Override
    public Page<StaffResponse> findALlStaff(String search, Pageable pageable) {
        Page<Users> page;
        if (search.isEmpty()) {
            page = usersRepository.findAllByRoles(RoleName.ROLE_STAFF, pageable);
        } else {
            page = usersRepository.findAllByRolesAndUserName(RoleName.ROLE_STAFF, search, pageable);
        }
        return page.map(item -> userMapper.toStaffResponse(item));
    }

    @Override
    public CustomerResponse findCustomerById(Long id) throws CustomsException {
        Users customer = usersRepository.findById(id).orElseThrow(() -> new CustomsException("Customer Not Found"));
        return userMapper.toCusResponse(customer);
    }

    @Override
    public ManagementResponse findManagementById(Long id) throws CustomsException {
        Users management = usersRepository.findById(id).orElseThrow(() -> new CustomsException("Management Not Found"));
        return userMapper.toManaResponse(management);
    }

    @Override
    public StaffResponse findStaffById(Long id) throws CustomsException {
        Users staff = usersRepository.findById(id).orElseThrow(() -> new CustomsException("Staff Not Found"));
        return userMapper.toStaffResponse(staff);
    }


    @Override
    public CustomerResponse updateCustomer(Authentication authentication, Long id, CustomerResponse customerResponse) throws CustomsException {
        Users customer = usersRepository.findById(id).orElseThrow(() -> new CustomsException("Customer Not Found"));
        customer.setId(id);
        return null;
    }

    @Override
    public ManagementResponse updateManagement(Authentication authentication, Long id, ManagementResponse managementResponse) throws CustomsException {
        Users management = usersRepository.findById(id).orElseThrow(() -> new CustomsException("Management Not Found"));
        return null;
    }

    @Override
    public StaffResponse updateStaff(Authentication authentication, Long id, StaffResponse staffResponse) throws CustomsException {
        Users staff = usersRepository.findById(id).orElseThrow(() -> new CustomsException("Staff Not Found"));
        return null;
    }

    @Override
    public void changeStatusManagement(Authentication authentication, Long id) {

    }

    @Override
    public void changeStatusStaff(Authentication authentication, Long id) {

    }

    @Override
    public void changeStatusCustomer(Authentication authentication, Long id) {

    }
}
