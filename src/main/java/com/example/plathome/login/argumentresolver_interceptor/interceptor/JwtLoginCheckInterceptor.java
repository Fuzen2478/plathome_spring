package com.example.plathome.login.argumentresolver_interceptor.interceptor;


import com.example.plathome.login.domain.UserContext;
import com.example.plathome.login.service.JwtValidateService;
import com.example.plathome.member.domain.MemberSession;
import com.example.plathome.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.example.plathome.global.constant.JwtStaticField.REFRESH_URL;


@Slf4j
@RequiredArgsConstructor
public class JwtLoginCheckInterceptor implements HandlerInterceptor {

    private final MemberService memberService;
    private final JwtValidateService jwtValidateService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if(StringUtils.equals(request.getMethod(), "OPTIONS")){
            return true;
        }
        String requestURI = request.getRequestURI();
        log.info("MEMBER 로그인 인증 인터 셉터 실행 [{}]", requestURI);
        String stringMemberId = this.getMemberId(request);
        MemberSession memberSession = memberService.getMemberSessionById(Long.parseLong(stringMemberId));
        request.setAttribute("MemberSession", memberSession);
        UserContext.set(memberSession.nickname());
        log.info("MEMBER 로그인 확인 성공");
        return true;
    }

    private String getMemberId(HttpServletRequest request){
        if (!request.getRequestURI().contains(REFRESH_URL)) {
            return jwtValidateService.validateAccessToken(request);
        }
        return jwtValidateService.validateRefreshToken(request);
    }
}
