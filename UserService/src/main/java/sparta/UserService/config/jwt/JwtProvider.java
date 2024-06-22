package sparta.UserService.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import sparta.UserService.config.CustomUserDetails;
import sparta.UserService.dto.TokenDto;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtProvider {

    private static final String AUTHORITIES_KEY = "auth"; // header key name

    private static final String BEARER_TYPE = "bearer"; // token type

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 ;      // 1일

    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final Key private_key;

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.private_key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto createTokenDto(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

        log.info("memberId: {}", principal.getMemberId());

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())  // payload "sub": "email"
                .claim("member_id", principal.getMemberId()) // payload "member_id": "Long"
                .claim(AUTHORITIES_KEY, authorities)   // payload "auth": "role"
                .setExpiration(accessTokenExpiresIn)   // payload "exp": 1516239022 (예시)
                .signWith(private_key, SignatureAlgorithm.HS512)  // header "alg": "HS512" -> HS512: HMAC using SHA-512
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(private_key, SignatureAlgorithm.HS512)
                .compact();

        log.info("AccessToken: {}", accessToken);
        log.info("RefreshToken: {}", refreshToken);
        log.info("권한: {}", authorities);

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshTokenExpiresIn(REFRESH_TOKEN_EXPIRE_TIME)
                .refreshToken(refreshToken)
                .accessTokenExpireIn(ACCESS_TOKEN_EXPIRE_TIME)
                .authority(authorities)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {

        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
//        UserDetails principal = new User(claims.getSubject(), "", authorities);

        log.info("claims.get(AUTHORITIES_KEY): {}", claims.get(AUTHORITIES_KEY));
        CustomUserDetails principal = new CustomUserDetails(
                Long.parseLong(claims.get("member_id").toString()),
                claims.getSubject(),
                ""
        );

        // SecurityContext 를 사용하기 위한 절차(SecurityContext 가 Authentication 객체를 저장)
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(private_key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean validateToken(String token) {
        try {
            log.info("token: {}", token);
            Jwts.parserBuilder()
                    .setSigningKey(private_key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(private_key)
                .build().parseClaimsJws(accessToken).getBody().getExpiration();

        //현재 시간
        long now = new Date().getTime();

        return (expiration.getTime() - now);
    }

}
