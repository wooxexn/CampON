package com.tz.campon.board.post;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {

    /*
     게시글 저장
     @param params - 게시글 정보
     */
    @Insert("insert into board (user_id, image_url, caption, create_at) values (#{userId}, #{imageUrl}, #{caption}, NOW())")
    void save(Board board);

    /*
     게시글 상세정보 조회
     @param id - PK
     @return 게시글 상세정보
     */
    @Select("select * from board where board_id = #{boardId}")
    Board findById(Integer id);

    /*
     게시글 수정
     @param params - 게시글 정보
     */
    @Update("update board set image_url = #{imageUrl}, caption = #{caption} where board_id = #{boardId}")
    void update(String image_url, String caption, Integer board_id);

    /*
     게시글 삭제
     @param id - PK
     */
    @Delete("delete from board where board_id = #{boardId}")
    void deleteById(Integer id);

    /*
     게시글 리스트 조회
     @return 게시글 리스트
     */
    @Select("select * from board")
    List<Board> findAll();

    /*
     게시글 수 카운팅
     @return 게시글 수
     */
    @Select("select count(*) from board")
    int count();

    // 좋아요 추가
    @Insert("INSERT INTO likes (board_id, user_id) VALUES (#{boardId}, #{userId})")
    void addLike(@Param("boardId") int boardId, @Param("userId") String userId);

    // 좋아요 제거
    @Delete("DELETE FROM likes WHERE board_id = #{boardId} AND user_id = #{userId}")
    void removeLike(@Param("boardId") int boardId, @Param("userId") String userId);

    // 좋아요 여부 확인
    @Select("SELECT COUNT(*) > 0 FROM likes WHERE board_id = #{boardId} AND user_id = #{userId}")
    boolean isLikedByUser(@Param("boardId") int boardId, @Param("userId") String userId);

    // 좋아요 개수 증가
    @Update("UPDATE board SET like_count = like_count + 1 WHERE board_id = #{boardId}")
    void incrementLikeCount(@Param("boardId") int boardId);

    // 좋아요 개수 감소
    @Update("UPDATE board SET like_count = like_count - 1 WHERE board_id = #{boardId}")
    void decrementLikeCount(@Param("boardId") int boardId);

    // 현재 좋아요 개수 조회
    @Select("SELECT like_count FROM board WHERE board_id = #{boardId}")
    int getLikeCount(@Param("boardId") int boardId);

    // 댓글 추가
    @Insert("INSERT INTO comments (board_id, user_id, content, create_at) VALUES (#{boardId}, #{userId}, #{content}, NOW())")
    void addComment(@Param("boardId") int boardId, @Param("userId") String userId, @Param("content") String content);

    // 특정 게시글의 댓글 조회
    @Select("SELECT comment_id, board_id, user_id, content, create_at FROM comments WHERE board_id = #{boardId} ORDER BY create_at ASC")
    List<Comment> findCommentsByBoardId(@Param("boardId") int boardId);

    // 댓글 삭제
    @Delete("DELETE FROM comments WHERE comment_id = #{commentId}")
    void deleteComment(@Param("commentId") int commentId);
}
