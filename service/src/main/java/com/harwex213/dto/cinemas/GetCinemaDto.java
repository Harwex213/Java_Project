package com.harwex213.dto.cinemas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCinemaDto {

    @NotNull(message = "Can not be null")
    private Long id;

    @NotNull(message = "Can not be null")
    private String name;

    @NotNull(message = "Can not be null")
    private String description;

    @NotNull(message = "Can not be null")
    private String address;
}
