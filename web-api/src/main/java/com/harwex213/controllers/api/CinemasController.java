package com.harwex213.controllers.api;

import com.harwex213.dto.cinemas.CreateCinemaDto;
import com.harwex213.dto.cinemas.GetCinemaDto;
import com.harwex213.dto.cinemas.UpdateCinemaDto;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.interfaces.ICinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cinemas")
public class CinemasController {
    private final ICinemaService iCinemaService;

    @Autowired
    public CinemasController(ICinemaService iCinemaService) {
        this.iCinemaService = iCinemaService;
    }
    
    @GetMapping
    public List<GetCinemaDto> getCinemas()
    {
        return iCinemaService.getCinemas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetCinemaDto createCinema(@Valid @RequestBody CreateCinemaDto createFilmDto)
    {
        return iCinemaService.createCinema(createFilmDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateCinema(@Valid @RequestBody UpdateCinemaDto updateCinemaDto) throws NotFoundException {
        iCinemaService.updateCinema(updateCinemaDto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCinema(@PathVariable("id") Long id) throws NotFoundException {
        iCinemaService.deleteCinema(id);
    }
}
