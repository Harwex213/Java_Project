package com.harwex213.services;

import com.harwex213.dto.cinemaMovies.CreateCinemaMovieDto;
import com.harwex213.dto.cinemaMovies.GetCinemaMovieDto;
import com.harwex213.entities.CinemaMovie;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.interfaces.ICinemaMovieService;
import com.harwex213.mapper.Mapper;
import com.harwex213.repositories.ICinemaMovieRepository;
import com.harwex213.repositories.ICinemaRepository;
import com.harwex213.repositories.IMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public List<GetCinemaMovieDto> getCinemaMovies() {
        return Mapper.mapAll(iCinemaMovieRepository.findAll(), GetCinemaMovieDto.class);
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
