package com.harwex213.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
    @NotNull(message = "Can not be null")
    private String username;

    @Email
    private String email;

    @Size(min = 4, max = 16, message = "password from 4 to 16")
    @NotNull(message = "Can not be null")
    private String password;

    @Size(min = 4, max = 16, message = "password from 4 to 16")
    @NotNull(message = "Can not be null")
    private String repeatedPassword;
}
