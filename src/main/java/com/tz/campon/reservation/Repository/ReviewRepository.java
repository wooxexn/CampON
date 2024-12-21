package com.tz.campon.reservation.Repository;

import com.tz.campon.reservation.DTO.Review;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReviewRepository {

    @Autowired
    SqlSession session;

    public List<Review> getReviewAll(int camp_id){

        return session.selectList("d.getReview", camp_id);

    }

    public List<Review> getReviewOnly3(int camp_id){

        return session.selectList("d.getReviewOnly3", camp_id);

    }

    public int regReview(Review review){


        int row = session.insert("d.regReview", review);

        return row;
    }

}
