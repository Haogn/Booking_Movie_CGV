package com.ra.repository;

import com.ra.constant.RoleName;
import com.ra.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsersRepository extends JpaRepository<Users , Long> {

    Optional<Users> findByUserName(String userName) ;
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone) ;
    Users findByPhone(String phone) ;

    @Query("SELECT DISTINCT u FROM Users u JOIN u.roles r WHERE r.roleName = :roleName AND u.userName LIKE %:userName%")
    Page<Users> findAllByRolesAndUserName(@Param("roleName") RoleName roleName, @Param("userName") String userName, Pageable pageable);

    @Query("SELECT DISTINCT u FROM Users u JOIN u.roles r WHERE r.roleName = :roleName ")
    Page<Users> findAllByRoles(@Param("roleName") RoleName roleName , Pageable pageable) ;
}
