package com.tz.campon.board.post;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardRepository {

    private final PostMapper postMapper;

    public BoardRepository(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public List<Board> selectAllPage(int currentPage, int size){

        int start = (currentPage-1)*size+1;
        int end = currentPage*size;

        System.out.println("start"+start);
        System.out.println("end"+end);
        Map<String,Integer> pageInfo = new HashMap<>();
        pageInfo.put("start", start);
        pageInfo.put("end", end);

        return postMapper.findAll();

    }

    public int countAll(){
        return postMapper.count();
    }

    public void save(Board board){
        postMapper.save(board);
    }

    public Board findById(int id){
        return postMapper.findById(id);
    }

    public void delete(int id){
        postMapper.deleteById(id);
    }

    // 좋아요 추가
    public void addLike(int boardId, String userId) {
        postMapper.addLike(boardId, userId);
    }

    // 좋아요 제거
    public void removeLike(int boardId, String userId) {
        postMapper.removeLike(boardId, userId);
    }

    // 특정 사용자가 게시글에 좋아요를 눌렀는지 확인
    public boolean isLikedByUser(int boardId, String userId) {
        return postMapper.isLikedByUser(boardId, userId);
    }

    // 좋아요 개수 증가
    public void incrementLikeCount(int boardId) {
        postMapper.incrementLikeCount(boardId);
    }

    // 좋아요 개수 감소
    public void decrementLikeCount(int boardId) {
        postMapper.decrementLikeCount(boardId);
    }

    // 현재 좋아요 개수 가져오기
    public int getLikeCount(int boardId) {
        return postMapper.getLikeCount(boardId);
    }

    // 댓글 추가
    public void addComment(int boardId, String userId, String content) {
        postMapper.addComment(boardId, userId, content);
    }

    // 특정 게시글의 댓글 조회
    public List<Comment> findCommentsByBoardId(int boardId) {
        return postMapper.findCommentsByBoardId(boardId);
    }

    // 댓글 삭제
    public void deleteComment(int commentId) {
        postMapper.deleteComment(commentId);
    }

}
