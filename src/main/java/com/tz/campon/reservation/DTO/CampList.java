package com.tz.campon.reservation.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CampList {

    @JsonProperty("camp_id")
    int campId;
    @JsonProperty("name")
    String name;
    @JsonProperty("description")
    String description;
    @JsonProperty("region")
    String region;
    @JsonProperty("location")
    String location;
    @JsonProperty("price")
    int price;
    @JsonProperty("capacity")
    int capacity;
    @JsonProperty("facilities")
    String facilities;
    @JsonProperty("photo_url")
    String photoUrl;
    @JsonProperty("rating")
    int rating;
    @JsonProperty("mapX")
    double mapX;
    @JsonProperty("mapY")
    double mapY;
    @JsonProperty("phone")
    String phone;



}
