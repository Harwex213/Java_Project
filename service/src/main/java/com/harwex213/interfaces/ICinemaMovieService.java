package com.harwex213.interfaces;

import com.harwex213.dto.cinemaMovies.CreateCinemaMovieDto;
import com.harwex213.dto.cinemaMovies.GetCinemaMovieDto;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;

import java.util.List;

public interface ICinemaMovieService {
    public List<GetCinemaMovieDto> getCinemaMovies();
    public GetCinemaMovieDto createCinemaMovie(CreateCinemaMovieDto createCinemaMovieDto) throws NotFoundException, BadRequestException;
    public void deleteCinemaMovie(Long id) throws NotFoundException;
}