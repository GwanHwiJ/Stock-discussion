package sparta.UserService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sparta.UserService.dto.*;
import sparta.UserService.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public SignUpResponseDto createUser(@RequestBody SignUpRequestDto dto) {
        return memberService.createMember(dto);
    }

    @PostMapping("/update-profile")
    public UpdateProfileResponseDto updateProfile(@RequestBody UpdateProfileRequestDto dto) {
        return memberService.updateProfile(dto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto dto) {
        return memberService.login(dto);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody TokenRequestDto tokenRequestDto) {
        memberService.logout(tokenRequestDto);
    }
}