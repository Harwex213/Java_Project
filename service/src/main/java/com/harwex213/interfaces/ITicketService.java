package com.harwex213.interfaces;

import com.harwex213.dto.tickets.CreateTicketDto;
import com.harwex213.dto.tickets.GetTicketDto;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;

import java.util.List;

public interface ITicketService {
    public List<GetTicketDto> getTickets();
    public GetTicketDto createTicket(CreateTicketDto createTicketDto) throws BadRequestException, NotFoundException;
    public void deleteTicket(Long id) throws NotFoundException;
}