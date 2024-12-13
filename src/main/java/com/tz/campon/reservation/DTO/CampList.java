package com.tz.campon.reservation.DTO;


import lombok.Data;

@Data
public class CampList {

    int camp_id;
    String name;
    String description;
    String location;
    int price;
    int capacity;
    String facilities;
    String photo_url;
    int rating;


}
