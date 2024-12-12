package com.tz.campon.login.Exception;

import com.tz.campon.login.DTO.KakaoUserInfoResponseDto;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final KakaoUserInfoResponseDto kakaoUserInfo;

    public UserNotFoundException(String s, KakaoUserInfoResponseDto kakaoUserInfo) {
        super(s);
        this.kakaoUserInfo = kakaoUserInfo;
    }
}
