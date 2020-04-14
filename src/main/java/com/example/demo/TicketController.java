package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.demo.Util.getUserNotAvailableError;

@Controller
public class TicketController {

    @Autowired
    private UserService userSevice;

    @Autowired
    private TicketService ticketSevice;
    /*
     * Rule:
     * Only user can create a ticket
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @UserTokenRequired
    @RequestMapping(value = "", method = RequestMethod.POST)
    public <T> T addTicket(
            @RequestParam(value="content") String content,
            HttpServletRequest request
    ) {
        User user = userSevice.getUserByToken(request.getHeader("token"));
        ticketSevice.addTicket(user.getUserid(), content, 2, 1);
        return Util.getSuccessResult();
    }

    @ResponseBody
    @RequestMapping("/my/tickets")
    public Map<String, Object> getMyTickets(
            HttpServletRequest request
    ) {
        User user = userSevice.getUserByToken(request.getHeader("token"));
        if(user == null){
            return getUserNotAvailableError();
        }
        return Util.getSuccessResult(ticketSevice.getMyTickets(user.getUserid()));
    }

    @ResponseBody
    @TokenRequired
    @RequestMapping("/{ticketid}")
    public <T> T getTicket(
            @PathVariable("ticketid") final Integer ticketid,
            HttpServletRequest request
    ) {

        return (T) Util.getSuccessResult(ticketSevice.getTicket(ticketid));
    }

    @ResponseBody
    @RequestMapping(value = "/{ticketid}", method = RequestMethod.PUT)
    public <T> T updateTicketByCustomer (
            @PathVariable("ticketid") final Integer ticketid,
            @RequestParam(value="content") String content,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        User user = userSevice.getUserByToken(request.getHeader("token"));
        if(user == null){
            return getUserNotAvailableError();
        }
        ticketSevice.updateTicket(ticketid, content, 2, 1);
        Map<String, String> result = new LinkedHashMap<>();
        result.put("result", "updated");
        return (T) result;
    }

    @ResponseBody
    @UserTokenRequired
    @RequestMapping(value = "/{ticketid}", method = RequestMethod.DELETE)
    public <T> T deleteTicketByUser (
            @PathVariable("ticketid") final Integer ticketid,
            HttpServletRequest request
    ) {
        User user = userSevice.getUserByToken(request.getHeader("token"));
        ticketSevice.deleteMyTicket(user.getUserid(), ticketid);
        return Util.getSuccessResult();
    }

    @ResponseBody
    @AdminTokenRequired
    @RequestMapping("/by/admin")
    public <T> T getAllTickets(
            HttpServletRequest request,
            HttpServletResponse response) {

        return (T) ticketSevice.getAllTickets();
    }

    @ResponseBody
    @RequestMapping(value = "/by/admin", method = RequestMethod.PUT)
    public <T> T updateTicketByAdmin (
            @RequestParam("ticketid") final Integer ticketid,
            @RequestParam(value="content") String content,
            @RequestParam(value="severity") Integer severity,
            @RequestParam(value="status") Integer status,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        User user = userSevice.getUserByToken(request.getHeader("token"));
        if(user == null){
            return getUserNotAvailableError();
        }
        ticketSevice.updateTicket(ticketid, content, severity, status);
        Map<String, String> result = new LinkedHashMap<>();
        result.put("result", "updated");
        return (T) result;
    }

    @ResponseBody
    @AdminTokenRequired
    @RequestMapping(value = "/by/admin", method = RequestMethod.DELETE)
    public <T> T deleteTicketsByAdmin (
            @RequestParam("ticketids") final String ticketids,
            HttpServletRequest request
    )  {

        User user = userSevice.getUserByToken(request.getHeader("token"));

        ticketSevice.deleteTickets(user, ticketids);

        return Util.getSuccessResult();
    }

    @ResponseBody
    @CSRTokenRequired
    @RequestMapping(value = "/by/csr", method = RequestMethod.PUT)
    public <T> T updateTicketByCSR (
            @RequestParam("ticketid") final Integer ticketid,
            @RequestParam(value="content") String content,
            @RequestParam(value="severity") Integer severity,
            @RequestParam(value="status") Integer status,
            HttpServletRequest request
    ) {
        ticketSevice.updateTicket(ticketid, content, severity, status);
        return Util.getSuccessResult();
    }

    @ResponseBody
    @CSRTokenRequired
    @RequestMapping("/by/csr")
    public <T> T getAllTicketsByCSR(HttpServletRequest request) {
        return (T) ticketSevice.getAllTickets();
    }

    @ResponseBody
    @CSRTokenRequired
    @RequestMapping(value = "/by/csr", method = RequestMethod.DELETE)
    public <T> T deleteTicketsByCSR (
            @RequestParam("ticketids") final String ticketids,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        User user = userSevice.getUserByToken(request.getHeader("token"));
        ticketSevice.deleteTickets(user, ticketids);
        Map<String, String> result = new LinkedHashMap<>();
        result.put("result", "deleted");
        return (T) result;
    }
}
