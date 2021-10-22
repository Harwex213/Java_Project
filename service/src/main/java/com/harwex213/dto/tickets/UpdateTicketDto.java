package com.harwex213.dto.tickets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTicketDto {
    private Long id;
    private Long sessionId;
    private Long userId;
}
