package com.camping.project.Service;

import com.camping.project.DTO.KakaoUserInfoResponseDto;
import com.camping.project.DTO.UserDTO;
import com.camping.project.Exception.UserNotFoundException;
import com.camping.project.Mapper.UserMapper;
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
