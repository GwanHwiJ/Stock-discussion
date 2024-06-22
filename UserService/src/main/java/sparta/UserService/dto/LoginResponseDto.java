package sparta.UserService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.UserService.config.CustomUserDetails;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginResponseDto {

    private Long memberId;
    private String email;
    private String authority;
    @JsonIgnore
    private String accessToken;
    @JsonIgnore
    private String refreshToken;

    public static LoginResponseDto of(CustomUserDetails customUserDetails, TokenDto tokenDto) {
        return new LoginResponseDto(
                customUserDetails.getMemberId(),
                customUserDetails.getUsername(),
                tokenDto.getAuthority(),
                tokenDto.getAccessToken(),
                tokenDto.getRefreshToken()
        );
    }
}
