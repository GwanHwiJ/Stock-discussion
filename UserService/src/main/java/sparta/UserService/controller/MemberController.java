package sparta.UserService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.UserService.dto.SignUpRequestDto;
import sparta.UserService.dto.SignUpResponseDto;
import sparta.UserService.dto.UpdateProfileRequestDto;
import sparta.UserService.dto.UpdateProfileResponseDto;
import sparta.UserService.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public SignUpResponseDto createUser(@RequestBody SignUpRequestDto dto) {
        return memberService.createMember(dto);
    }

    @GetMapping("/update-profile")
    public UpdateProfileResponseDto updateProfile(@RequestBody UpdateProfileRequestDto dto) {
        return memberService.updateProfile(dto);
    }
}
