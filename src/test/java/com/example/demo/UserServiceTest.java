package com.example.demo;

import com.example.demo.Model.LoginRequest;
import com.example.demo.Model.RegisterRequest;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.JwtService;
import com.example.demo.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. registerUser - Path 1: Email exists
    @Test
    public void testRegisterUser_EmailAlreadyExists() {
        com.example.demo.dto.RegisterRequest request = new com.example.demo.dto.RegisterRequest("test@example.com", "password123", "Test User");
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(request);
        });
        assertEquals("Email already in use: test@example.com", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    // 1. registerUser - Path 2: Email doesn’t exist
    @Test
    public void testRegisterUser_Success() {
        com.example.demo.dto.RegisterRequest request = new com.example.demo.dto.RegisterRequest("test@example.com", "password123", "Test User");
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.registerUser(request);

        verify(userRepository).existsByEmail("test@example.com");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }

    // 2. authenticateUser - Path 1: User not found
    @Test
    public void testAuthenticateUser_UserNotFound() {
        com.example.demo.dto.LoginRequest request = new com.example.demo.dto.LoginRequest("test@example.com", "password123");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.authenticateUser(request);
        });
        assertEquals("User not found with email: test@example.com", exception.getMessage());
        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtService, never()).generateToken(any(User.class));
    }

    // 2. authenticateUser - Path 2: User found, password doesn’t match
    @Test
    public void testAuthenticateUser_InvalidPassword() {
        com.example.demo.dto.LoginRequest request = new com.example.demo.dto.LoginRequest("test@example.com", "wrongPassword");
        User user = new User("test@example.com", "encodedPassword", "Test User");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.authenticateUser(request);
        });
        assertEquals("Invalid password for email: test@example.com", exception.getMessage());
        verify(jwtService, never()).generateToken(any(User.class));
    }

    // 2. authenticateUser - Path 3: User found, password matches
    @Test
    public void testAuthenticateUser_Success() {
        com.example.demo.dto.LoginRequest request = new com.example.demo.dto.LoginRequest("test@example.com", "password123");
        User user = new User("test@example.com", "encodedPassword", "Test User");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        String token = userService.authenticateUser(request);

        assertEquals("jwtToken", token);
        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).matches("password123", "encodedPassword");
        verify(jwtService).generateToken(user);
    }

    // 3. getUserByEmail - Path 1: Email null or empty
    @Test
    public void testGetUserByEmail_NullEmail() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserByEmail(null);
        });
        assertEquals("Email cannot be null or empty", exception.getMessage());
        verify(userRepository, never()).findByEmail(anyString());
    }

    // 3. getUserByEmail - Path 2: Email valid, user not found
    @Test
    public void testGetUserByEmail_UserNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserByEmail("test@example.com");
        });
        assertEquals("User not found with email: test@example.com", exception.getMessage());
        verify(userRepository).findByEmail("test@example.com");
    }

    // 3. getUserByEmail - Path 3: Email valid, user found
    @Test
    public void testGetUserByEmail_Success() {
        User user = new User("test@example.com", "encodedPassword", "Test User");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail("test@example.com");

        assertEquals("test@example.com", result.getEmail());
        assertEquals("Test User", result.getName());
        verify(userRepository).findByEmail("test@example.com");
    }

    // 4. existsByEmail - Path 1: Email null or empty
    @Test
    public void testExistsByEmail_EmptyEmail() {
        boolean exists = userService.existsByEmail("");

        assertFalse(exists);
        verify(userRepository, never()).existsByEmail(anyString());
    }

    // 4. existsByEmail - Path 2: Email valid, user exists
    @Test
    public void testExistsByEmail_EmailExists() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        boolean exists = userService.existsByEmail("test@example.com");

        assertTrue(exists);
        verify(userRepository).existsByEmail("test@example.com");
    }

    // 4. existsByEmail - Path 3: Email valid, user doesn’t exist
    @Test
    public void testExistsByEmail_EmailDoesNotExist() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        boolean exists = userService.existsByEmail("test@example.com");

        assertFalse(exists);
        verify(userRepository).existsByEmail("test@example.com");
    }

    // 5. updateUserName - Path 1: New name null or empty
    @Test
    public void testUpdateUserName_EmptyName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserName("test@example.com", "");
        });
        assertEquals("New name cannot be null or empty", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    // 5. updateUserName - Path 2: New name valid, user not found
    @Test
    public void testUpdateUserName_UserNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUserName("test@example.com", "New Name");
        });
        assertEquals("User not found with email: test@example.com", exception.getMessage());
        verify(userRepository).findByEmail("test@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    // 5. updateUserName - Path 3: New name valid, user found
    @Test
    public void testUpdateUserName_Success() {
        User user = new User("test@example.com", "encodedPassword", "Old Name");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.updateUserName("test@example.com", "New Name");

        assertEquals("New Name", user.getName());
        verify(userRepository).findByEmail("test@example.com");
        verify(userRepository).save(user);
    }
}