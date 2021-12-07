package com.harwex213.dto.cinemaMovies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCinemaMoviesByDateDto {

    @NotNull(message = "Can not be null")
    private Long id;

    @NotNull(message = "Can not be null")
    private Long movieId;

    @NotNull(message = "Can not be null")
    private String movieName;

    @NotNull(message = "Can not be null")
    private Double minPrice;
}
