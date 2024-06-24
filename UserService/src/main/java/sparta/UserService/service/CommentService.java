package sparta.UserService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.UserService.dto.CreateBoardRequestDto;
import sparta.UserService.dto.CreateBoardResponseDto;
import sparta.UserService.dto.CreateCommentResponseDto;
import sparta.UserService.entity.Board;
import sparta.UserService.entity.Comment;
import sparta.UserService.entity.Member;
import sparta.UserService.entity.Profile;
import sparta.UserService.repository.BoardRepository;
import sparta.UserService.repository.CommentRepository;
import sparta.UserService.repository.MemberRepository;
import sparta.UserService.repository.ProfileRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;

    private final BoardRepository boardRepository;

    public CreateCommentResponseDto addComment(Long memberId, Long boardId, String content) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Board board = boardRepository.findById(boardId).orElseThrow();

        Comment comment = new Comment(content, member, board);
        commentRepository.save(comment);

        return CreateCommentResponseDto.of(comment, member.getProfile(), board);
    }
}
