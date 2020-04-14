package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private List<Ticket> tickets = new ArrayList<>();

    @Override
    public void addTicket(Integer creatorid, String content, Integer severity, Integer status) {
        Ticket ticket = new Ticket(creatorid, new Date(), content, severity, status);
//        tickets.add(ticket);
    }

    @Override
    public Ticket getMyTickets(Integer userid) {
        return null;
    }

    @Override
    public Ticket getTicket(Integer ticketid) {
        return null;
    }

    @Override
    public void updateTicket(Integer ticketid, String content, Integer severity, Integer status) {
        Ticket ticket = getTicket(ticketid);
        if(ticket == null){
            throw new RuntimeException("Ticket Not Available");
        }
        ticket.setContent(content);
        ticket.setSeverity(severity);
        ticket.setStatus(status);
    }

    @Override
    public void deleteMyTicket(Integer userid, Integer ticketid) {
        tickets.removeIf(x -> x.getTicketid().intValue() == ticketid.intValue() && x.getCreatorid().intValue() == userid.intValue());
    }

    @Override
    public List<Ticket> getAllTickets() {
        return tickets;
    }

    @Override
    public void deleteTickets(User user, String ticketids) {
        List<String> ticketObjList = Arrays.asList(ticketids.split(","));
        if(user.getUsertype() == 2 && ticketObjList.size() > 3){
            throw new RuntimeException("CSR can't delete more than 3 tickets");
        }
        List<Integer> intList =
                ticketObjList.stream()
                        .map(Integer::valueOf)
                        .collect(Collectors.toList())
                ;
        tickets.removeIf(x -> intList.contains(x.getTicketid()));
    }
}
