package com.fptacademy.training.web;

import com.fptacademy.training.security.jwt.JwtTokenProvider;
import com.fptacademy.training.web.api.AuthenticationResource;
import com.fptacademy.training.web.vm.LoginVM;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class AuthenticationResourceImpl implements AuthenticationResource {
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public void login(LoginVM loginVM, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginVM.email(),
                loginVM.password()
        );
        Authentication authentication = authenticationManagerBuilder.getOrBuild().authenticate(authenticationToken);
        response.setHeader("access_token", tokenProvider.generateAccessToken(authentication));
        response.setHeader("refresh_token", tokenProvider.generateRefreshToken(authentication.getName()));
    }

    @Override
    public void getAccessTokenFromRefreshToken(String refreshToken, HttpServletResponse response) {
        if (StringUtils.hasText(refreshToken) && tokenProvider.validateRefreshToken(refreshToken)) {
            response.setHeader("access_token", tokenProvider.generateAccessToken(refreshToken));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
