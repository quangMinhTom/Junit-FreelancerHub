package com.example.demo.Service;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtService(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public String generateToken(User user) {
        // Truyền vào username thay vì toàn bộ đối tượng User
        return jwtUtil.generateToken(user.getEmail());  // Sử dụng email của người dùng làm thông tin định danh
    }

    public Authentication getAuthentication(String token) {
        String username = jwtUtil.extractUsername(token);
        UserDetails userDetails = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getTokenFromRequest(jakarta.servlet.http.HttpServletRequest request) {
        return jwtUtil.getTokenFromRequest(request);
    }

    public boolean isValidToken(String token) {
        return jwtUtil.isValidToken(token);
    }

    public String extractEmail(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.getSubject(); // Subject thường chứa email
    }
}
