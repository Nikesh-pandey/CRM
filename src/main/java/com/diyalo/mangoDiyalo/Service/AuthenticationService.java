package com.diyalo.mangoDiyalo.Service;

import com.diyalo.mangoDiyalo.Dto.AuthenticationResponse;
import com.diyalo.mangoDiyalo.Dto.LoginRequest;
import com.diyalo.mangoDiyalo.Dto.RegisterRequest;
import com.diyalo.mangoDiyalo.Config.JwtService;
import com.diyalo.mangoDiyalo.Entities.User;
import com.diyalo.mangoDiyalo.Exception.EmailAlreadyUsedException;
import com.diyalo.mangoDiyalo.Repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        String email = normalizeEmail(request.getEmail());

        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyUsedException("An account with this email already exists");
        }

        User user = User.builder()
                .name(request.getName().trim())
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .build();

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            // Guards against the race where two requests pass the existence check at once.
            throw new EmailAlreadyUsedException("An account with this email already exists");
        }

        return buildResponse(user);
    }

    public AuthenticationResponse login(LoginRequest request) {
        String email = normalizeEmail(request.getEmail());

        // Throws BadCredentialsException on failure (handled by the global exception handler).
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, request.getPassword())
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return buildResponse(user);
    }

    private AuthenticationResponse buildResponse(User user) {
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .tokenType("Bearer")
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}
