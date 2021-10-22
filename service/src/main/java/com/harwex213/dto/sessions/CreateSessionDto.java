package com.harwex213.dto.sessions;

import com.harwex213.entities.CinemaMovie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSessionDto {
    private CinemaMovie cinemaMovie;
    private LocalDateTime time;
    private Integer ticketsAmount;
    private Integer ticketsFree;
}
