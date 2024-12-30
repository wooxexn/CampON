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

    public int deleteReview(int review_id){

        int row = session.delete("d.deleteReviewById", review_id);

        return row;
    }

    public List<Review> getReviewOnly3(int camp_id){

        return session.selectList("d.getReviewOnly3", camp_id);

    }

    public List<Review> getReviewPage(int camp_id, int currentPage){


        int pageSize = 5;
        int offset = (currentPage - 1) * pageSize;

        Map<String, Object> params = new HashMap<>();
        params.put("camp_id", camp_id);
        params.put("offset", offset);
        params.put("pageSize", pageSize);

        return session.selectList("d.getReview", params);

    }

    public int countAll(int camp_id){
        return session.selectOne("d.selectCount", camp_id);
    }


    public int regReview(Review review){


        int row = session.insert("d.regReview", review);

        return row;
    }

    public int calculateAverageRating(int camp_id){
        return session.selectOne("d.calculateAverageRating", camp_id);
    }

}

