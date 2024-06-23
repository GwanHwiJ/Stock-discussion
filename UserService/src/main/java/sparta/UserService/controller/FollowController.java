package sparta.UserService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sparta.UserService.dto.FollowUserRequestDto;
import sparta.UserService.dto.FollowUserResponseDto;
import sparta.UserService.service.FollowService;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/create-follow")
    public FollowUserResponseDto followUser(@RequestBody FollowUserRequestDto requestDto) {
       return followService.followUser(requestDto);
    }
}
