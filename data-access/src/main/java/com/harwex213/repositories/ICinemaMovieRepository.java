package com.harwex213.repositories;

import com.harwex213.entities.CinemaMovie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICinemaMovieRepository extends CrudRepository<CinemaMovie, Long> {
    List<CinemaMovie> findAll();
}
