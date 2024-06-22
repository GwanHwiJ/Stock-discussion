package sparta.UserService.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PasswordChangeRequestDto {

    private Long memberId;

    private String currentPassword;

    private String newPassword;

}
