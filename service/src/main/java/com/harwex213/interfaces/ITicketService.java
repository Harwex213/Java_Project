package com.harwex213.interfaces;

import com.harwex213.dto.tickets.CreateTicketDto;
import com.harwex213.dto.tickets.GetCreateTicketDto;
import com.harwex213.dto.tickets.GetTicketDto;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.exceptions.UnauthenticatedException;

import java.util.List;

public interface ITicketService {
    public List<GetTicketDto> getTicketsByToken(String token) throws UnauthenticatedException;
    public GetCreateTicketDto createTicket(CreateTicketDto createTicketDto) throws BadRequestException, NotFoundException;
    public void deleteTicket(Long id) throws NotFoundException;
}