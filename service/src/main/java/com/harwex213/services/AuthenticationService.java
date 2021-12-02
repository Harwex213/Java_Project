package com.harwex213.services;

import com.harwex213.dto.users.AuthenticateUserDto;
import com.harwex213.dto.users.CreateUserDto;
import com.harwex213.dto.users.GetAuthenticatedUserDto;
import com.harwex213.dto.users.RegisterUserDto;
import com.harwex213.entities.User;
import com.harwex213.exceptions.BadRequestException;
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
