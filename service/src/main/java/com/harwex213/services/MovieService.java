package com.harwex213.services;

import com.harwex213.entities.Movie;
import com.harwex213.dto.movies.GetMovieDto;
import com.harwex213.dto.movies.CreateMovieDto;
import com.harwex213.dto.movies.UpdateMovieDto;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.repositories.IMovieRepository;
import com.harwex213.interfaces.IMovieService;
import com.harwex213.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService implements IMovieService {
    private final IMovieRepository iMovieRepository;

    @Autowired
    public MovieService(IMovieRepository iMovieRepository) {
        this.iMovieRepository = iMovieRepository;
    }

    @Override
    public List<GetMovieDto> getMovies() {
        return Mapper.mapAll(iMovieRepository.findAll(), GetMovieDto.class);
    }

    @Override
    public GetMovieDto createMovie(CreateMovieDto createMovieDto) {
        var movie = Mapper.map(createMovieDto, Movie.class);

        iMovieRepository.save(movie);

        return Mapper.map(movie, GetMovieDto.class);
    }

    @Override
    public void updateMovie(UpdateMovieDto updateMovieDto) throws NotFoundException {
        var movieUpdated = Mapper.map(updateMovieDto, Movie.class);
        var movie = iMovieRepository.findById(movieUpdated.getId()).orElseThrow(NotFoundException::new);

        movie.setName(movieUpdated.getName());
        movie.setDescription(movieUpdated.getDescription());

        iMovieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Long id) throws NotFoundException {
        var movie = iMovieRepository.findById(id).orElseThrow(NotFoundException::new);

        iMovieRepository.delete(movie);
    }


}
