package com.tz.campon.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "아이디는 필수입니다.")
    private String id;  // 아이디는 읽기 전용, 수정 불가

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "전화번호는 필수입니다.")
    private String phone;

    // 비밀번호는 변경할 수 있도록 설정
    private String password;
}
