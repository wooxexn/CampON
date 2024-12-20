package com.tz.campon.reservation.Repository;


import com.tz.campon.reservation.DTO.CampList;
import com.tz.campon.reservation.mapper.CampListMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CampListRepository {

    private final CampListMapper campListMapper;

    public CampListRepository(CampListMapper campListMapper) {
        this.campListMapper = campListMapper;
    }

    public ArrayList<CampList> getAllCamp(){

        return campListMapper.getCampList();
    }

    public CampList getCampgroundById(int camp_id){

        return campListMapper.getCampListByCampId(camp_id);

    }

    public ArrayList<CampList> getCampsByPrice() {
        return campListMapper.getCampListByPrice();
    }

    public ArrayList<CampList> getCampsByRating() {
        return campListMapper.getCampListByRating();
    }

    public ArrayList<CampList> getCampListByRegion(String region) {
        return campListMapper.getCampListByRegion(region);
    }

    public ArrayList<CampList> getCampListByRegionSorted(String region, String sort) {
        if ("price".equals(sort)) {
            return campListMapper.getCampListByRegionSortedByPrice(region);
        } else if ("rating".equals(sort)) {
            return campListMapper.getCampListByRegionSortedByRating(region);
        }
        return getCampListByRegion(region);
    }

}
