package com.harwex213.repositories;

import com.harwex213.entities.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISessionRepository extends CrudRepository<Session, Long> {
    List<Session> findAll();
}
