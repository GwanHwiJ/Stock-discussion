package sparta.UserService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sparta.UserService.config.CustomUserDetails;
import sparta.UserService.entity.Member;
import sparta.UserService.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow();
        return new CustomUserDetails(member.getId(), member.getEmail(), member.getPassword());
    }

    public Member findLoginMember() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Principal is instanceof {}", authentication.getPrincipal().getClass());

        CustomUserDetails userDetails = null;
        try {
            userDetails = (CustomUserDetails) authentication.getPrincipal();
        } catch (ClassCastException e) {
            throw new RuntimeException(); // unauthorized 예외
        }

        log.info("CustomUserDetails.getMemberId: {}", userDetails.getMemberId());

        return memberRepository.findByEmail(userDetails.getUsername()).orElseThrow();   //userNotFound 예외
    }
}
