package com.tz.campon.reservation.Repository;


import com.tz.campon.reservation.DTO.CampList;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CampListRepository {

    @Autowired
    SqlSession session;

    public List<CampList> getAllCamp(){

        return  session.selectList("b.getCampground");
    }

    public CampList getCampgroundById(int camp_id){

        CampList campList = session.selectOne("b.getCampgroundById", camp_id);

        return campList;

    }

}
