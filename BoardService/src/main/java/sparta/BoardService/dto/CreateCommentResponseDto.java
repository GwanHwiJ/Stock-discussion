package sparta.BoardService.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import sparta.BoardService.entity.Board;
import sparta.BoardService.entity.Comment;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CreateCommentResponseDto {

    private String content;

    private String boardTitle;

    private String memberName;

    public static CreateCommentResponseDto of(Comment comment, Profile profile, Board board) {
        return new CreateCommentResponseDto(
                comment.getContent(),
                board.getTitle(),
                profile.getName()
        );
    }
}
