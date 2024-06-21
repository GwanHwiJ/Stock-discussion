package sparta.UserService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.UserService.dto.SignUpRequestDto;
import sparta.UserService.dto.SignUpResponseDto;
import sparta.UserService.entity.Member;
import sparta.UserService.entity.Profile;
import sparta.UserService.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

}
