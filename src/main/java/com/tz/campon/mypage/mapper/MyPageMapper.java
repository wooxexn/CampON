package com.tz.campon.mypage.mapper;

import com.tz.campon.login.dto.UserDTO;
import com.tz.campon.mypage.dto.ReservationDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MyPageMapper {

    @Select("SELECT * FROM user WHERE id = #{userId}")
    UserDTO findUserById(String userId);

    @Update("UPDATE user SET email = #{email}, name = #{name}, phone = #{phone} WHERE id = #{id}")
    void updateUser(UserDTO userDTO);

    @Select("SELECT * FROM reservations WHERE user_id = #{userId}")
    List<ReservationDTO> findReservationsByUserId(String userId);

    @Delete("DELETE FROM reservations WHERE id = #{reservationId}")
    int deleteReservation(int reservationId); // 반환 타입을 int로 변경
}
