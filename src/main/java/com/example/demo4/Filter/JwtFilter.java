package com.example.demo4.Filter;

import com.example.demo4.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Component
public class JwtFilter extends GenericFilterBean {
    @Value("${jwt.name}")
    private String name;
    @Resource
    private JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // OncePreRequestFilter do not fit with SSE behaviour :(
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = ((HttpServletRequest) request).getHeader(name);
        if (!StringUtils.hasText(token) || !jwtUtil.simpleValidateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims payload = jwtUtil.getPayload(token);
        if (Objects.isNull(payload)) {
            filterChain.doFilter(request, response);
            return;
        }

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(payload.get("role").toString());
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(simpleGrantedAuthority);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        payload, null, authorityList));

        filterChain.doFilter(request, response);
    }
}
