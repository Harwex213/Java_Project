package com.harwex213.dto.cinemas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCinemaDto {
    private Long id;
    private String name;
    private String description;
    private String address;
}
