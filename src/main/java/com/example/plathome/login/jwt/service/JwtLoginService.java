package com.example.plathome.login.jwt.service;


import com.example.plathome.login.email.service.MailMemberService;
import com.example.plathome.login.jwt.domain.UserContext;
import com.example.plathome.login.jwt.dto.MemberWithTokenDto;
import com.example.plathome.login.jwt.dto.request.LoginForm;
import com.example.plathome.login.jwt.dto.request.SignUpForm;
import com.example.plathome.login.jwt.provider.JwtProvider;
import com.example.plathome.login.jwt.service.redis.RefreshTokenRedisService;
import com.example.plathome.member.domain.Member;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.exception.DuplicationMemberException;
import com.example.plathome.member.exception.NotFoundMemberException;
import com.example.plathome.member.exception.NotMatchMemberPasswordException;
import com.example.plathome.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.plathome.global.error.ErrorStaticField.DUP_EMAIL;
import static com.example.plathome.global.error.ErrorStaticField.DUP_NICKNAME;
import static com.example.plathome.login.jwt.common.JwtStaticField.BEARER;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JwtLoginService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final MailMemberService mailMemberService;

    @Transactional
    public MemberWithTokenDto signUp(SignUpForm signUpForm) {
        verifyValidSignUpdForm(signUpForm);
        mailMemberService.verifyCode(signUpForm.email(), signUpForm.authCode());
        UserContext.set(signUpForm.nickname());
        return MemberWithTokenDto.withoutToken(memberRepository.save(signUpForm.toEntity(passwordEncoder)));
    }

    private void verifyValidSignUpdForm(SignUpForm signUpForm) {
        String email = signUpForm.email();
        String nickname = signUpForm.nickname();
        Optional<Member> optionalUser = memberRepository.findByEmailOrNickname(email, nickname);
        if (optionalUser.isPresent()) {
            if (optionalUser.get().getEmail().equals(email)) {
                throw new DuplicationMemberException(DUP_EMAIL);
            } else {
                throw new DuplicationMemberException(DUP_NICKNAME);
            }
        }
    }

    public MemberWithTokenDto login(LoginForm loginForm, HttpServletRequest request, HttpServletResponse response) {
        String email = loginForm.email();
        Member member = memberRepository.findByEmail(email).orElseThrow(NotFoundMemberException::new);
        if (passwordEncoder.matches(loginForm.password(), member.getPassword())) {
            String accessToken = jwtProvider.createAccessToken(member.getId().toString());
            String refreshToken = jwtProvider.createRefreshToken(member.getId().toString());
            refreshTokenRedisService.setData(member.getId().toString(), refreshToken);

            return MemberWithTokenDto.from(member, accessToken, refreshToken);
        }
        throw new NotMatchMemberPasswordException();
    }

    public MemberWithTokenDto refresh(MemberSession memberSession, HttpServletResponse response) {
        String accessToken = jwtProvider.createAccessToken(memberSession.id().toString());
        String refreshToken = jwtProvider.createRefreshToken(memberSession.id().toString());
        refreshTokenRedisService.setData(memberSession.id().toString(), refreshToken);

        return MemberWithTokenDto.of()
                .id(memberSession.id())
                .nickname(memberSession.nickname())
                .email(memberSession.email())
                .accessToken(BEARER + accessToken)
                .refreshToken(BEARER + refreshToken)
                .build();
    }

    public void logout(MemberSession memberSession) {
        refreshTokenRedisService.deleteData(memberSession.email());
    }
}
