package com.tz.campon.board.post;

import com.tz.campon.common.generator.CustomUUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BoardController {

    private final BoardService boardService;

    private final CustomUUIDGenerator customUUIDGenerator;

    public BoardController(BoardService boardService, CustomUUIDGenerator customUUIDGenerator) {
        this.boardService = boardService;
        this.customUUIDGenerator = customUUIDGenerator;
    }

    @GetMapping("/board")
    public String getboard(@RequestParam(required = false, defaultValue = "1", name = "currentPage") Integer currentPage, Model model){
        int size = 2;
        int grpSize = 5;
        int totalCount = 0;

        List<Board> list = boardService.getBoard(currentPage, size);
        List<BoardImage> imageList = boardService.getBoardImage();
        totalCount = boardService.getTotal();

        for (Board board : list) {
            List<Comment> comments = boardService.getComments(board.getBoardId());
            board.setComments(comments); // Board 클래스에 댓글 필드가 있어야 합니다.
        }

        System.out.println(totalCount);
        PageHandler handler = new PageHandler(currentPage, totalCount, size, grpSize);

        model.addAttribute("members", list);
        model.addAttribute("images", imageList);
        model.addAttribute("pageHandler", handler);

        return "board/boardlist";
    }

    @GetMapping("/board/add")
    public String addBoard(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        model.addAttribute("userId", userId);

        return "board/boardadd";
    }


    @PostMapping("/board/add")
    public String saveBoard(
            @RequestParam("userId") String userId,
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("caption") String caption,
            Model model
    ) {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            String imageUrl = boardService.saveImage(image); // 개별 이미지 저장
            imageUrls.add(imageUrl);
        }
        Board board = new Board();
        String board_id = customUUIDGenerator.generateUniqueUUID();
        board.setBoardId(board_id);
        board.setUserId(userId);
        board.setCaption(caption);

        boardService.saveBoard(board); // 데이터 저장
        for (String imageUrl : imageUrls) {
            boardService.saveBoardImage(board_id, imageUrl);
        }
        return "redirect:/board";
    }

    @GetMapping("/board/edit/{id}")
    public String editPost(@PathVariable("id") String id, Model model) {
        Board board = boardService.getPostById(id);
        model.addAttribute("board", board);
        return "board/boardedit";
    }

    @PostMapping("/board/edit")
    public String editBoard(
            @RequestParam("boardId") String boardId,
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("caption") String caption,
            Model model
    ) {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            String imageUrl = boardService.saveImage(image); // 개별 이미지 저장
            imageUrls.add(imageUrl);
        }
        boardService.deleteBoardImage(boardId);
        boardService.updateBoard(caption, boardId); // 데이터 저장
        for (String imageUrl : imageUrls) {
            boardService.saveBoardImage(boardId, imageUrl);
        }
        return "redirect:/board";
    }

    @GetMapping("/board/delete/{id}")
    public String deletePost(@PathVariable("id") String id) {
        Board post = boardService.getPostById(id);
        if (post == null) {
            throw new RuntimeException("Post not found");
        }
        // 본인 글인지 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!post.getUserId().equals(authentication.getName())) {
            throw new RuntimeException("You are not authorized to delete this post");
        }
        boardService.deleteBoardImage(id);
        boardService.deletePostById(id);
        return "redirect:/board";
    }

    @PostMapping("/board/like/{id}")
    @ResponseBody
    public int likePost(@PathVariable("id") String id, Authentication authentication) {
        String userId = authentication.getName();
        return boardService.toggleLike(id, userId);
    }

    @PostMapping("/board/comment/{id}")
    @ResponseBody
    public List<Comment> addComment(@PathVariable("id") String id, @RequestParam("content") String content, Authentication authentication) {
        String userId = authentication.getName();
        boardService.addComment(id, userId, content);
        return boardService.getComments(id);
    }

    @GetMapping("/board/comments/{id}")
    @ResponseBody
    public List<Comment> getComments(@PathVariable("id") String id) {
        return boardService.getComments(id);
    }

    @PostMapping("/board/comment/delete")
    @ResponseBody
    public String deleteComment(@RequestParam("commentId") int commentId, Authentication authentication) {
        String userId = authentication.getName();
        Comment comment = boardService.findCommentById(commentId);
        if (comment == null || !comment.getUserId().equals(userId)) {
            throw new RuntimeException("댓글 삭제 권한이 없습니다.");
        }
        boardService.deleteComment(commentId);
        return "댓글이 삭제되었습니다.";
    }


    @GetMapping("/test")
    public String test(String test ){
        System.out.println( test);
        return "board/boardlist";
    }

}
