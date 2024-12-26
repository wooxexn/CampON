package com.tz.campon.board.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getBoard(int currentPage, int size){
        System.out.println("current Page"+currentPage);
        System.out.println("size"+size);

        return boardRepository.selectAllPage((currentPage) * size, size);
    }

    public List<BoardImage> getBoardImage(){
        return boardRepository.selectAllPageImage();
    }

    public int getTotal(){
        return boardRepository.countAll();
    }

    public String saveImage(MultipartFile image) {
        String filePath = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        String fullPath = uploadDir + File.separator + filePath;
        try {
            File file = new File(fullPath);
            image.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
        return filePath;
    }

    public void saveBoard(Board board) {
        boardRepository.save(board);
    }

    public void saveBoardImage(String id,String image) {
        boardRepository.saveImage(id,image);
    }

    public Board getPostById(String id) {
        return boardRepository.findById(id);
    }

    public List<String> getImageById(String id){
        return boardRepository.findImageById(id);
    }

    public void updateBoard(String image_url, String board_id) {
        boardRepository.update(image_url, board_id);
    }

    public void updateBoardImage(int id,String image_url) {
        boardRepository.updateImage(id, image_url);
    }

    public void deletePostById(String id) {
        boardRepository.delete(id);
    }

    public void deleteBoardImage(String id) {
        boardRepository.deleteImage(id);
    }

    public int toggleLike(String boardId, String userId) {
        boolean isLiked = boardRepository.isLikedByUser(boardId, userId);
        if (isLiked) {
            boardRepository.removeLike(boardId, userId);
            boardRepository.decrementLikeCount(boardId);
        } else {
            boardRepository.addLike(boardId, userId);
            boardRepository.incrementLikeCount(boardId);
        }
        return boardRepository.getLikeCount(boardId);
    }

    public void addComment(String boardId, String userId, String content) {
        boardRepository.addComment(boardId, userId, content);
    }

    public List<Comment> getComments(String boardId) {
        return boardRepository.findCommentsByBoardId(boardId);
    }

}
