package com.example.demo.Controller;
import com.example.demo.Model.User;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.Service.JwtService;
import com.example.demo.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.authenticateUser(loginRequest);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {

        if (userService.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new AuthResponse("Email is already in use."));
        }

        userService.registerUser(registerRequest);
        return ResponseEntity.ok(new AuthResponse("User registered successfully"));
    }
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String token) {
        // Xử lý token: Loại bỏ "Bearer " khỏi header
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Lấy email hoặc id từ token
        String email = jwtService.extractEmail(token);

        // Lấy thông tin người dùng từ email
        User user = userService.getUserByEmail(email);

        // Trả về thông tin người dùng
        return ResponseEntity.ok(user);
    }
    @GetMapping("/check-permission")
    public ResponseEntity<AuthResponse> checkPermissions(@RequestHeader("Authorization") String token) {
        try {
            // Kiểm tra token có tồn tại hay không
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(400).body(new AuthResponse("Token không hợp lệ"));
            }

            // Loại bỏ "Bearer " từ token
            String jwtToken = token.replace("Bearer ", "");

            // Kiểm tra tính hợp lệ của token
            if (!jwtService.isValidToken(jwtToken)) {
                return ResponseEntity.status(403).body(new AuthResponse("Token không hợp lệ hoặc hết hạn"));
            }

            // Lấy email từ token
            String email = jwtService.extractEmail(jwtToken);

            // Nếu token hợp lệ, xác thực quyền người dùng (có thể mở rộng thêm để kiểm tra role, quyền hạn)
            // Ở đây chỉ kiểm tra email trong token
            return ResponseEntity.ok(new AuthResponse("Token hợp lệ, người dùng có quyền truy cập"));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(new AuthResponse("Đã xảy ra lỗi khi kiểm tra quyền: " + e.getMessage()));
        }
    }

}
