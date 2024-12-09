package com.camping.project.Mapper;

import com.camping.project.DTO.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (id, email, password, name, phone, kakao_id) VALUES (#{id}, #{email}, #{password}, #{name}, #{name}, #{kakao_id})")
    void insertUser(UserDTO userDTO);

    @Select("SELECT * FROM user WHERE id = #{id}")
    UserDTO findByUsername(@Param("id") String id);
}
