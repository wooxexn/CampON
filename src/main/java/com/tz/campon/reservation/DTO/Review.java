package com.tz.campon.reservation.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class Review {


    int review_id;
    String user_id;
    int camp_id;
    int rating;
    String reply;
    Date created_at;


}
