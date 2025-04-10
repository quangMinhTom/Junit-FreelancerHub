//package com.example.demo.Controller;
//
//import com.example.demo.Model.User;
//import com.example.demo.Service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//
//    private final UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    // Lấy tất cả các user
//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    // Lấy user theo ID
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        Optional<User> user = userService.getUserById(id);
//        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // Lưu một user mới
////    @PostMapping
////    public ResponseEntity<User> createUser(@RequestBody User user) {
////        User savedUser = userService.saveUser(user);
////        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
////    }
////
////    // Xóa user theo ID
////    @DeleteMapping("/{id}")
////    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
////        userService.deleteUser(id);
////        return ResponseEntity.noContent().build();
////    }
//}
