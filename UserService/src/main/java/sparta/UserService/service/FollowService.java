package sparta.UserService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.UserService.dto.FollowUserResponseDto;
import sparta.UserService.entity.Follow;
import sparta.UserService.entity.Member;
import sparta.UserService.repository.FollowRepository;
import sparta.UserService.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FollowService {

    private final FollowRepository followRepository;

    private final MemberRepository memberRepository;

    public FollowUserResponseDto followUser(Long followerId, Long followingId) {
        if(followingId.equals(followerId)){
            throw new RuntimeException("자기 자신을 팔로우 할 수 없습니다.");
        }

        Optional<Follow> checkFollow = followRepository.findByFollowingIdAndFollowerId(followingId, followerId);
        if(checkFollow.isPresent()){
            throw new RuntimeException("이미 팔로우한 회원입니다.");
        }

        Member follower = memberRepository.findById(followerId).orElseThrow();
        Member following = memberRepository.findById(followingId).orElseThrow();
        Follow follow = new Follow(follower, following);
        followRepository.save(follow);

        return FollowUserResponseDto.of(follower, following);
    }
}