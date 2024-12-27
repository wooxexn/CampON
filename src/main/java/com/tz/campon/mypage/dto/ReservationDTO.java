package com.tz.campon.mypage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    @JsonProperty("reservation_id")
    int reservationId;
    @JsonProperty("campId")
    int campId;
    @JsonProperty("name")
    String name;
    @JsonProperty("location")
    String location;
    @JsonProperty("price")
    int price;
    @JsonProperty("photo_url")
    String photoUrl;
    @JsonProperty("userId")
    String userId;
    @JsonProperty("campdetail_id")
    int campdetailId;
    @JsonProperty("check_in_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate checkInDate;
    @JsonProperty("check_out_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate checkOutDate;
    @JsonProperty("number_of_guest")
    int numberOfGuest;
    @JsonProperty("total_price")
    int totalPrice;
    @JsonProperty("detail_name")
    String detailName;
}
