package com.tz.campon.login.Service;

import com.tz.campon.login.DTO.KakaoUserInfoResponseDto;
import com.tz.campon.login.DTO.UserDTO;
import com.tz.campon.login.Exception.UserNotFoundException;
import com.tz.campon.login.Mapper.UserMapper;
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
