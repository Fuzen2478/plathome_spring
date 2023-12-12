package com.example.plathome.login.controller;


import com.example.plathome.login.argumentresolver_interceptor.argumentresolver.Login;
import com.example.plathome.login.dto.request.LoginForm;
import com.example.plathome.login.dto.request.SignUpForm;
import com.example.plathome.login.dto.response.MemberResponse;
import com.example.plathome.login.dto.response.TokenResponse;
import com.example.plathome.login.service.LoginService;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.service.MemberService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/jwt")
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;
    private final MemberService memberService;

    @GetMapping("/auth/token")
    public TokenResponse refresh(@Parameter(hidden = true) @Login MemberSession memberSession) {
        return loginService.refresh(memberSession);
    }

    @GetMapping("/auth")
    public MemberResponse get(@Parameter(hidden = true) @Login MemberSession memberSession) {
        return memberService.getBySession(memberSession);
    }

    @PostMapping("/no-auth/sign-up")
    public MemberResponse signUp(@RequestBody @Valid SignUpForm signUpForm) {
        return loginService.signUp(signUpForm);
    }

    @PostMapping("/no-auth/login")
    public TokenResponse login(@RequestBody @Valid LoginForm loginForm) {
        return loginService.login(loginForm);
    }

    @GetMapping("/auth/logout")
    public String logout(@Parameter(hidden = true) @Login MemberSession memberSession) {
        loginService.logout(memberSession);
        return "success";
    }
}
