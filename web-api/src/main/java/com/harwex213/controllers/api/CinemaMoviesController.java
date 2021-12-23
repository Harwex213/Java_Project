package com.harwex213.controllers.api;

import com.harwex213.dto.cinemaMovies.CreateCinemaMovieDto;
import com.harwex213.dto.cinemaMovies.GetCinemaMovieDto;
import com.harwex213.dto.cinemaMovies.GetCinemaMoviesByDateDto;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.interfaces.ICinemaMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/cinema-movies")
public class CinemaMoviesController {
    private final ICinemaMovieService iCinemaMovieService;

    @Autowired
    public CinemaMoviesController(ICinemaMovieService iCinemaMovieService) {
        this.iCinemaMovieService = iCinemaMovieService;
    }

    @GetMapping(path = "/by-cinema/{cinemaId}")
    public List<GetCinemaMovieDto> getCinemaMoviesByCinemaId(@PathVariable("cinemaId") Long cinemaId)
    {
        return iCinemaMovieService.getCinemaMoviesByCinema(cinemaId);
    }

    @GetMapping(path = "/{date}")
    public List<GetCinemaMoviesByDateDto> getCinemaMovies(@PathVariable("date") String date)
    {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var dateTime = LocalDate.parse(date, formatter);

        return iCinemaMovieService.getCinemaMoviesByDate(dateTime);
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
