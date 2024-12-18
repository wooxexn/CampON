package com.tz.campon.reservation.Repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CampDetailRepository {

    @Autowired
    SqlSession session;

    public List<CampDetail> getCampDetail(int camp_id){

        return session.selectList("c.getCampDetailById", camp_id);

    }

    public CampDetail selectCampDetailOne(int camp_id, int campdetail_id){

        Map<String, Object> params = new HashMap<>();

        params.put("camp_id", camp_id);
        params.put("campdetail_id", campdetail_id);


        return session.selectOne("c.selectCampDetailOne", params );

    }



}
