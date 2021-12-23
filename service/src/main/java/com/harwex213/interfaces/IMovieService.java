package com.harwex213.interfaces;

import com.harwex213.dto.movies.CreateMovieDto;
import com.harwex213.dto.movies.GetMovieDto;
import com.harwex213.dto.movies.UpdateMovieDto;
import com.harwex213.exceptions.NotFoundException;

import java.util.List;

public interface IMovieService {
    public List<GetMovieDto> getMovies();
    public GetMovieDto createMovie(CreateMovieDto createMovieDto);
    public void updateMovie(UpdateMovieDto updateMovieDto) throws NotFoundException;
    public void deleteMovie(Long id) throws NotFoundException;
}