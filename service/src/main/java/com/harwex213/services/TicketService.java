package com.harwex213.services;

import com.harwex213.dto.tickets.CreateTicketDto;
import com.harwex213.dto.tickets.GetTicketDto;
import com.harwex213.entities.Ticket;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.interfaces.ITicketService;
import com.harwex213.mapper.Mapper;
import com.harwex213.repositories.ISessionRepository;
import com.harwex213.repositories.ITicketRepository;
import com.harwex213.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService implements ITicketService {
    private final ITicketRepository iTicketRepository;
    private final ISessionRepository iSessionRepository;
    private final IUserRepository iUserRepository;

    @Autowired
    public TicketService(ITicketRepository iTicketRepository,
                         ISessionRepository iSessionRepository,
                         IUserRepository iUserRepository) {
        this.iTicketRepository = iTicketRepository;
        this.iSessionRepository = iSessionRepository;
        this.iUserRepository = iUserRepository;
    }

    @Override
    public List<GetTicketDto> getTickets() {
        return Mapper.mapAll(iTicketRepository.findAll(), GetTicketDto.class);
    }

    @Override
    public GetTicketDto createTicket(CreateTicketDto createTicketDto)
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

            return Mapper.map(ticket, GetTicketDto.class);
        }
        catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Duplicate entity");
        }
    }

    @Override
    public void deleteTicket(Long id) throws NotFoundException {
        var Ticket = iTicketRepository.findById(id).orElseThrow(NotFoundException::new);

        iTicketRepository.delete(Ticket);
    }


}
