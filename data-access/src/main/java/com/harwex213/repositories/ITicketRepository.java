package com.harwex213.repositories;

import com.harwex213.entities.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITicketRepository extends CrudRepository<Ticket, Long> {
    List<Ticket> findAll();
}
