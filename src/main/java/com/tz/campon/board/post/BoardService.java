package com.tz.campon.board.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final JdbcTemplate jdbcTemplate;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    public BoardService(BoardRepository boardRepository, JdbcTemplate jdbcTemplate) {
        this.boardRepository = boardRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    // 가장 큰 board_id를 조회하여 증가된 값을 생성하는 메소드
    public Integer generateBoardId() {
        // SQL 쿼리로 가장 큰 board_id 값을 조회
        String sql = "SELECT MAX(board_id) FROM board";
        Integer maxBoardId = jdbcTemplate.queryForObject(sql, Integer.class);

        // 최대값이 없으면 1부터 시작
        if (maxBoardId == null) {
            return 1; // 처음 게시글인 경우 1부터 시작
        } else {
            // 기존의 최대 board_id 값에 1을 더하여 새로운 board_id 생성
            return maxBoardId + 1;
        }
    }

    // 모든 게시글을 가져오는 메소드
    public List<Board> getBoard() {
        return boardRepository.selectAll();  // 페이지네이션 없이 모든 게시글을 가져옴
    }

    public List<BoardImage> getBoardImage(Integer id) {
        return boardRepository.selectAllPageImage(id);
    }

    public int getTotal() {
        return boardRepository.countAll();
    }

    public String saveImage(MultipartFile file) {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String fullPath = uploadDir + File.separator + fileName;

        try {
            File destination = new File(fullPath);
            file.transferTo(destination); // 파일 저장
            System.out.println(fullPath);
            return fileName; // 업로드 후 URL 반환
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }

    // 게시글 저장
    public void saveBoard(Board board) {
        // board_id를 순차적으로 생성
        Integer newBoardId = generateBoardId();  // 순차적인 board_id 생성
        board.setBoardId(newBoardId);           // 생성된 board_id 설정

        // board 객체를 데이터베이스에 저장
        boardRepository.save(board);
    }

    // 게시글에 이미지 저장
    public void saveBoardImage(Integer id, String image) {
        boardRepository.saveImage(id, image);
    }

    // 게시글 상세 조회
    public Board getPostById(Integer id) {
        return boardRepository.findById(id);
    }

    // 게시글 이미지 조회
    public List<String> getImageById(Integer id) {
        return boardRepository.findImageById(id);
    }

    // 게시글 수정
    public void updateBoard(String image_url, Integer board_id) {
        boardRepository.update(image_url, board_id);
    }

    // 게시글 이미지 수정
    public void updateBoardImage(Integer id, String image_url) {
        boardRepository.updateImage(id, image_url);
    }

    // 게시글 삭제
    public void deletePostById(Integer id) {
        boardRepository.delete(id);
    }

    // 게시글 이미지 삭제
    public void deleteBoardImage(Integer id) {
        boardRepository.deleteImage(id);
    }

    // 좋아요 처리
    public int toggleLike(Integer boardId, String userId) {
        boolean isLiked = boardRepository.isLikedByUser(boardId, userId);

        if (isLiked) {
            boardRepository.removeLike(boardId, userId);  // 좋아요 취소
            boardRepository.decrementLikeCount(boardId);  // 좋아요 수 감소
        } else {
            boardRepository.addLike(boardId, userId);     // 좋아요 추가
            boardRepository.incrementLikeCount(boardId);  // 좋아요 수 증가
        }

        // 좋아요 수가 음수로 내려가지 않도록 처리
        int likeCount = boardRepository.getLikeCount(boardId);
        if (likeCount < 0) {
            likeCount = 0;  // 좋아요 수가 음수일 경우 0으로 설정
            boardRepository.updateLikeCount(boardId, likeCount);  // 업데이트
        }

        return likeCount;  // 최종 좋아요 수 반환
    }


    // 댓글 추가
    public void addComment(Integer boardId, String userId, String content) {
        boardRepository.addComment(boardId, userId, content);
    }

    // 게시글에 대한 댓글 조회
    public List<Comment> getComments(Integer boardId) {
        List<Comment> comments = boardRepository.findCommentsByBoardId(boardId);
        System.out.println("서비스에서 조회된 댓글 목록: " + comments); // 댓글 확인
        return comments;
    }

    // 댓글 삭제
    public void deleteComment(int commentId) {
        boardRepository.deleteComment(commentId);
    }

    // 댓글 조회
    public Comment findCommentById(int commentId) {
        return boardRepository.findCommentById(commentId);
    }

    public Comment findLastComment() {return boardRepository.findLastComment();}
}
