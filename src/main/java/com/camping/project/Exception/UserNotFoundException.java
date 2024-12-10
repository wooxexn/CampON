package com.camping.project.Exception;

import com.camping.project.DTO.KakaoUserInfoResponseDto;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final KakaoUserInfoResponseDto kakaoUserInfo;

    public UserNotFoundException(String s, KakaoUserInfoResponseDto kakaoUserInfo) {
        super(s);
        this.kakaoUserInfo = kakaoUserInfo;
    }
}
