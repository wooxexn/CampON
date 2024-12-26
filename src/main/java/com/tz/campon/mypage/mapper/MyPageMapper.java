package com.tz.campon.mypage.mapper;

import com.tz.campon.login.dto.UserDTO;
import com.tz.campon.mypage.dto.ReservationDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MyPageMapper {

    // 사용자 정보 조회 (비밀번호 포함)
    @Select("SELECT id, email, name, phone, password, kakao_id FROM user WHERE id = #{userId}")
    UserDTO findUserById(String userId);

    // 사용자 정보 업데이트 (동적 업데이트)
    @Update({
            "<script>",
            "UPDATE user",
            "<set>",
            "<if test='email != null'>email = #{email},</if>",
            "<if test='name != null'>name = #{name},</if>",
            "<if test='phone != null'>phone = #{phone},</if>",
            "<if test='password != null'>password = #{password},</if>",
            "</set>",
            "WHERE id = #{id}",
            "</script>"
    })
    int updateUser(UserDTO userDTO);

    // 예약 내역 조회
    @Select("SELECT * FROM reservations WHERE user_id = #{userId}")
    List<ReservationDTO> findReservationsByUserId(String userId);

    // 예약 삭제
    @Delete("DELETE FROM reservations WHERE reservation_id = #{reservationId}")
    int deleteReservation(@Param("reservationId") int reservationId);
}

