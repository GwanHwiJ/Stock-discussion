package sparta.UserService.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.UserService.entity.Profile;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UpdateProfileResponseDto {

    private String name;

    private String profileimage;

    private String greeting;

    public static UpdateProfileResponseDto of(Profile profile) {
        return new UpdateProfileResponseDto(
                profile.getName(),
                profile.getProfileImage(),
                profile.getGreeting()
        );
    }
}
