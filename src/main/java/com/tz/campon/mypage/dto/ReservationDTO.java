package com.tz.campon.mypage.dto;

import lombok.Data;

@Data
public class ReservationDTO {
    private int id;
    private String campgroundName;
    private String address;
    private String reservationDate;
    private double price;
    private String photoUrl;
}
