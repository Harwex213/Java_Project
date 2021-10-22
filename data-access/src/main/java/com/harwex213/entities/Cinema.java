package com.harwex213.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cinemas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(min = 2, max = 16, message = "name from 2 to 16")
    @NotNull(message = "Can not be null")
    private String name;

    @Column
    @Size(min = 3, message = "description from 3 to inf")
    @NotNull(message = "Can not be null")
    private String description;

    @Column
    @Size(min = 3, message = "description from 3 to inf")
    @NotNull(message = "Can not be null")
    private String address;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CinemaMovie> cinemaMovies = new ArrayList<>();;
}
