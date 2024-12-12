package com.tz.campon.login.service;

import com.tz.campon.login.dto.KakaoUserInfoResponseDto;
import com.tz.campon.login.dto.UserDTO;

public interface KakaoUserService {
    UserDTO findOrRegisterUser(KakaoUserInfoResponseDto userInfo);
}
