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
    private int reservationId; // 예약 ID (PK)

    @JsonProperty("user_id")
    private String userId; // 사용자 ID (FK)

    @JsonProperty("camp_id")
    private int campId; // 캠프 ID

    @JsonProperty("campdetail_id")
    private int campdetailId; // 캠프 디테일 ID

    @JsonProperty("check_in_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkInDate; // 체크인 날짜

    @JsonProperty("check_out_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOutDate; // 체크아웃 날짜

    @JsonProperty("number_of_guest")
    private int numberOfGuest; // 예약 인원 수

    @JsonProperty("total_price")
    private int totalPrice; // 총 금액

    @JsonProperty("status")
    private String status; // 예약 상태 (옵션)

    @JsonProperty("created_at")
    private Timestamp createdAt; // 생성 시간 (옵션)

    // 아래는 추가 데이터
    private String name; // 캠프 이름 (추가)
    private String location; // 캠프 위치 (추가)
    private int price; // 캠프 디테일 가격 (추가)
    private String photoUrl; // 캠프 사진 URL (추가)
    private String detailName; // 캠프 디테일 이름 (추가)
}
