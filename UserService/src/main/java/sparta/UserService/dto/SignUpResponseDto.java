package sparta.UserService.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.UserService.entity.Member;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignUpResponseDto {

    private String email;

    private String name;

    public static SignUpResponseDto of(Member member) {
        return new SignUpResponseDto(
                member.getEmail(),
                member.getProfile().getName()
        );
    }

}
