package com.harwex213.controllers.api;

import com.harwex213.dto.movies.CreateMovieDto;
import com.harwex213.dto.movies.GetMovieDto;
import com.harwex213.dto.movies.UpdateMovieDto;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.interfaces.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/movies")
public class MoviesController {
    private final IMovieService iMovieService;

    @Autowired
    public MoviesController(IMovieService iMovieService) {
        this.iMovieService = iMovieService;
    }

    @GetMapping
    public List<GetMovieDto> getMovies()
    {
        return iMovieService.getMovies();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetMovieDto createMovie(@Valid @RequestBody CreateMovieDto createFilmDto)
    {
        return iMovieService.createMovie(createFilmDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateMovie(@Valid @RequestBody UpdateMovieDto updateMovieDto) throws NotFoundException {
        iMovieService.updateMovie(updateMovieDto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMovie(@PathVariable("id") Long id) throws NotFoundException {
        iMovieService.deleteMovie(id);
    }
}