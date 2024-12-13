package com.tz.campon.login.service;

import com.tz.campon.login.dto.UserDTO;
import com.tz.campon.login.mapper.UserMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(UserDTO userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
        if (isIdDuplicated(userDTO.getId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        userMapper.insertUser(userDTO);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserDTO userDTO = userMapper.findByUsername(id);
        if (userDTO == null) {
            System.out.println("User not found: " + id);
            throw new UsernameNotFoundException(id);
        }
        return new User(userDTO.getId(),userDTO.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }


    public boolean isIdDuplicated(String id) {
        return userMapper.existsById(id);
    }

}
