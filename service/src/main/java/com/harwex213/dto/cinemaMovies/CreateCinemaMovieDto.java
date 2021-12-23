package com.harwex213.dto.cinemaMovies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCinemaMovieDto {

    @NotNull(message = "Can not be null")
    private Long cinemaId;

    @NotNull(message = "Can not be null")
    private Long movieId;
}
