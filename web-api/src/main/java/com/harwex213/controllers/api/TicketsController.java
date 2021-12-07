package com.harwex213.controllers.api;

import com.harwex213.dto.tickets.CreateTicketDto;
import com.harwex213.dto.tickets.GetCreateTicketDto;
import com.harwex213.dto.tickets.GetTicketDto;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.exceptions.UnauthenticatedException;
import com.harwex213.interfaces.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketsController {
    private final ITicketService iTicketService;

    @Autowired
    public TicketsController(ITicketService iTicketService) {
        this.iTicketService = iTicketService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GetTicketDto> getTickets(@RequestHeader (name="Authorization") String authHeader) throws UnauthenticatedException
    {
        var token = authHeader.split(" ")[1].trim();
        return iTicketService.getTicketsByToken(token);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetCreateTicketDto createTicket(@Valid @RequestBody CreateTicketDto createFilmDto)
            throws BadRequestException, NotFoundException {
        return iTicketService.createTicket(createFilmDto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTicket(@PathVariable("id") Long id) throws NotFoundException {
        iTicketService.deleteTicket(id);
    }
}
