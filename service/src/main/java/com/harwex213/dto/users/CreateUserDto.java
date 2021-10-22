package com.harwex213.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {

    @NotNull(message = "Can not be null")
    private String username;

    @NotNull(message = "Can not be null")
    private String password;

    @NotNull(message = "Can not be null")
    private String repeatedPassword;
}
