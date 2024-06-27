package sparta.BoardService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.BoardService.dto.CreateBoardRequestDto;
import sparta.BoardService.dto.CreateBoardResponseDto;
import sparta.BoardService.entity.Board;
import sparta.BoardService.repository.BoardRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public CreateBoardResponseDto createBoard(Long memberId, CreateBoardRequestDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Board board = new Board(dto.getTitle(), dto.getImage(), dto.getDescription(), member);

        boardRepository.save(board);

        return CreateBoardResponseDto.of(board);
    }

}
