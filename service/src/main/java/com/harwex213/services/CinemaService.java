package com.harwex213.services;

import com.harwex213.dto.cinemas.CreateCinemaDto;
import com.harwex213.dto.cinemas.GetCinemaDto;
import com.harwex213.dto.cinemas.UpdateCinemaDto;
import com.harwex213.entities.Cinema;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.interfaces.ICinemaService;
import com.harwex213.mapper.Mapper;
import com.harwex213.repositories.ICinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService implements ICinemaService {
    private final ICinemaRepository iCinemaRepository;

    @Autowired
    public CinemaService(ICinemaRepository iCinemaRepository) {
        this.iCinemaRepository = iCinemaRepository;
    }

    @Override
    public List<GetCinemaDto> getCinemas() {
        return Mapper.mapAll(iCinemaRepository.findAll(), GetCinemaDto.class);
    }

    @Override
    public GetCinemaDto createCinema(CreateCinemaDto createCinemaDto) {
        var cinema = Mapper.map(createCinemaDto, Cinema.class);

        iCinemaRepository.save(cinema);

        return Mapper.map(cinema, GetCinemaDto.class);
    }

    @Override
    public void updateCinema(UpdateCinemaDto updateCinemaDto) throws NotFoundException {
        var cinemaUpdated = Mapper.map(updateCinemaDto, Cinema.class);
        var cinema = iCinemaRepository.findById(cinemaUpdated.getId()).orElseThrow(NotFoundException::new);

        cinema.setName(cinemaUpdated.getName());
        cinema.setDescription(cinemaUpdated.getDescription());
        cinema.setAddress(cinemaUpdated.getAddress());

        iCinemaRepository.save(cinema);
    }

    @Override
    public void deleteCinema(Long id) throws NotFoundException {
        var cinema = iCinemaRepository.findById(id).orElseThrow(NotFoundException::new);

        iCinemaRepository.delete(cinema);
    }


}
