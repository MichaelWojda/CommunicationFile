package mainDirectory.Converters;

        import mainDirectory.database.model.Ticket;
        import mainDirectory.database.model.Ticket_History;
        import mainDirectory.modelFX.TicketFX;
        import mainDirectory.modelFX.TicketFX_History;

        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.Locale;

public class TicketConverter {
    public static Ticket convertToTicket(TicketFX ticketFX) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketFX.getIdProperty());
        ticket.setAuthor(PersonConverter.convertToPerson(ticketFX.getAuthorFXProperty()));
        ticket.setMaterialName(ticketFX.getMaterialNameProperty());
        ticket.setMaterialDescription(ticketFX.getMaterialDescriptionProperty());
        ticket.setProject(ticketFX.getProjectProperty());
        ticket.setNotes(ticketFX.getNotesProperty());
        ticket.setPlanner(PersonConverter.convertToPerson(ticketFX.getPlannerFXProperty()));
        ticket.setBuyer(PersonConverter.convertToPerson(ticketFX.getBuyerFXProperty()));
        ticket.setScmer(PersonConverter.convertToPerson(ticketFX.getScmerFXProperty()));
        ticket.setStatus(StatusConverter.convertToStatus(ticketFX.getStatusProperty()));
        ticket.setActive(ticketFX.getActiveProperty());
        return ticket;

    }

    public static TicketFX convertToTicketFX(Ticket ticket) {
        TicketFX ticketFX = new TicketFX();
        ticketFX.setIdProperty(ticket.getId());
        ticketFX.setAuthorFXProperty(PersonConverter.convertToPersonFX(ticket.getAuthor()));
        ticketFX.setMaterialNameProperty(ticket.getMaterialName());
        ticketFX.setMaterialDescriptionProperty(ticket.getMaterialDescription());
        ticketFX.setProjectProperty(ticket.getProject());
        ticketFX.setNotesProperty(ticket.getNotes());
        ticketFX.setPlannerFXProperty(PersonConverter.convertToPersonFX(ticket.getPlanner()));
        ticketFX.setBuyerFXProperty(PersonConverter.convertToPersonFX(ticket.getBuyer()));
        ticketFX.setScmerFXProperty(PersonConverter.convertToPersonFX(ticket.getScmer()));
        ticketFX.setStatusProperty(StatusConverter.convertToStatusFX(ticket.getStatus()));
        ticketFX.setActiveProperty(ticket.getActive());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm", Locale.ENGLISH);
        ticketFX.setData(dateFormat.format(ticket.getData()));
        return ticketFX;

    }

    public static Ticket_History convertToHistoryTicket (Ticket ticket){
        Ticket_History ticket_history = new Ticket_History();
        ticket_history.setId_ticket(ticket.getId());
        ticket_history.setMaterialName(ticket.getMaterialName());
        ticket_history.setAuthor(ticket.getAuthor());
        ticket_history.setMaterialDescription(ticket.getMaterialDescription());
        ticket_history.setProject(ticket.getProject());
        ticket_history.setBuyer(ticket.getBuyer());
        ticket_history.setPlanner(ticket.getPlanner());
        ticket_history.setScmer(ticket.getScmer());
        ticket_history.setStatus(ticket.getStatus());
        ticket_history.setNotes(ticket.getNotes());
        ticket_history.setActive(ticket.getActive());
        ticket_history.setData(new Date());
        return ticket_history;

    }


    public static TicketFX_History convertToHistoryTicketFX(Ticket_History t) {
        TicketFX_History ticketFX_history = new TicketFX_History();
        ticketFX_history.setId_ticketProperty(t.getId_ticket());
        ticketFX_history.setMaterialNameProperty(t.getMaterialName());
        ticketFX_history.setMaterialDescriptionProperty(t.getMaterialDescription());
        ticketFX_history.setAuthorFXProperty(PersonConverter.convertToPersonFX(t.getAuthor()));
        ticketFX_history.setProjectProperty(t.getProject());
        ticketFX_history.setBuyerFXProperty(PersonConverter.convertToPersonFX(t.getBuyer()));
        ticketFX_history.setPlannerFXProperty(PersonConverter.convertToPersonFX(t.getPlanner()));
        ticketFX_history.setScmerFXProperty(PersonConverter.convertToPersonFX(t.getScmer()));
        ticketFX_history.setStatusProperty(StatusConverter.convertToStatusFX(t.getStatus()));
        ticketFX_history.setNotesProperty(t.getNotes());
        ticketFX_history.setActiveProperty(t.getActive());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm", Locale.ENGLISH);
        ticketFX_history.setData(dateFormat.format(t.getData()));
        return ticketFX_history;

    }
}
