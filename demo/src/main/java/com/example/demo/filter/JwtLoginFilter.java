package com.example.demo.filter;

import com.example.demo.exception.TokenException;
import com.example.demo.result.ResultCodeEnum;
import com.example.demo.service.UserDetailService;
import com.example.demo.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtLoginFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = httpServletRequest.getHeader("accessToken");
        if(!StringUtils.hasText(accessToken)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        boolean checkToken = JWTUtil.checkToken(accessToken);
        if(!checkToken){
            throw new RuntimeException("token无效");
        }
        String username = JWTUtil.getUsername(accessToken);
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
