package sparta.BoardService.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sparta.BoardService.dto.CreateCommentResponseDto;
import sparta.BoardService.service.CommentService;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add-comment")
    public CreateCommentResponseDto addComment(String comment, @RequestParam(name = "boardId") Long boardId,
                                               HttpServletRequest request) {
        return commentService.addComment(getMemberId(request), boardId, comment);
    }

    private Long getMemberId(HttpServletRequest request) {
        return (Long) request.getAttribute("member_id");
    }
}
