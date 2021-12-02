package com.harwex213.interfaces;


import com.harwex213.dto.users.AuthenticateUserDto;
import com.harwex213.dto.users.GetAuthenticatedUserDto;
import com.harwex213.dto.users.RegisterUserDto;
import com.harwex213.exceptions.BadRequestException;

public interface IAuthenticationService {
    GetAuthenticatedUserDto login(AuthenticateUserDto authenticateUserDto);
    GetAuthenticatedUserDto register(RegisterUserDto registerUserDto) throws BadRequestException;
}
