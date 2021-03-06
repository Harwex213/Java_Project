package com.harwex213.services;

import com.harwex213.dto.users.CreateUserDto;
import com.harwex213.dto.users.GetUserDto;
import com.harwex213.dto.users.UpdateUserDto;
import com.harwex213.entities.User;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.interfaces.IUserService;
import com.harwex213.mapper.Mapper;
import com.harwex213.models.UserRole;
import com.harwex213.models.UserWithDetails;
import com.harwex213.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserService implements IUserService {
    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository iUserRepository, PasswordEncoder passwordEncoder) {
        this.iUserRepository = iUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var user = iUserRepository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("User with such username doesn't exist"));

        var userAuthorities = new HashSet<UserRole>();
        userAuthorities.add(new UserRole(user.getRole()));

        return new UserWithDetails(user, userAuthorities);
    }

    @Override
    public List<GetUserDto> getUsers() {
        return Mapper.mapAll(iUserRepository.findAll(), GetUserDto.class);
    }

    @Override
    public GetUserDto getUser(Long id) throws NotFoundException {
        var user = iUserRepository.findById(id).orElseThrow(() -> new NotFoundException());
        return Mapper.map(user, GetUserDto.class);
    }

    @Override
    public GetUserDto createUser(CreateUserDto createUserDto) throws BadRequestException {
        try {
            if (!createUserDto.getPassword().equals(createUserDto.getRepeatedPassword())) {
                throw new BadRequestException("Password must match with repeated");
            }

            var user = Mapper.map(createUserDto, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            iUserRepository.save(user);

            return Mapper.map(user, GetUserDto.class);
        }
        catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Such username already exist");
        }
    }

    @Override
    public void updateUser(UpdateUserDto updateUserDto) throws NotFoundException, BadRequestException {
        try {
            var user = iUserRepository.findById(updateUserDto.getId()).orElseThrow(NotFoundException::new);

            if (updateUserDto.getIsPasswordChanged() &&
                    !updateUserDto.getNewPassword().equals(updateUserDto.getRepeatedNewPassword())) {
                throw new BadRequestException("New password must match with repeated");
            }
            if (updateUserDto.getIsPasswordChanged() &&
                    !updateUserDto.getOldPassword().equals(user.getPassword())) {
                throw new BadRequestException("Old password is not correct");
            }

            user.setUsername(updateUserDto.getUsername());
            user.setPassword(updateUserDto.getNewPassword());
            if (updateUserDto.getIsPasswordChanged()) {
                user.setPassword(updateUserDto.getNewPassword());
            }

            iUserRepository.save(user);
        }
        catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Such username already exist");
        }
    }

    @Override
    public void deleteUser(Long id) throws NotFoundException {
        var User = iUserRepository.findById(id).orElseThrow(NotFoundException::new);

        iUserRepository.delete(User);
    }

}
