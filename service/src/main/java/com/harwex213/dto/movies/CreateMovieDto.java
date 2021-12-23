package com.harwex213.dto.movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMovieDto {

    @NotNull(message = "Can not be null")
    private String name;

    @NotNull(message = "Can not be null")
    private String description;
}
