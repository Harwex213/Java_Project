package com.harwex213.services;

import com.harwex213.dto.cinemaMovies.CreateCinemaMovieDto;
import com.harwex213.dto.cinemaMovies.GetCinemaMovieDto;
import com.harwex213.dto.cinemaMovies.GetCinemaMoviesByDateDto;
import com.harwex213.entities.CinemaMovie;
import com.harwex213.entities.Session;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.interfaces.ICinemaMovieService;
import com.harwex213.mapper.Mapper;
import com.harwex213.repositories.ICinemaMovieRepository;
import com.harwex213.repositories.ICinemaRepository;
import com.harwex213.repositories.IMovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CinemaMovieService implements ICinemaMovieService {
    private final ICinemaMovieRepository iCinemaMovieRepository;
    private final ICinemaRepository iCinemaRepository;
    private final IMovieRepository iMovieRepository;

    @Autowired
    public CinemaMovieService(ICinemaMovieRepository iCinemaMovieRepository,
                              ICinemaRepository iCinemaRepository,
                              IMovieRepository iMovieRepository) {
        this.iCinemaMovieRepository = iCinemaMovieRepository;
        this.iCinemaRepository = iCinemaRepository;
        this.iMovieRepository = iMovieRepository;
    }

    @Override
    public List<GetCinemaMoviesByDateDto> getCinemaMoviesByDate(LocalDate time) {
        var cinemaMovies = iCinemaMovieRepository.findAll();
        Map<Long, GetCinemaMoviesByDateDto> response = new HashMap<>();
        for (var cinemaMovie:
             cinemaMovies) {
            var session = cinemaMovie.getSessions().stream()
                    .filter(s -> s.getTime().toLocalDate().equals(time))
                    .min(Comparator.comparingDouble(Session::getPrice))
                    .orElse(null);
            if (session != null) {
                var cinemaMovieDto = new GetCinemaMoviesByDateDto();
                cinemaMovieDto.setId(cinemaMovie.getId());
                cinemaMovieDto.setMovieId(cinemaMovie.getMovie().getId());
                cinemaMovieDto.setMovieName(cinemaMovie.getMovie().getName());
                cinemaMovieDto.setMinPrice(session.getPrice());

                response.put(cinemaMovie.getId(), cinemaMovieDto);
            }
        }
        return new ArrayList<>(response.values());
    }

    @Override
    public GetCinemaMovieDto createCinemaMovie(CreateCinemaMovieDto createCinemaMovieDto)
            throws NotFoundException, BadRequestException {
        try {
            var cinemaMovie = new CinemaMovie();
            var cinema = iCinemaRepository.findById(createCinemaMovieDto.getCinemaId())
                    .orElseThrow(() -> new NotFoundException("Cinema id not found"));
            var movie = iMovieRepository.findById(createCinemaMovieDto.getMovieId())
                    .orElseThrow(() -> new NotFoundException("Movie id not found"));

            cinemaMovie.setCinema(cinema);
            cinemaMovie.setMovie(movie);

            iCinemaMovieRepository.save(cinemaMovie);

            return Mapper.map(cinemaMovie, GetCinemaMovieDto.class);
        }
        catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Duplicate entity");
        }
    }

    @Override
    public void deleteCinemaMovie(Long id) throws NotFoundException {
        var cinemaMovie = iCinemaMovieRepository.findById(id).orElseThrow(NotFoundException::new);

        iCinemaMovieRepository.delete(cinemaMovie);
    }


}
