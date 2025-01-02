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

    public List<Board> selectAll() {
        return postMapper.findAll(); // 페이지네이션 없이 모든 게시글 조회
    }

    public List<BoardImage> selectAllPageImage(Integer id){
        return postMapper.findAllImages(id);
    }

    public int countAll(){
        return postMapper.count();
    }

    public void save(Board board){
        postMapper.save(board);
    }

    public void saveImage(Integer id, String image){
        postMapper.saveImage(id, image);
    }

    public Board findById(Integer id) {
        Board board = postMapper.findById(id);
        System.out.println("Repository에서 조회된 게시글: " + board);
        return board;
    }

    public List<String> findImageById(Integer id){
        return postMapper.findImageUrlByBoardId(id);
    }

    public void update(String image_url, Integer board_id){
        postMapper.update(image_url, board_id);
    }

    public void updateImage(Integer id, String image_url){
        postMapper.updateImage(image_url, id);
    }

    public void delete(Integer id){
        postMapper.deleteById(id);
    }

    public void deleteImage(Integer id){
        postMapper.deleteImageByBoardId(id);
    }

    // 좋아요 추가
    public void addLike(Integer boardId, String userId) {
        postMapper.addLike(boardId, userId);
    }

    public void updateLikeCount(Integer boardId, int likeCount) {
        postMapper.updateLikeCount(boardId, likeCount); // 좋아요 수 업데이트
    }

    // 좋아요 제거
    public void removeLike(Integer boardId, String userId) {
        postMapper.removeLike(boardId, userId);
    }

    // 특정 사용자가 게시글에 좋아요를 눌렀는지 확인
    public boolean isLikedByUser(Integer boardId, String userId) {
        return postMapper.isLikedByUser(boardId, userId);
    }

    // 좋아요 개수 증가
    public void incrementLikeCount(Integer boardId) {
        postMapper.incrementLikeCount(boardId);
    }

    // 좋아요 개수 감소
    public void decrementLikeCount(Integer boardId) {
        postMapper.decrementLikeCount(boardId);
    }

    // 현재 좋아요 개수 가져오기
    public int getLikeCount(Integer boardId) {
        return postMapper.getLikeCount(boardId);
    }

    public void deleteLike(Integer boardId) {
        postMapper.deleteLike(boardId);
    }

    // 댓글 추가
    public void addComment(Integer boardId, String userId, String content) {
        postMapper.addComment(boardId, userId, content);
    }

    // 특정 게시글의 댓글 조회
    public List<Comment> findCommentsByBoardId(Integer boardId) {
        return postMapper.findCommentsByBoardId(boardId);
    }

    // 댓글 삭제
    public void deleteComment(int commentId) {
        postMapper.deleteComment(commentId);
    }

    public void deleteCommentByBoardId(Integer boardId) {postMapper.deleteCommentByBoardId(boardId);}

    public Comment findCommentById(int commentId) {
        return postMapper.findCommentById(commentId);
    }

    public Comment findLastComment() {return postMapper.findLastComment();}
}

