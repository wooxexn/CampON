package com.tz.campon.mypage.model;

import lombok.Data;

@Data
public class User {
    private String id;
    private String email;
    private String name;
    private String password; // 비밀번호는 암호화되어 저장될 것
    private String phone;
}
