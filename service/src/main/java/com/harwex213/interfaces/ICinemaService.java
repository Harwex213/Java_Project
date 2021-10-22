package com.harwex213.interfaces;

import com.harwex213.dto.cinemas.CreateCinemaDto;
import com.harwex213.dto.cinemas.GetCinemaDto;
import com.harwex213.dto.cinemas.UpdateCinemaDto;
import com.harwex213.exceptions.NotFoundException;

import java.util.List;

public interface ICinemaService {
    public List<GetCinemaDto> getCinemas();
    public GetCinemaDto createCinema(CreateCinemaDto createCinemaDto);
    public void updateCinema(UpdateCinemaDto updateCinemaDto) throws NotFoundException;
    public void deleteCinema(Long id) throws NotFoundException;
}