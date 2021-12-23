package com.harwex213.repositories;

import com.harwex213.entities.Cinema;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICinemaRepository extends CrudRepository<Cinema, Long> {
    List<Cinema> findAll();
}
