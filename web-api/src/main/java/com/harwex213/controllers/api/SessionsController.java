package com.harwex213.controllers.api;

import com.harwex213.dto.sessions.CreateSessionDto;
import com.harwex213.dto.sessions.GetSessionDto;
import com.harwex213.dto.sessions.UpdateSessionDto;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.interfaces.ISessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionsController {
    private final ISessionService iSessionService;

    @Autowired
    public SessionsController(ISessionService iSessionService) {
        this.iSessionService = iSessionService;
    }

    @GetMapping(path="{cinemaMovieId}")
    public List<GetSessionDto> getSessions(@PathVariable("cinemaMovieId") Long cinemaMovieId)
    {
        return iSessionService.getSessions(cinemaMovieId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetSessionDto createSession(@Valid @RequestBody CreateSessionDto createFilmDto) throws NotFoundException {
        return iSessionService.createSession(createFilmDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateSession(@Valid @RequestBody UpdateSessionDto updateSessionDto) throws NotFoundException {
        iSessionService.updateSession(updateSessionDto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSession(@PathVariable("id") Long id) throws NotFoundException {
        iSessionService.deleteSession(id);
    }
}
