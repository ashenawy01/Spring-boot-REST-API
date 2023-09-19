package com.sigma.SecureAPI.service;


import com.sigma.SecureAPI.dto.AuthenticationRequest;
import com.sigma.SecureAPI.dto.AuthenticationResponse;
import com.sigma.SecureAPI.dto.RegisterRequest;
import com.sigma.SecureAPI.entity.Role;
import com.sigma.SecureAPI.entity.User;
import com.sigma.SecureAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
        ));
        System.out.println("Got out of Authentication manager");
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        var jwtToken = jwtService.generateToken(user);
        System.out.println("Build for return");
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
