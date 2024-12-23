package com.tz.campon.reservation.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CampDetail {

    @JsonProperty("campdetail_id")
    int campdetailId;
    @JsonProperty("camp_id")
    int campId;
    @JsonProperty("detail_name")
    String detailName;
    @JsonProperty("price")
    int price;
    @JsonProperty("photo_url")
    String photoUrl;

}
