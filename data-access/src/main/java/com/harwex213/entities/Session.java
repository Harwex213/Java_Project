package com.harwex213.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private CinemaMovie cinemaMovie;

    @Column
    @NotNull(message = "Can not be null")
    private LocalDateTime time;

    @Column
    @Range(min = 0, message = "Can not be less than 0")
    @NotNull(message = "Can not be null")
    private Integer ticketsAmount;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Ticket> ticketList = new ArrayList<>();
}
