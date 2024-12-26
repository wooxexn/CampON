package com.tz.campon.board.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Board {
        @JsonProperty("board_id")
        public String boardId;
        @JsonProperty("userId")
        public String userId;
        @JsonProperty("image_url")
        public List<String> imageUrls;
        @JsonProperty("caption")
        public String caption;
        @JsonProperty("like_count")
        public int likeCount;
        private List<Comment> comments;
    }
