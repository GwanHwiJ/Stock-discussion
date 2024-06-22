package sparta.UserService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.UserService.config.CustomUserDetails;
import sparta.UserService.config.jwt.JwtProvider;
import sparta.UserService.dto.*;
import sparta.UserService.entity.Member;
import sparta.UserService.entity.Profile;
import sparta.UserService.repository.MemberRepository;
import sparta.UserService.repository.ProfileRepository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    private final ProfileRepository profileRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RedisTemplate<String, String> redisTemplate;

    private final JwtProvider jwtProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public SignUpResponseDto createMember(SignUpRequestDto dto) {
        Optional<Member> memberOptional = memberRepository.findByEmail(dto.getEmail());
        if (memberOptional.isPresent()) {
            log.info("이미 존재하는 이메일입니다."); // 에러 처리
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        Member encoding = new Member(dto.getEmail(), bCryptPasswordEncoder.encode(dto.getPassword()), dto.getName());

        memberRepository.save(encoding);

        return SignUpResponseDto.of(encoding);
    }

    @Transactional
    public UpdateProfileResponseDto updateProfile(UpdateProfileRequestDto dto) {
        Profile profile = profileRepository.findByMemberId(dto.getMemberId()).orElseThrow();

        profile.update(dto.getName(), dto.getProfileImg(), dto.getGreeting());

        return UpdateProfileResponseDto.of(profile);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

        TokenDto tokenDto = jwtProvider.createTokenDto(authentication);

        redisTemplate.opsForValue().set("RT:" + authentication.getName(), tokenDto.getRefreshToken(),
                tokenDto.getRefreshTokenExpiresIn(),
                TimeUnit.MILLISECONDS);

        log.info("로그인 완료");
        return LoginResponseDto.of(principal, tokenDto);
    }

    public void logout(TokenRequestDto dto) {
        if (!jwtProvider.validateToken(dto.getAccessToken())) {
            log.info("잘못된 요청입니다."); //에러 처리
            throw new RuntimeException(); // 인증되지 않은 사용자 예외 처리
        }

        Authentication authentication = jwtProvider.getAuthentication(dto.getAccessToken());

        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            //refresh token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        Long expiration = jwtProvider.getExpiration(dto.getAccessToken());
        redisTemplate.opsForValue()
                .set(dto.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
    }

    public void changePassword(PasswordChangeRequestDto dto) {
        Optional<Member> memberOptional = memberRepository.findById(dto.getMemberId());
        if (memberOptional.isEmpty()) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }

        Member member = memberOptional.get();

        if (!bCryptPasswordEncoder.matches(dto.getCurrentPassword(), member.getPassword())) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }

        member.updatePassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
        memberRepository.save(member);
    }
}