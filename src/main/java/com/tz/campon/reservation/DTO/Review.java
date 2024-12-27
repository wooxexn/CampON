package com.tz.campon.reservation.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Review {

    @JsonProperty("review_id")
    int reviewId;
    @JsonProperty("user_id")
    String userId;
    @JsonProperty("camp_id")
    int campId;
    @JsonProperty("rating")
    int rating;
    @JsonProperty("comment")
    String comment;
    @JsonProperty("created_at")
    Date createdAt;


}
