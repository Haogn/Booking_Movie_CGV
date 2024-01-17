package com.ra.dto.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerRegister {
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email không đúng định dạng")
    @NotBlank(message = "Email không được để rỗng")
    private String email ;
    @Length(min =2 , max = 50, message = "Tên tài khoản ít nhất 2 ký tự , nhiều nhất 50 ký tự")
    @NotBlank(message = "tên tài khoản không được để trống")
    private String userName ;
    @Pattern(regexp = "^(\\\\+84|0)\\\\d{9,10}$", message = "Số điện thoại không đúng định dạng")
    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone ;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", message = "Password từ 6 đến 16 ký tự, chứa ít nhất một ký tự chữ cái (viết hoa hoặc viết thường) , ít nhất một chữ số.")
    @Length(min = 6, max = 16, message = "password ít nhất 6 ký tự, nhiều nhất 16 ký tự")
    @NotBlank(message = "passwourd không được để trống")
    private String password ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "Ngày sinh không được được để trống")
    private LocalDate birthDay;
    private String roleName = "customer";
    private String memberLever;
    private Integer scorePoints;
    private Boolean status ;
    private String avatar ;
}
