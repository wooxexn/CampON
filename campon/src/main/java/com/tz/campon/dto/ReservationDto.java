package com.tz.campon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReservationDto {

    @NotBlank(message = "예약 날짜는 필수입니다.")  // 비어있지 않도록 검사
    private String reservationDate;

    @NotBlank(message = "구역은 필수입니다.")  // 비어있지 않도록 검사
    private String area;  // 구역 정보
}
