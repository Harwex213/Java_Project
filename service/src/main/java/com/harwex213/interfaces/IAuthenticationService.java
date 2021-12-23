package com.harwex213.interfaces;


import com.harwex213.dto.users.AuthenticateUserDto;
import com.harwex213.dto.users.GetAuthenticatedUserDto;
import com.harwex213.dto.users.GetUserDto;
import com.harwex213.dto.users.RegisterUserDto;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.UnauthenticatedException;

public interface IAuthenticationService {
    GetUserDto getUserByToken(String token) throws UnauthenticatedException;
    GetAuthenticatedUserDto login(AuthenticateUserDto authenticateUserDto);
    GetAuthenticatedUserDto register(RegisterUserDto registerUserDto) throws BadRequestException;
}
