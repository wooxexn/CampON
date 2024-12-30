package com.tz.campon.board.post;

import com.tz.campon.common.generator.CustomUUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시글 목록을 가져오는 메서드
    @GetMapping("/board")
    public String getBoard(Model model) {
        try {
            List<Board> list = boardService.getBoard();
            for (Board board : list) {
                // 댓글을 가져와서 게시글에 추가
                List<Comment> comments = boardService.getComments(board.getBoardId());
                board.setComments(comments);

                // 날짜 포맷 처리
                String formattedDate = board.getCreateAt() != null
                        ? board.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        : "작성일 없음";
                board.setFormattedCreateAt(formattedDate); // 포맷된 날짜 설정
            }

            // 게시글 목록과 총 게시글 수를 모델에 추가
            model.addAttribute("posts", list);
            model.addAttribute("totalPosts", list.size());
            return "board/boardlist"; // 게시글 목록을 보여주는 템플릿
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "게시글 목록을 가져오는 데 오류가 발생했습니다.");
            return "error/500"; // 오류 페이지를 보여줌
        }
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
            @RequestParam(value = "images", required = false) List<MultipartFile> images, // 파일을 선택하지 않아도 되도록 수정
            @RequestParam("caption") String caption,
            Model model
    ) {
        List<String> imageUrls = new ArrayList<>();
        if (images != null && !images.isEmpty()) { // 이미지가 있을 경우에만 처리
            for (MultipartFile image : images) {
                String imageUrl = boardService.saveImage(image); // 개별 이미지 저장
                imageUrls.add(imageUrl);
            }
        }
        Board board = new Board();
        board.setUserId(userId);
        board.setCaption(caption);

        boardService.saveBoard(board); // 데이터 저장
        for (String imageUrl : imageUrls) {
            boardService.saveBoardImage(board.getBoardId(), imageUrl); // 이미지 저장
        }
        return "redirect:/board"; // 게시글 목록 페이지로 리디렉션
    }

    @PostMapping("/upload-image")
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        // 이미지를 저장하고 URL을 반환하는 로직
        return boardService.saveImage(file); // 클라이언트에 반환할 이미지 URL
    }

    @GetMapping("/board/detail/{id}")
    public String getPostDetail(@PathVariable("id") Integer id, Model model,Authentication authentication) {
        // 게시글 데이터를 가져옵니다.
        Board post = boardService.getPostById(id);
        List<BoardImage> imageList = boardService.getBoardImage(id);

        if (post == null) {
            model.addAttribute("errorMessage", "해당 게시글을 찾을 수 없습니다.");
            return "error/404";
        }

        List<Comment> comments = boardService.getComments(id);
        post.setComments(comments);

        // createdAt을 문자열로 변환
        String formattedDate = post.getCreateAt() != null
                ? post.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                : "작성일 없음";

        model.addAttribute("post", post);
        model.addAttribute("images", imageList);
        model.addAttribute("formattedCreateAt", formattedDate); // 변환된 날짜 추가
        model.addAttribute("isLoggedIn", authentication != null && authentication.isAuthenticated());

        return "board/boarddetail";
    }


    @GetMapping("/board/edit/{id}")
    public String editPost(@PathVariable("id") Integer id, Model model) {  // Integer 타입으로 수정
        Board board = boardService.getPostById(id);  // 게시글 조회
        model.addAttribute("board", board);  // 모델에 게시글 데이터 추가
        return "board/boardedit";  // boardedit.html로 포워딩
    }

    @PostMapping("/board/edit")
    public String editBoard(
            @RequestParam("boardId") Integer boardId,  // Integer 타입으로 수정
            @RequestParam(value = "images", required = false) List<MultipartFile> images,  // 이미지가 없을 수도 있으므로 required=false
            @RequestParam("caption") String caption,
            Model model
    ) {
        // 이미지 URL을 담을 리스트
        List<String> imageUrls = new ArrayList<>();

        // 이미지가 있을 경우에만 처리
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                String imageUrl = boardService.saveImage(image); // 이미지 저장
                imageUrls.add(imageUrl);
            }
        }

        // 게시글 제목 수정
        boardService.updateBoard(caption, boardId); // Integer 타입으로 처리

        // 이미지 삭제 후 새로 저장
        boardService.deleteBoardImage(boardId); // 이전 이미지를 삭제
        for (String imageUrl : imageUrls) {
            boardService.saveBoardImage(boardId, imageUrl); // 새로운 이미지를 저장
        }

        return "redirect:/board"; // 게시글 목록으로 리디렉션
    }

    @GetMapping("/board/comments/{id}")
    @ResponseBody
    public ResponseEntity<List<Comment>> getComments(@PathVariable("id") Integer id) {
        List<Comment> comments = boardService.getComments(id);
        // 로그 추가
        System.out.println("조회된 댓글 목록: " + comments); // 댓글 확인
        if (comments == null || comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>()); // 댓글이 없을 때 빈 배열 반환
        }
        return ResponseEntity.ok(comments);
    }


    @PostMapping("/board/comment/add")
    @ResponseBody
    public Comment addComment(@RequestParam("boardId") Integer boardId,
                             @RequestParam("content") String content,
                             Authentication authentication) {
        // 로그인된 사용자의 ID를 가져오기
        String userId = authentication.getName();

        // 댓글을 추가하는 서비스 호출
        boardService.addComment(boardId, userId, content);

        // 게시글 상세 페이지로 리다이렉트
        return boardService.findLastComment();
    }


    @PostMapping("/board/comment/delete")
    @ResponseBody
    public ResponseEntity<String> deleteComment(@RequestParam("commentId") int commentId, Authentication authentication) {
        String userId = authentication.getName();
        Comment comment = boardService.findCommentById(commentId);

        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글이 존재하지 않습니다.");
        }

        if (!comment.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 삭제 권한이 없습니다.");
        }

        boardService.deleteComment(commentId);
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }

    @PostMapping("/board/like/{id}")
    @ResponseBody
    public ResponseEntity<Integer> toggleLike(@PathVariable("id") Integer id, Principal principal) {
        String userId = principal.getName();
        int likeCount = boardService.toggleLike(id, userId);
        return ResponseEntity.ok(likeCount);
    }

    @GetMapping("/test")
    public String test(String test ){
        System.out.println( test);
        return "board/boardlist";
    }
}
