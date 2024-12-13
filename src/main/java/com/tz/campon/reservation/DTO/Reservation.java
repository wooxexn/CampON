package com.tz.campon.reservation.DTO;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class Reservation {

    String user_id;
    int camp_id;
    int campdetail_id;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate check_in_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate  check_out_date;

    int number_of_guest;
    int total_price;
    String status;
    Timestamp created_at;

}
