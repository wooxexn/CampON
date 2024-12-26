package com.tz.campon.board.post;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {

    @Select("SELECT COUNT(*) > 0 FROM board WHERE board_id = #{boardId}")
    boolean existsByReservationUuid(String boardId);

    /*
     게시글 저장
     @param params - 게시글 정보
     */
    @Insert("insert into board (board_id ,user_id, caption, created_at) values (#{boardId},#{userId}, #{caption}, NOW())")
    void save(Board board);

    @Insert("insert into board_images (board_id, image_url) values (#{boardId}, #{imageUrl})")
    void saveImage(@Param("boardId")String id, @Param("imageUrl")String imageUrl);

    /*
     게시글 상세정보 조회
     @param id - PK
     @return 게시글 상세정보
     */
    @Select("select * from board where board_id = #{boardId}")
    Board findById(@Param("boardId") String id);

    @Select("select image_url from board_images where board_id = #{boardId}")
    List<String> findImageUrlByBoardId(@Param("boardId") String id);

    /*
     게시글 수정
     @param params - 게시글 정보
     */
    @Update("update board set caption = #{caption} where board_id = #{boardId}")
    void update(@Param("caption") String caption,@Param("boardId") String board_id);

    @Update("update board_images set image_url = #{imageUrl} where boardi_id = #{id}")
    void updateImage(@Param("imageUrl")String imageUrl,@Param("Id") int id);

    /*
     게시글 삭제
     @param id - PK
     */
    @Delete("delete from board where board_id = #{boardId}")
    void deleteById(@Param("boardId")String id);

    @Delete("delete from board_images where board_id = #{boardId}")
    void deleteImageByBoardId(@Param("boardId")String id);

    /*
     게시글 리스트 조회
     @return 게시글 리스트
     */
    @Select("select * from board")
    List<Board> findAll();

    @Select("select * from board_images")
    List<BoardImage> findAllImages();

    /*
     게시글 수 카운팅
     @return 게시글 수
     */
    @Select("select count(*) from board")
    int count();

    // 좋아요 추가
    @Insert("INSERT INTO likes (board_id, user_id) VALUES (#{boardId}, #{userId})")
    void addLike(@Param("boardId") String boardId, @Param("userId") String userId);

    // 좋아요 제거
    @Delete("DELETE FROM likes WHERE board_id = #{boardId} AND user_id = #{userId}")
    void removeLike(@Param("boardId") String boardId, @Param("userId") String userId);

    // 좋아요 여부 확인
    @Select("SELECT COUNT(*) > 0 FROM likes WHERE board_id = #{boardId} AND user_id = #{userId}")
    boolean isLikedByUser(@Param("boardId") String boardId, @Param("userId") String userId);

    // 좋아요 개수 증가
    @Update("UPDATE board SET like_count = like_count + 1 WHERE board_id = #{boardId}")
    void incrementLikeCount(@Param("boardId") String boardId);

    // 좋아요 개수 감소
    @Update("UPDATE board SET like_count = like_count - 1 WHERE board_id = #{boardId}")
    void decrementLikeCount(@Param("boardId") String boardId);

    // 현재 좋아요 개수 조회
    @Select("SELECT like_count FROM board WHERE board_id = #{boardId}")
    int getLikeCount(@Param("boardId") String boardId);

    // 댓글 추가
    @Insert("INSERT INTO comments (board_id, user_id, content, created_at) VALUES (#{boardId}, #{userId}, #{content}, NOW())")
    void addComment(@Param("boardId") String boardId, @Param("userId") String userId, @Param("content") String content);

    // 특정 게시글의 댓글 조회
    @Select("SELECT comment_id, board_id, user_id, content, created_at FROM comments WHERE board_id = #{boardId} ORDER BY created_at ASC")
    List<Comment> findCommentsByBoardId(@Param("boardId") String boardId);

    // 댓글 삭제
    @Delete("DELETE FROM comments WHERE comment_id = #{commentId}")
    void deleteComment(@Param("commentId") int commentId);

    @Select("SELECT * from comments where comment_id = #{commentId}")
    Comment findCommentById(@Param("commentId") int commentId);
}
