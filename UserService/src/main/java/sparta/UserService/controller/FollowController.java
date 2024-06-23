package sparta.UserService.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sparta.UserService.dto.FollowUserResponseDto;
import sparta.UserService.service.FollowService;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/create-follow")
    public FollowUserResponseDto followUser(HttpServletRequest request, @RequestParam(name = "followingId") Long followingId) {
       return followService.followUser(getMemberId(request), followingId);
    }

    private Long getMemberId(HttpServletRequest request) {
        return (Long) request.getAttribute("member_id");
    }
}
