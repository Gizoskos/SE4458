package com.gizemyazilim.mobileprovider.controller;

import com.gizemyazilim.mobileprovider.dto.*;
import com.gizemyazilim.mobileprovider.entity.AppUser;
import com.gizemyazilim.mobileprovider.entity.Subscriber;
import com.gizemyazilim.mobileprovider.repository.AppUserRepository;
import com.gizemyazilim.mobileprovider.repository.SubscriberRepository;
import com.gizemyazilim.mobileprovider.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v{version}/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final SubscriberRepository subscriberRepository;

    public AuthController(AuthenticationManager authenticationManager, AppUserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, SubscriberRepository subscriberRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.subscriberRepository = subscriberRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@PathVariable String version,
                                           @RequestBody AuthRequest request) {
        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        // Yeni kullanıcıya ait Subscriber oluştur
        Subscriber subscriber = new Subscriber();
        subscriber.setAppUser(user);
        subscriber.setName(request.getUsername()); // veya istersen ayrı bir DTO alanı ekleyebilirsin
        subscriberRepository.save(subscriber);

        return ResponseEntity.ok("User and subscriber registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@PathVariable String version,
                                              @RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtService.generateToken(request.getUsername());
        AppUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Subscriber subscriber = subscriberRepository.findByAppUserUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        return ResponseEntity.ok(new AuthResponse(token, subscriber.getId()));
    }
}
