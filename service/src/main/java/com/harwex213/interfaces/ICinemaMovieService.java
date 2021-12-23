package com.harwex213.interfaces;

import com.harwex213.dto.cinemaMovies.CreateCinemaMovieDto;
import com.harwex213.dto.cinemaMovies.GetCinemaMovieDto;
import com.harwex213.dto.cinemaMovies.GetCinemaMoviesByDateDto;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ICinemaMovieService {
    public List<GetCinemaMovieDto> getCinemaMoviesByCinema(Long cinemaId);
    public List<GetCinemaMoviesByDateDto> getCinemaMoviesByDate(LocalDate time);
    public GetCinemaMovieDto createCinemaMovie(CreateCinemaMovieDto createCinemaMovieDto) throws NotFoundException, BadRequestException;
    public void deleteCinemaMovie(Long id) throws NotFoundException;
}