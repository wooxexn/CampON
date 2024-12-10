package com.tz.campon.service;

import com.tz.campon.dto.ReservationDto;
import com.tz.campon.mapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    public List<ReservationDto> getUserReservations(String userId) {
        return reservationMapper.findReservationsByUserId(userId);
    }

    public void cancelReservation(int reservationId) {
        reservationMapper.deleteReservation(reservationId);
    }
}
