package com.harwex213.dto.tickets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTicketDto {

    @NotNull(message = "Can not be null")
    private Long id;

    @NotNull(message = "Can not be null")
    private Long sessionId;

    @NotNull(message = "Can not be null")
    private Long userId;
}
