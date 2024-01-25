package com.restaurant.controllers;

import com.restaurant.dtos.AuthenticationRequest;
import com.restaurant.dtos.AuthenticationResponse;
import com.restaurant.dtos.SingupRequest;
import com.restaurant.dtos.UserDto;
import com.restaurant.entities.User;
import com.restaurant.repositories.UserRepository;
import com.restaurant.services.auth.AuthService;
import com.restaurant.services.auth.jwt.UserDetailsServiceImpl;
import com.restaurant.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
   AuthService authService;

    @Autowired
   AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;


    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SingupRequest singupRequest) {
        UserDto createdUserDto = authService.createUser(singupRequest);

        if (createdUserDto == null) {
            return new ResponseEntity<>("User Not Created, Come Again Later", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public AuthenticationResponse CreateAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,HttpServletResponse response) throws IOException {
    try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));

    } catch (BadCredentialsException e){
        throw  new BadCredentialsException("Incorrect Username or Password..");
    }catch (DisabledException disabledException){
        response.sendError(HttpServletResponse.SC_NOT_FOUND,"User Not Active");
        return  null;
    }
     final UserDetails userDetails= userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
    final  String jwt=jwtUtil.generateToken(userDetails.getUsername());
        Optional<User> optionalUser= userRepository.findByEmail(userDetails.getUsername());
        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
        if(optionalUser.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            authenticationResponse.setUserid(optionalUser.get().getId());
        }
    return authenticationResponse;
    }

}
