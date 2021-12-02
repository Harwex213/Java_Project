package com.harwex213.interfaces;

import com.harwex213.dto.users.CreateUserDto;
import com.harwex213.dto.users.GetUserDto;
import com.harwex213.dto.users.UpdateUserDto;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
    public List<GetUserDto> getUsers();
    public GetUserDto createUser(CreateUserDto createUserDto) throws BadRequestException;
    public void updateUser(UpdateUserDto updateUserDto) throws NotFoundException, BadRequestException;
    public void deleteUser(Long id) throws NotFoundException;
}