package mainDirectory.Converters;

        import mainDirectory.database.model.Ticket;
        import mainDirectory.modelFX.TicketFX;

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
        return ticketFX;

    }



}
