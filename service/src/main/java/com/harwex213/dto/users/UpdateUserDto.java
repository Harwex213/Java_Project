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
    private String oldPassword;

    @NotNull(message = "Can not be null")
    private String newPassword;

    @NotNull(message = "Can not be null")
    private String repeatedNewPassword;
}
