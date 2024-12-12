package com.tz.campon.login.Mapper;

import com.tz.campon.login.DTO.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE kakao_id = #{kakao_id}")
    UserDTO findByKakaoId(String kakao_id);

    @Insert("INSERT INTO user (id, email, password, name, phone, kakao_id) VALUES (#{id}, #{email}, #{password}, #{name}, #{phone}, #{kakao_id})")
    void insertUser(UserDTO userDTO);

    @Select("SELECT * FROM user WHERE id = #{id}")
    UserDTO findByUsername(@Param("id") String id);
}
