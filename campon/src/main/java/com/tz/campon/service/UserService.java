package com.tz.campon.service;

import com.tz.campon.dto.UserDto;
import com.tz.campon.mapper.UserMapper;
import com.tz.campon.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 사용자 정보를 가져오는 메서드
    public UserDto getUserInfo(String userId) {
        User user = userMapper.findUserById(userId);
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPhone(), "");
    }

    // 사용자 정보를 업데이트하는 메서드 (아이디는 제외)
    public void updateUserInfo(String userId, UserDto userDto) {
        User user = new User();
        user.setId(userId);  // ID는 서비스 계층에서 직접 설정
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPhone(userDto.getPhone());
        // 비밀번호는 설정되지 않으면 그대로 두고, 변경되었으면 처리
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));  // 비밀번호 암호화 후 설정
        }

        userMapper.updateUser(user);  // 업데이트
    }

    // 비밀번호 변경 메서드
    public void updateUserPassword(String userId, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        userMapper.updatePassword(userId, encodedPassword);
    }
}
