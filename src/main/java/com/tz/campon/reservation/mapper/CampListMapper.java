package com.tz.campon.reservation.mapper;

import com.tz.campon.reservation.DTO.CampList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CampListMapper {

    @Select("SELECT * FROM campground")
    ArrayList<CampList> getCampList();

    @Select("SELECT * FROM campground WHERE camp_id = #{camp_id}")
    CampList getCampListByCampId(Integer camp_id);

    @Select("SELECT * FROM campground ORDER BY price ASC")
    ArrayList<CampList> getCampListByPrice();

    @Select("SELECT * FROM campground ORDER BY rating DESC")
    ArrayList<CampList> getCampListByRating();

    @Select("SELECT * FROM campground WHERE region = #{region}")
    ArrayList<CampList> getCampListByRegion(@Param("region") String region);

    @Select("SELECT * FROM campground WHERE region = #{region} ORDER BY price ASC")
    ArrayList<CampList> getCampListByRegionSortedByPrice(@Param("region") String region);

    @Select("SELECT * FROM campground WHERE region = #{region} ORDER BY rating DESC")
    ArrayList<CampList> getCampListByRegionSortedByRating(@Param("region") String region);

}
