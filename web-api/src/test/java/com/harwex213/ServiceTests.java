package com.harwex213;

import com.harwex213.dto.tickets.CreateTicketDto;
import com.harwex213.dto.users.AuthenticateUserDto;
import com.harwex213.exceptions.BadRequestException;
import com.harwex213.exceptions.NotFoundException;
import com.harwex213.exceptions.UnauthenticatedException;
import com.harwex213.interfaces.IAuthenticationService;
import com.harwex213.interfaces.ITicketService;
import com.harwex213.interfaces.IUserService;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ServiceTests {

    @Autowired
    IUserService iUserService;

    @Autowired
    IAuthenticationService iAuthService;

    @Autowired
    ITicketService iTicketService;

    @Test
    public void isTokenFromLoginValid() throws UnauthenticatedException {
        var user = new AuthenticateUserDto();
        user.setUsername("Harwex213");
        user.setPassword("1111");

        var response = iAuthService.login(user);

        iAuthService.getUserByToken(response.getAccessToken());
    }

    @Test(expected = BadRequestException.class)
    public void isOnlyOneTicketPossible() throws NotFoundException, BadRequestException {
        var ticket = new CreateTicketDto();
        ticket.setSessionId(1L);
        ticket.setUserId(1L);

        iTicketService.createTicket(ticket);
        iTicketService.createTicket(ticket);
    }

    @Test(expected = NotFoundException.class)
    public void getUserByLoginAndPassword() throws NotFoundException {
        iUserService.getUser(9999L);
    }
}
