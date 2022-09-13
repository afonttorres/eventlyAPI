package com.example.evently.auth.controller;

import com.example.evently.auth.config.JwtUtils;
import com.example.evently.auth.config.UserDetailsImpl;
import com.example.evently.models.Role;
import com.example.evently.models.user.User;
import com.example.evently.repositories.AuthRepository;
import com.example.evently.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {
    private final AuthRepository authRepository;
    private final RoleRepository roleRepository;
    private  final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationController(
            AuthRepository authRepository,
            RoleRepository roleRepository,
            PasswordEncoder encoder,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils) {
        this.authRepository = authRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginReq loginReq){
        System.out.println(" EXISTS? :"+authRepository.existsByUsername(loginReq.getUsername()));
        if(!authRepository.existsByUsername(loginReq.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageRes("User with username "+loginReq.getUsername()+" does not exist!"));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupReq singUpReq){

        if(authRepository.existsByUsername(singUpReq.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageRes("Username already taken!"));
        }

        if(authRepository.existsByEmail(singUpReq.getEmail())){
            return  ResponseEntity
                    .badRequest()
                    .body(new MessageRes("Email already in use!"));
        }

        User user = new User(
                singUpReq.getName(),
                singUpReq.getSurname(),
                singUpReq.getUsername(),
                singUpReq.getEmail(),
                encoder.encode(singUpReq.getPassword())
        );

        Set<String> strRoles = singUpReq.getRole();
        Set<Role> roles = new HashSet<>();


        if(strRoles == null){
            Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER)
                    .orElseThrow(()-> new RuntimeException("Role is not found."));
            roles.add(userRole);
        }else{
            strRoles.forEach(role->{
                switch (role){
                    case "admin" :{
                        Role adminRole = roleRepository.findByName(Role.RoleName.ROLE_ADMIN)
                                .orElseThrow(()-> new RuntimeException("Role is not found."));
                        roles.add(adminRole);
                    }
                    default:{
                        Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER)
                                .orElseThrow(()-> new RuntimeException("Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }

        user.setRoles(roles);
        authRepository.save(user);

//        LoginReq loginReq = new LoginReq(singUpReq.getUsername(), singUpReq.getPassword());
//        this.authenticateUser(loginReq);

        return ResponseEntity.ok(new MessageRes("User registered successfully!"));
    }

}
