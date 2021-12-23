package com.harwex213.dto.cinemas;

import com.harwex213.dto.sessions.GetSessionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCinemaByDateMovieDto {

    @NotNull(message = "Can not be null")
    private Long id;

    @NotNull(message = "Can not be null")
    private String name;

    @NotNull(message = "Can not be null")
    private List<GetSessionDto> sessions= new ArrayList<>();
}
