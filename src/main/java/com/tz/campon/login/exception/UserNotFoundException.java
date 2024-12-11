package com.tz.campon.login.exception;

import com.tz.campon.login.dto.KakaoUserInfoResponseDto;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final KakaoUserInfoResponseDto kakaoUserInfo;

    public UserNotFoundException(String s, KakaoUserInfoResponseDto kakaoUserInfo) {
        super(s);
        this.kakaoUserInfo = kakaoUserInfo;
    }
}
