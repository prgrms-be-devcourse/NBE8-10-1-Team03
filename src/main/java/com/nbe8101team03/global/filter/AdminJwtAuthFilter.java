package com.nbe8101team03.global.filter;

import com.nbe8101team03.global.exception.errorCode.JwtErrorCode;
import com.nbe8101team03.global.exception.exception.BaseException;
import com.nbe8101team03.global.exception.exception.JwtException;
import com.nbe8101team03.global.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AdminJwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public AdminJwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = resolveBearerToken(request);
        if (token == null) {
            throw new JwtException(JwtErrorCode.JWT_MISSING,
                    "JWT_MISSING",
                    "authorization header or bearer token does not exist"
            );
        }

        if (jwtUtil.isExpired(token)) {
            throw new JwtException(JwtErrorCode.JWT_EXPIRED,
                    "JWT_EXPIRED",
                    "jwt token has expired"
            );
        }

        String role;
        try {
            role = jwtUtil.getRole(token);
        } catch (Exception e) {
            throw new JwtException(JwtErrorCode.JWT_INVALID,
                    "JWT_INVALID",
                    "jwt token is invalid"
            );
        }

        if (!"ADMIN".equals(role)) {
            throw new JwtException(JwtErrorCode.JWT_FORBIDDEN,
                    "JWT_INVALID",
                    "jwt token is invalid"
            );
        }

        request.setAttribute("auth.userId", jwtUtil.getUserId(token));
        request.setAttribute("auth.role", role);

        filterChain.doFilter(request, response);
    }

    private String resolveBearerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null) return null;
        if (!header.startsWith("Bearer")) return null;
        String token = header.substring(7).trim();
        return token.isEmpty() ? null : token;
    }
}
