package com.nbe8101team03.global.filter;

import com.nbe8101team03.global.exception.errorCode.JwtErrorCode;
import com.nbe8101team03.global.exception.exception.JwtException;
import com.nbe8101team03.global.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

public class AdminJwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public AdminJwtAuthFilter(JwtUtil jwtUtil, ObjectMapper objectMapper){
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = resolveBearerToken(request);
        if(token == null){
            throw new JwtException(JwtErrorCode.JWT_MISSING,
                    "JWT_MISSING",
                    "authorization header or bearer token does not exist"
            );
        }

        if(jwtUtil.isExpired(token)){
            throw new JwtException(JwtErrorCode.JWT_EXPIRED,
                    "JWT_EXPIRED",
                    "jwt token has expired"
                    );
        }

        String role;
        try {
            role = jwtUtil.getRole(token);
        } catch(Exception e){
            throw new JwtException(JwtErrorCode.JWT_INVALID);
        }

        if(!"ADMIN".equals(role)){
            throw new JwtException(JwtErrorCode.JWT_FORBIDDEN);
        }

        request.setAttribute("auth.userId", jwtUtil.getUserId(token));
        request.setAttribute("autho.role", role);
    }
}
