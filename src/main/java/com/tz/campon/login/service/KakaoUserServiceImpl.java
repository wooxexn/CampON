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
            throw new UserNotFoundException("카카오 회원이 존재하지 않습니다. 회원가입 후 이용해주세요.",userInfo);
        }
        return user;
    }

}
