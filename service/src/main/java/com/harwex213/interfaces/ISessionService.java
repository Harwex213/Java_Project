package com.harwex213.interfaces;

import com.harwex213.dto.sessions.CreateSessionDto;
import com.harwex213.dto.sessions.GetSessionDto;
import com.harwex213.dto.sessions.UpdateSessionDto;
import com.harwex213.exceptions.NotFoundException;

import java.util.List;

public interface ISessionService {
    public List<GetSessionDto> getSessions(Long cinemaMovieId);
    public GetSessionDto createSession(CreateSessionDto createSessionDto) throws NotFoundException;
    public void updateSession(UpdateSessionDto updateSessionDto) throws NotFoundException;
    public void deleteSession(Long id) throws NotFoundException;
}