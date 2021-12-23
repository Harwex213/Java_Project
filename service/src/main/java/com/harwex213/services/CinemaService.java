package com.harwex213.services;

import com.harwex213.dto.cinemas.CreateCinemaDto;
import com.harwex213.dto.cinemas.GetCinemaByDateMovieDto;
import com.harwex213.dto.cinemas.GetCinemaDto;
import com.harwex213.dto.cinemas.UpdateCinemaDto;
import com.harwex213.dto.sessions.GetSessionDto;
import com.harwex213.entities.Cinema;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.interfaces.ICinemaService;
import com.harwex213.mapper.Mapper;
import com.harwex213.repositories.ICinemaMovieRepository;
import com.harwex213.repositories.ICinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CinemaService implements ICinemaService {
    private final ICinemaRepository iCinemaRepository;
    private final ICinemaMovieRepository iCinemaMovieRepository;

    @Autowired
    public CinemaService(ICinemaRepository iCinemaRepository,
                         ICinemaMovieRepository iCinemaMovieRepository) {
        this.iCinemaRepository = iCinemaRepository;
        this.iCinemaMovieRepository = iCinemaMovieRepository;
    }

    @Override
    public List<GetCinemaDto> getCinemas() {
        return Mapper.mapAll(iCinemaRepository.findAll(), GetCinemaDto.class);
    }


    @Override
    public List<GetCinemaByDateMovieDto> getCinemasByDateAndMovie(LocalDate time, Long movieId)
    {
        var cinemaMovies = iCinemaMovieRepository.findAll();
        List<GetCinemaByDateMovieDto> response = new ArrayList<>();
        for (var cinemaMovie:
                cinemaMovies
                        .stream()
                        .filter(cm -> Objects.equals(cm.getMovie().getId(), movieId))
                        .collect(Collectors.toList())) {

            var cinemaDto = new GetCinemaByDateMovieDto();
            cinemaDto.setId(cinemaMovie.getId());
            cinemaDto.setName(cinemaMovie.getCinema().getName());
            cinemaDto.setSessions(cinemaMovie.getSessions()
                    .stream()
                    .filter(s -> s.getTime().toLocalDate().equals(time))
                    .map(s -> {
                        var sessionDto = new GetSessionDto();
                        sessionDto.setId(s.getId());
                        sessionDto.setPrice(s.getPrice());
                        sessionDto.setTime(s.getTime());
                        sessionDto.setTicketsAmount(s.getTicketsAmount());
                        sessionDto.setCinemaMovieId(s.getCinemaMovie().getId());
                        sessionDto.setIsExistFreeSeats(s.getTicketList().size() < s.getTicketsAmount());
                        return sessionDto;
                    })
                    .collect(Collectors.toList()));

            response.add(cinemaDto);
        }

        return response;
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
