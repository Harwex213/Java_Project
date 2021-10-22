package com.harwex213.dto.sessions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSessionDto {
    private Long id;
    private Long cinemaMovieId;
    private LocalDateTime time;
    private Integer ticketsAmount;
}
