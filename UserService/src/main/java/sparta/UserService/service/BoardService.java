package sparta.UserService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.UserService.dto.CreateBoardRequestDto;
import sparta.UserService.dto.CreateBoardResponseDto;
import sparta.UserService.entity.Board;
import sparta.UserService.entity.Member;
import sparta.UserService.repository.BoardRepository;
import sparta.UserService.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    public CreateBoardResponseDto createBoard(Long memberId, CreateBoardRequestDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Board board = new Board(dto.getTitle(), dto.getImage(), dto.getDescription(), member);

        boardRepository.save(board);

        return CreateBoardResponseDto.of(board);
    }

}
