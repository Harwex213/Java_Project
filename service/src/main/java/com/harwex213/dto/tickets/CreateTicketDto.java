package com.harwex213.dto.tickets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketDto {

    @NotNull(message = "Can not be null")
    private Long sessionId;

    @NotNull(message = "Can not be null")
    private Long userId;
}
