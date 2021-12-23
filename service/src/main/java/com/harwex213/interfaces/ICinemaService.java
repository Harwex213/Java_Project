package com.harwex213.interfaces;

import com.harwex213.dto.cinemas.CreateCinemaDto;
import com.harwex213.dto.cinemas.GetCinemaByDateMovieDto;
import com.harwex213.dto.cinemas.GetCinemaDto;
import com.harwex213.dto.cinemas.UpdateCinemaDto;
import com.harwex213.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface ICinemaService {
    public List<GetCinemaDto> getCinemas();
    public List<GetCinemaByDateMovieDto> getCinemasByDateAndMovie(LocalDate date, Long movieId);
    public GetCinemaDto createCinema(CreateCinemaDto createCinemaDto);
    public void updateCinema(UpdateCinemaDto updateCinemaDto) throws NotFoundException;
    public void deleteCinema(Long id) throws NotFoundException;
}