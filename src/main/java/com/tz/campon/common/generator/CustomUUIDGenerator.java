package com.tz.campon.common.generator;

import com.tz.campon.board.post.PostMapper;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CustomUUIDGenerator {
    private static final String PREFIX = "BD";
    private static final int RANDOM_PART_LENGTH = 10;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();
    private final PostMapper postMapper;

    public CustomUUIDGenerator(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public Integer generateUniqueUUID() {
        Integer boardId;
        do {
            boardId = generateCustomUUID();
        } while (postMapper.existsByReservationUuid(boardId)); // 예약 번호 존재하는 지 확인
        return boardId;
    }

    private Integer generateCustomUUID() {
        StringBuilder sb = new StringBuilder(PREFIX);
        for (int i = 0; i < RANDOM_PART_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return Integer.parseInt(sb.toString().substring(2));  // PREFIX가 "BD"이므로, 그 이후 숫자만 Integer로 변환
    }
}
