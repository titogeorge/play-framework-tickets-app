package services;

import helper.ds.MorphiaObject;
import model.Ticket;
import org.bson.types.ObjectId;
import play.mvc.Controller;

import java.util.List;

/**
 * Created by tito on 21/10/15.
 */
public class TicketService extends DataAccessServiceImpl<Ticket, String>{

    @Override
    public Ticket create(Ticket ticket){
        if(ticket.getAssign() == null || ticket.getAssign() == ""){
            ticket.setStatus("New");
        } else {
            ticket.setStatus("Open");
        }
        super.create(ticket);
        return ticket;
    }

    @Override
    public Class<Ticket> getEntityClass() {
        return Ticket.class;
    }
}
