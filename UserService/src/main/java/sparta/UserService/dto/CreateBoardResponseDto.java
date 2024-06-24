package sparta.UserService.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.UserService.entity.Board;
import sparta.UserService.entity.Member;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CreateBoardResponseDto {

    private Long boardId;

    private String title;

    public static CreateBoardResponseDto of(Board board) {
        return new CreateBoardResponseDto(
                board.getId(),
                board.getTitle()
        );
    }

}
