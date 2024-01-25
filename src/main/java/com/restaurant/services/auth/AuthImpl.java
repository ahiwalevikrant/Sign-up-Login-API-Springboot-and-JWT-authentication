package com.restaurant.services.auth;

import com.restaurant.dtos.SingupRequest;
import com.restaurant.dtos.UserDto;
import com.restaurant.entities.User;
import com.restaurant.enums.UserRole;
import com.restaurant.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthImpl implements AuthService {

    private  final UserRepository userRepository;


    public AuthImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDto createUser(SingupRequest singupRequest) {
        User user =new User();
        user.setName(singupRequest.getName());
        user.setEmail(singupRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(singupRequest.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
       User createdUser= userRepository.save(user);
       UserDto CreatedUserDto = new UserDto();
       CreatedUserDto.setId(createdUser.getId());
       CreatedUserDto.setName(createdUser.getName());
        CreatedUserDto.setEmail(createdUser.getEmail());
        CreatedUserDto.setUserRole(createdUser.getUserRole());
        return CreatedUserDto;
    }
}
