package com.tz.campon.board.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {

    @JsonProperty("comment_id")
    public int commentId;
    @JsonProperty("userId")
    public String userId;
    @JsonProperty("board_id")
    public int boardId;
    @JsonProperty("content")
    public String content;
    @JsonProperty("created_at")
    public LocalDateTime createdAt;

}
