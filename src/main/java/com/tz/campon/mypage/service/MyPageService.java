package com.tz.campon.mypage.service;

import com.tz.campon.login.dto.UserDTO;
import com.tz.campon.mypage.dto.ReservationDTO;
import com.tz.campon.mypage.exception.ReservationNotFoundException;
import com.tz.campon.mypage.exception.UserNotFoundException;
import com.tz.campon.mypage.mapper.MyPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageService {

    @Autowired
    private MyPageMapper myPageMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO getUserInfo(String userId) {
        UserDTO user = myPageMapper.findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return user;
    }

    public void updateUserInfo(UserDTO userDTO) {
        UserDTO existingUser = myPageMapper.findUserById(userDTO.getId());
        if (existingUser == null) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }

        System.out.println("Existing User Info: " + existingUser);

        if (userDTO.getName() != null) {
            existingUser.setName(userDTO.getName());
        }
        if (userDTO.getEmail() != null) {
            existingUser.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPhone() != null) {
            existingUser.setPhone(userDTO.getPhone());
        }
        if (userDTO.getPassword() != null) {
            existingUser.setPassword(userDTO.getPassword());
        }

        System.out.println("Updated User Info: " + existingUser);

        int affectedRows = myPageMapper.updateUser(existingUser);
        if (affectedRows == 0) {
            throw new UserNotFoundException("사용자 정보 수정에 실패했습니다.");
        }
    }

    public List<ReservationDTO> getUserReservations(String userId) {
        return myPageMapper.findReservationsByUserId(userId);
    }

    public void cancelReservation(int reservationId) {
        int affectedRows = myPageMapper.deleteReservation(reservationId);
        if (affectedRows == 0) {
            throw new ReservationNotFoundException("예약 ID를 찾을 수 없습니다.");
        }
    }

    public boolean validatePassword(String userId, String password) {
        UserDTO user = myPageMapper.findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }

        System.out.println("Encoded Password from DB: " + user.getPassword());
        System.out.println("Input Password: " + password);
        return passwordEncoder.matches(password, user.getPassword());
    }

}
