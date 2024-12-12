package com.tz.campon.login.service;

import com.tz.campon.login.dto.KakaoUserInfoResponseDto;
import com.tz.campon.login.dto.UserDTO;
import com.tz.campon.login.exception.UserNotFoundException;
import com.tz.campon.login.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class KakaoUserServiceImpl implements KakaoUserService {

    private final UserMapper userMapper;

    public KakaoUserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO findOrRegisterUser(KakaoUserInfoResponseDto userInfo) {
        UserDTO user = userMapper.findByKakaoId(userInfo.getId().toString());
        if (user == null) {
            throw new UserNotFoundException("회원 정보가 없습니다.",userInfo);
        }
        return user;
    }

}
