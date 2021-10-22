package com.harwex213.dto.sessions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSessionDto {

    @NotNull(message = "Can not be null")
    private Long id;

    @NotNull(message = "Can not be null")
    private Long cinemaMovieId;

    @NotNull(message = "Can not be null")
    private LocalDateTime time;

    @Min(value = 0, message = "Can not be less than 0")
    @NotNull(message = "Can not be null")
    private Integer ticketsAmount;
}
