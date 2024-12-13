package com.tz.campon.reservation.DTO;

import lombok.Data;

@Data
public class CampDetail {

    int campdetail_id;
    int camp_id;
    String detail_name;
    int price;
    String photo_url;

}
