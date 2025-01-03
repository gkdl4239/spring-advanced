package org.example.expert.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.expert.domain.user.enums.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    private final JwtUtil jwtUtil;

    public AuthInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws IOException {

        String url = request.getRequestURI();

        String token = request.getHeader("Authorization");
        if (token == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT 토큰이 필요합니다.");
        }

        try {
            String jwt = jwtUtil.substringToken(token);
            Claims claims = jwtUtil.extractClaims(jwt);
            UserRole userRole = UserRole.valueOf(claims.get("userRole", String.class));

            if (!UserRole.ADMIN.equals(userRole)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자 권한이 없습니다.");
                return false;
            }
            String time = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            logger.info("요청 시각: {} | 요청 주소 {}", time, url);
            return true;
        }
        catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 JWT입니다.");
            return false;
        }
    }
}
