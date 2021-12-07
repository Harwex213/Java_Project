package com.harwex213.services;

import com.harwex213.dto.tickets.CreateTicketDto;
import com.harwex213.dto.tickets.GetCreateTicketDto;
import com.harwex213.dto.tickets.GetTicketDto;
import com.harwex213.entities.Ticket;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.exceptions.UnauthenticatedException;
import com.harwex213.interfaces.ITicketService;
import com.harwex213.mapper.Mapper;
import com.harwex213.repositories.ISessionRepository;
import com.harwex213.repositories.ITicketRepository;
import com.harwex213.repositories.IUserRepository;
import com.harwex213.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService implements ITicketService {
    private final ITicketRepository iTicketRepository;
    private final ISessionRepository iSessionRepository;
    private final IUserRepository iUserRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public TicketService(ITicketRepository iTicketRepository,
                         ISessionRepository iSessionRepository,
                         IUserRepository iUserRepository,
                         JwtTokenUtil jwtTokenUtil) {
        this.iTicketRepository = iTicketRepository;
        this.iSessionRepository = iSessionRepository;
        this.iUserRepository = iUserRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public List<GetTicketDto> getTicketsByToken(String token) throws UnauthenticatedException {
        if (!jwtTokenUtil.validate(token)) {
            throw new UnauthenticatedException();
        }

        try {
            var tickets = iUserRepository.findById(Long.parseLong(jwtTokenUtil.getUserId(token)))
                    .orElseThrow(() -> new NotFoundException()).getTicketList();
            return tickets.stream()
                    .filter(ticket -> ticket.getSession().getTime().isAfter(LocalDateTime.now()))
                    .map(ticket -> {
                var session = ticket.getSession();
                var getTicketDto = new GetTicketDto();
                getTicketDto.setId(ticket.getId());
                getTicketDto.setUserId(ticket.getUser().getId());
                getTicketDto.setSessionId(session.getId());
                getTicketDto.setPrice(session.getPrice());
                getTicketDto.setTime(session.getTime());
                getTicketDto.setCinemaName(session.getCinemaMovie().getCinema().getName());
                getTicketDto.setMovieName(session.getCinemaMovie().getMovie().getName());
                return getTicketDto;
            }).collect(Collectors.toList());
        }
        catch (NotFoundException e) {
            throw new UnauthenticatedException();
        }
    }

    @Override
    public GetCreateTicketDto createTicket(CreateTicketDto createTicketDto)
            throws NotFoundException, BadRequestException {
        try {
            var ticket = new Ticket();
            var session = iSessionRepository.findById(createTicketDto.getSessionId())
                    .orElseThrow(() -> new NotFoundException("Session id not found"));
            var user = iUserRepository.findById(createTicketDto.getUserId())
                    .orElseThrow(() -> new NotFoundException("User id not found"));

            ticket.setSession(session);
            ticket.setUser(user);

            iTicketRepository.save(ticket);

            return Mapper.map(ticket, GetCreateTicketDto.class);
        }
        catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Already Ordered");
        }
    }

    @Override
    public void deleteTicket(Long id) throws NotFoundException {
        var Ticket = iTicketRepository.findById(id).orElseThrow(NotFoundException::new);

        iTicketRepository.delete(Ticket);
    }


}
