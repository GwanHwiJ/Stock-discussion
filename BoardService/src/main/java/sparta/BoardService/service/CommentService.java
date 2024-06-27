package sparta.BoardService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.BoardService.dto.CreateCommentResponseDto;
import sparta.BoardService.entity.Board;
import sparta.BoardService.entity.Comment;
import sparta.BoardService.repository.BoardRepository;
import sparta.BoardService.repository.CommentRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;

    private final BoardRepository boardRepository;

    @Transactional
    public CreateCommentResponseDto addComment(Long memberId, Long boardId, String content) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Board board = boardRepository.findById(boardId).orElseThrow();

        Comment comment = new Comment(content, member, board);
        commentRepository.save(comment);

        return CreateCommentResponseDto.of(comment, member.getProfile(), board);
    }
}
