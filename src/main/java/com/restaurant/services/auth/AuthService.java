package com.restaurant.services.auth;

import com.restaurant.dtos.SingupRequest;
import com.restaurant.dtos.UserDto;

public interface AuthService {
    UserDto createUser(SingupRequest singupRequest);
}
