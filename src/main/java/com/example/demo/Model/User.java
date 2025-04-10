package com.example.demo.Model;

//package com.example.demo.Model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.time.LocalDateTime;
//import java.util.Collection;
//import java.util.Collections;
//
//@Setter
//@Getter
//@RequiredArgsConstructor
//@AllArgsConstructor
//@Entity
//@Data
//@Table(name = "users")
//public class User implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Email
//    @NotBlank(message = "Email là bắt buộc")
//    private String email;
//
//    @NotBlank(message = "Mật khẩu là bắt buộc")
//    private String password;
//
//    @NotBlank(message = "Tên là bắt buộc")
//    @Column(nullable = false)
//    private String name;
//
//    @Enumerated(EnumType.STRING)
//    private Role role = Role.USER;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    // Cập nhật để triển khai getUsername() trả về email của người dùng
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt = LocalDateTime.now();
//}
public class User {
    private String email;
    private String password;
    private String name;

    // Constructor for convenience in tests
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // Default constructor (used by UserService)
    public User() {}

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}