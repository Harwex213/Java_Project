package com.harwex213.dto.tickets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTicketDto {
    private Long id;
    private Long sessionId;
    private Long userId;
}
