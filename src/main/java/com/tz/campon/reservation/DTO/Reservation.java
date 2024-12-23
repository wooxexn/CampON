package com.tz.campon.reservation.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reservation {
    @JsonProperty("reservation_id")
    int reservationId;
    @JsonProperty("user_id")
    String userId;
    @JsonProperty("camp_id")
    int campId;
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
    @JsonProperty("status")
    String status;
    @JsonProperty("created_at")
    Timestamp createdAt;

}
