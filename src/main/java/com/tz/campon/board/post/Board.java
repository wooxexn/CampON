package com.tz.campon.board.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Board {

        @JsonProperty("board_id")
        private Integer boardId;

        @JsonProperty("userId")
        private String userId;

        @JsonProperty("image_url")
        private List<String> imageUrls;

        @JsonProperty("caption")
        private String caption;

        @JsonProperty("like_count")
        private int likeCount;

        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createAt; // LocalDateTime 타입 사용

        // 추가된 필드: 포맷된 날짜
        private String formattedCreateAt;

        private List<Comment> comments;

        // formattedCreateAt의 getter
        public String getFormattedCreateAt() {
                return createAt != null ? createAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "작성일 없음";
        }
}
