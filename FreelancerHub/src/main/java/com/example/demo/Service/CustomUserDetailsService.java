package com.example.demo.Service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Ví dụ, bạn có thể thay thế logic này để lấy người dùng từ cơ sở dữ liệu
    @Override
    public UserDetails loadUserByUsername(String username) {
        // Giả sử bạn có 1 người dùng mặc định
        return User.builder()
                .username("user")
                .password("{noop}password")  // {noop} là chỉ thị rằng mật khẩu không được mã hóa
                .roles("USER")
                .build();
    }
}
