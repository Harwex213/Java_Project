package com.harwex213.dto.cinemas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCinemaDto {
    private Long id;
    private String name;
    private String description;
    private String address;
}
