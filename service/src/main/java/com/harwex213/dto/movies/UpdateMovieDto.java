package com.harwex213.dto.movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMovieDto {
    private Long id;
    private String name;
    private String description;
}