package com.tz.campon.reservation.Repository;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationRepository {

    @Autowired
    SqlSession session;

    public List<Reservation> getReservationDate(LocalDate check_id_date, LocalDate check_out_date, int camp_id){

        System.out.println(check_id_date);

        System.out.println(check_out_date);
        System.out.println(camp_id);


        Map<String, Object> params = new HashMap<>();
        params.put("check_in_date", check_id_date);
        params.put("check_out_date", check_out_date);
        params.put("camp_id", camp_id);



        return session.selectList("a.getReservationDate", params);

    }

    public List<Reservation> getReservationById(String id){

        return session.selectList("a.getReservationById", id);
    }

    public int register(Reservation reservation ){
       int row =   session.insert("a.addReserve", reservation );

       return  row;
    }
}
