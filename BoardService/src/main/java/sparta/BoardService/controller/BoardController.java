package sparta.BoardService.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.BoardService.dto.CreateBoardRequestDto;
import sparta.BoardService.dto.CreateBoardResponseDto;
import sparta.BoardService.service.BoardService;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/create-board")
    public CreateBoardResponseDto createBoard(HttpServletRequest request, @RequestBody CreateBoardRequestDto dto) {
        return boardService.createBoard(getMemberId(request), dto);
    }

    private Long getMemberId(HttpServletRequest request) {
        return (Long) request.getAttribute("member_id");
    }
}
