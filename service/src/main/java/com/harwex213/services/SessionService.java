package com.harwex213.services;

import com.harwex213.dto.sessions.CreateSessionDto;
import com.harwex213.dto.sessions.GetSessionDto;
import com.harwex213.dto.sessions.UpdateSessionDto;
import com.harwex213.entities.Session;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.interfaces.ISessionService;
import com.harwex213.mapper.Mapper;
import com.harwex213.repositories.ICinemaMovieRepository;
import com.harwex213.repositories.ISessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SessionService implements ISessionService {
    private final ICinemaMovieRepository iCinemaMovieRepository;
    private final ISessionRepository iSessionRepository;

    @Autowired
    public SessionService(ICinemaMovieRepository iCinemaMovieRepository,
                          ISessionRepository iSessionRepository) {
        this.iCinemaMovieRepository = iCinemaMovieRepository;
        this.iSessionRepository = iSessionRepository;
    }

    @Override
    public List<GetSessionDto> getSessions() {
        return Mapper.mapAll(iSessionRepository.findAll(), GetSessionDto.class);
    }

    @Override
    public GetSessionDto createSession(CreateSessionDto createSessionDto) throws NotFoundException {
        var session = new Session();
        session.setTime(createSessionDto.getTime());
        session.setPrice(createSessionDto.getPrice());
        session.setTicketsAmount(createSessionDto.getTicketsAmount());
        var cinemaMovie = iCinemaMovieRepository.findById(createSessionDto.getCinemaMovieId())
                .orElseThrow(() -> new NotFoundException("CinemaMovie id not found"));

        session.setCinemaMovie(cinemaMovie);

        iSessionRepository.save(session);

        return Mapper.map(session, GetSessionDto.class);
    }

    @Override
    public void updateSession(UpdateSessionDto updateSessionDto) throws NotFoundException {
        var sessionUpdated = Mapper.map(updateSessionDto, Session.class);
        var session = iSessionRepository.findById(sessionUpdated.getId()).orElseThrow(NotFoundException::new);

        session.setTime(sessionUpdated.getTime());
        session.setTicketsAmount(sessionUpdated.getTicketsAmount());

        if (!Objects.equals(session.getCinemaMovie().getId(), updateSessionDto.getCinemaMovieId())) {
            var cinemaMovie = iCinemaMovieRepository.findById(updateSessionDto.getCinemaMovieId())
                    .orElseThrow(() -> new NotFoundException("CinemaMovie id not found"));

            session.setCinemaMovie(cinemaMovie);
        }

        iSessionRepository.save(session);
    }

    @Override
    public void deleteSession(Long id) throws NotFoundException {
        var Session = iSessionRepository.findById(id).orElseThrow(NotFoundException::new);

        iSessionRepository.delete(Session);
    }


}
