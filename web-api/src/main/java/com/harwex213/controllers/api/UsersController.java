package com.harwex213.controllers.api;

import com.harwex213.dto.users.CreateUserDto;
import com.harwex213.dto.users.GetUserDto;
import com.harwex213.dto.users.UpdateUserDto;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final IUserService iUserService;

    @Autowired
    public UsersController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping
    public List<GetUserDto> getUsers()
    {
        return iUserService.getUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetUserDto createUser(@Valid @RequestBody CreateUserDto createFilmDto) throws BadRequestException {
        return iUserService.createUser(createFilmDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@Valid @RequestBody UpdateUserDto updateUserDto) throws NotFoundException, BadRequestException {
        iUserService.updateUser(updateUserDto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") Long id) throws NotFoundException {
        iUserService.deleteUser(id);
    }
}
