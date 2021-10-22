package com.harwex213.controllers.api;

import com.harwex213.dto.cinemaMovies.CreateCinemaMovieDto;
import com.harwex213.dto.cinemaMovies.GetCinemaMovieDto;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.interfaces.ICinemaMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cinema-movies")
public class CinemaMoviesController {
    private final ICinemaMovieService iCinemaMovieService;

    @Autowired
    public CinemaMoviesController(ICinemaMovieService iCinemaMovieService) {
        this.iCinemaMovieService = iCinemaMovieService;
    }

    @GetMapping
    public List<GetCinemaMovieDto> getCinemaMovies()
    {
        return iCinemaMovieService.getCinemaMovies();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetCinemaMovieDto createCinemaMovie(@Valid @RequestBody CreateCinemaMovieDto createFilmDto)
            throws NotFoundException, BadRequestException {
        return iCinemaMovieService.createCinemaMovie(createFilmDto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCinemaMovie(@PathVariable("id") Long id) throws NotFoundException {
        iCinemaMovieService.deleteCinemaMovie(id);
    }
}
