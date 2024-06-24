package sparta.UserService.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.UserService.entity.Board;
import sparta.UserService.entity.Comment;
import sparta.UserService.entity.Profile;

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
