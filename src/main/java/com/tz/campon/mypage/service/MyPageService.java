package com.tz.campon.mypage.service;

import com.tz.campon.login.dto.UserDTO;
import com.tz.campon.mypage.dto.ReservationDTO;
import com.tz.campon.mypage.exception.ReservationNotFoundException;
import com.tz.campon.mypage.exception.UserNotFoundException;
import com.tz.campon.mypage.mapper.MyPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageService {

    @Autowired
    private MyPageMapper myPageMapper;

    public UserDTO getUserInfo(String userId) {
        UserDTO user = myPageMapper.findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다: " + userId);
        }
        return user;
    }

    public void updateUserInfo(String userId, UserDTO userDTO) {
        userDTO.setId(userId); // ID는 변경되지 않도록 유지
        myPageMapper.updateUser(userDTO);
    }

    public List<ReservationDTO> getUserReservations(String userId) {
        List<ReservationDTO> reservations = myPageMapper.findReservationsByUserId(userId);
        if (reservations == null || reservations.isEmpty()) {
            throw new UserNotFoundException("예약 내역이 없습니다.");
        }
        return reservations;
    }

    public void cancelReservation(int reservationId) {
        int affectedRows = myPageMapper.deleteReservation(reservationId); // 반환된 행의 개수를 확인
        if (affectedRows == 0) {
            throw new ReservationNotFoundException("예약 ID를 찾을 수 없습니다: " + reservationId);
        }
    }
}

