package sparta.UserService.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.UserService.config.CustomUserDetails;
import sparta.UserService.entity.Follow;
import sparta.UserService.entity.Member;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FollowUserResponseDto {

    private String followerName;

    private String followingName;

    public static FollowUserResponseDto of(Member follower, Member following) {
        return new FollowUserResponseDto(
                follower.getProfile().getName(),
                following.getProfile().getName()
        );
    }

}
