package com.harwex213.controllers.auth;

import com.harwex213.dto.users.AuthenticateUserDto;
import com.harwex213.dto.users.GetAuthenticatedUserDto;
import com.harwex213.dto.users.GetUserDto;
import com.harwex213.dto.users.RegisterUserDto;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.UnauthenticatedException;
import com.harwex213.interfaces.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final IAuthenticationService iAuthenticationService;

    @Autowired
    public AuthenticationController(IAuthenticationService iAuthenticationService) {
        this.iAuthenticationService = iAuthenticationService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GetUserDto getUserByToken(@RequestHeader (name="Authorization") String authHeader) throws UnauthenticatedException {
        var token = authHeader.split(" ")[1].trim();
        return iAuthenticationService.getUserByToken(token);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public GetAuthenticatedUserDto login(@Valid @RequestBody AuthenticateUserDto authenticateUserDto) {
        return iAuthenticationService.login(authenticateUserDto);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public GetAuthenticatedUserDto register(@Valid @RequestBody RegisterUserDto registerUserDto)
            throws BadRequestException {
        return iAuthenticationService.register(registerUserDto);
    }
}