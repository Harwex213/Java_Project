package com.harwex213.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cinemaMovies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Cinema cinema;

    @ManyToOne()
    @JoinColumn
    private Movie movie;

    @OneToMany(mappedBy = "cinemaMovie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Session> sessions= new ArrayList<>();
}