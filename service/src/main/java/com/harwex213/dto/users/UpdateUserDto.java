package com.harwex213.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {

    @NotNull(message = "Can not be null")
    private Long id;

    @NotNull(message = "Can not be null")
    private String username;

    @NotNull(message = "Can not be null")
    private Boolean isPasswordChanged;

    private String oldPassword;

    private String newPassword;

    private String repeatedNewPassword;
}
