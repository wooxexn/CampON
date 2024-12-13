package com.tz.campon.reservation.Repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CampDetailRepository {

    @Autowired
    SqlSession session;

    public List<CampDetail> getCampDetail(int camp_id){

        return session.selectList("c.getCampDetailById", camp_id);

    }

}
