package com.harwex213.dto.cinemaMovies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCinemaMovieDto {
    private Long id;
    private Long cinemaId;
    private Long movieId;
}
