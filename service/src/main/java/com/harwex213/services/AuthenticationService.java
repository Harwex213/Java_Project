package com.harwex213.services;

import com.harwex213.dto.users.*;
import com.harwex213.entities.User;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.exceptions.UnauthenticatedException;
import com.harwex213.interfaces.IAuthenticationService;
import com.harwex213.interfaces.IUserService;
import com.harwex213.mapper.Mapper;
import com.harwex213.models.UserRole;
import com.harwex213.models.UserWithDetails;
import com.harwex213.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final IUserService iUserService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager,
                                 IUserService iUserService,
                                 JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.iUserService = iUserService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public GetUserDto getUserByToken(String token) throws UnauthenticatedException {
        if (!jwtTokenUtil.validate(token)) {
            throw new UnauthenticatedException();
        }

        try {
            return iUserService.getUser(Long.parseLong(jwtTokenUtil.getUserId(token)));
        }
        catch (NotFoundException e) {
            throw new UnauthenticatedException();
        }
    }

    @Override
    public GetAuthenticatedUserDto login(AuthenticateUserDto authenticateUserDto) {
        var authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authenticateUserDto.getUsername(),
                                authenticateUserDto.getPassword()
                        )
                );

        var user = ((UserWithDetails) authenticate.getPrincipal()).getUser();

        var getAuthenticatedUserDto = Mapper.map(user, GetAuthenticatedUserDto.class);
        getAuthenticatedUserDto.setAccessToken(jwtTokenUtil.generateAccessToken(user));

        return getAuthenticatedUserDto;
    }

    @Override
    public GetAuthenticatedUserDto register(RegisterUserDto registerUserDto) throws BadRequestException {
        var createUserDto = Mapper.map(registerUserDto, CreateUserDto.class);
        createUserDto.setRole(UserRole.USER_ROLE);

        var getUserDto = iUserService.createUser(createUserDto);
        var user = Mapper.map(getUserDto, User.class);

        var getAuthenticatedUserDto = Mapper.map(getUserDto, GetAuthenticatedUserDto.class);
        getAuthenticatedUserDto.setAccessToken(jwtTokenUtil.generateAccessToken(user));

        return getAuthenticatedUserDto;
    }
}
