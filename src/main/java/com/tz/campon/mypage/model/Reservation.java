package com.tz.campon.mypage.model;

import lombok.Data;

@Data
public class Reservation {
    private int id;
    private String campgroundName;
    private String address;
    private String reservationDate;
    private double price;
    private String photoUrl;
    private String userId;  // 예약한 사용자 ID
}
