package com.tz.campon.mapper;

import com.tz.campon.dto.ReservationDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationMapper {
    List<ReservationDto> findReservationsByUserId(String userId);
    void deleteReservation(int id);
}
