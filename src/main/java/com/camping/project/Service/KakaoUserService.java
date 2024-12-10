package com.camping.project.Service;

import com.camping.project.DTO.KakaoUserInfoResponseDto;
import com.camping.project.DTO.UserDTO;

public interface KakaoUserService {
    UserDTO findOrRegisterUser(KakaoUserInfoResponseDto userInfo);
}
