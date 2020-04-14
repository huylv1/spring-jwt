package com.example.demo;

import java.util.List;

public interface TicketService {
    void addTicket(Integer creatorid, String content, Integer severity, Integer status);

    Ticket getMyTickets(Integer userid);

    Ticket getTicket(Integer ticketid);

    void updateTicket(Integer ticketid, String content, Integer severity, Integer status);

    void deleteMyTicket(Integer userid, Integer ticketid);

    List<Ticket> getAllTickets();

    void deleteTickets(User user, String ticketids);
}
