package com.tz.campon.mapper;

import com.tz.campon.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    User findUserById(String id);

    // 사용자 정보 업데이트 (아이디는 변경되지 않음)
    void updateUser(User user);

    // 비밀번호 변경을 위한 메서드 추가
    @Update("UPDATE User SET password = #{password} WHERE id = #{userId}")
    void updatePassword(@Param("userId") String userId, @Param("password") String password);
}
