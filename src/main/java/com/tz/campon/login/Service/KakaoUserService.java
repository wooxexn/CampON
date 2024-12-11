package com.tz.campon.login.Service;

import com.tz.campon.login.DTO.KakaoUserInfoResponseDto;
import com.tz.campon.login.DTO.UserDTO;

public interface KakaoUserService {
    UserDTO findOrRegisterUser(KakaoUserInfoResponseDto userInfo);
}
