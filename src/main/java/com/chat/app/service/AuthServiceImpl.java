package com.chat.app.service;

import com.chat.app.entity.Role;
import com.chat.app.entity.User;
import com.chat.app.exception.dto.BadRequestException;
import com.chat.app.exception.dto.NotFoundException;
import com.chat.app.repository.RoleRepository;
import com.chat.app.repository.UserRepository;
import com.chat.app.security.dto.UserDetailsImpl;
import com.chat.app.security.jwt.JwtTokenProvider;
import com.chat.app.security.payload.request.LoginRequest;
import com.chat.app.security.payload.request.SignupRequest;
import com.chat.app.security.payload.response.JwtResponse;
import com.chat.app.security.payload.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> authorities = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toSet());
        return JwtResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .authorities(authorities)
                .accessToken(jwt)
                .type("Bearer")
                .build();
    }

    @Override
    public MessageResponse register(SignupRequest signupRequest) throws BadRequestException, NotFoundException {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new BadRequestException("Email is existed");
        }
        createUser(signupRequest);
        return new MessageResponse("User registered successfully!");
    }

    private User createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        setRoleForUser(user, signupRequest.getRoles());
        return userRepository.save(user);
    }

    private void setRoleForUser(User user, Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();
        if (CollectionUtils.isEmpty(strRoles)) {
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            convertStringToRole(roles, strRoles);
        }
        user.setRoles(roles);
    }

    private void convertStringToRole(Set<Role> roles, Set<String> strRoles) {
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName("ADMIN")
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                    break;
                default:
                    Role userRole = roleRepository.findByName("USER")
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                    break;
            }
        });
    }
}
