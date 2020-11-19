package mainDirectory.utils;

import javafx.collections.ObservableList;
import mainDirectory.modelFX.TicketFX;

import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

public class Other {


    public static void sendEmail(TicketFX ticketFX, String topic, String text, boolean dateYes) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        String autor = "";
        String body = "";
        String subject = topic + " Nr Zgłoszenia: " + ticketFX.getIdProperty();
        if (dateYes) {
            body = text + " " + ticketFX.dataProperty().getValue();
        } else {
            body = text;
        }
        if (ticketFX.statusPropertyProperty().getValue().departamentFXProperty().getValue().equals("Planowanie")) {
            autor = ticketFX.plannerFXPropertyProperty().getValue().getEmailFX();
        }
        if (ticketFX.statusPropertyProperty().getValue().departamentFXProperty().getValue().equals("SCM")) {
            autor = ticketFX.scmerFXPropertyProperty().getValue().getEmailFX();
        }
        if (ticketFX.statusPropertyProperty().getValue().departamentFXProperty().getValue().equals("Zaopatrzenie")) {
            autor = ticketFX.buyerFXPropertyProperty().getValue().getEmailFX();
        }

        String message = String.format("mailto:%s?subject=%s&body=%s", autor, urlEncode(subject), urlEncode(body));
        URI uri = URI.create(message);
        desktop.mail(uri);
    }

    private static final String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendMultipleEmail(ObservableList<TicketFX> multipleTicketsList, String topic, String text, boolean dateYes) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        String autor = "";
        String body = text;
        String subject = topic;

        if (dateYes) {
            for (int i = 0; i < multipleTicketsList.size(); i++) {
                body = body + "\n Ticket id:"
                        + multipleTicketsList.get(i).getIdProperty()
                        + " w systemie od: "
                        + multipleTicketsList.get(i).getData();


            }
        } else {
            for (int i = 0; i < multipleTicketsList.size(); i++) {
                body = body + "\n Ticket id:"
                        + multipleTicketsList.get(i).getIdProperty();


            }

        }
        if (multipleTicketsList.get(0).statusPropertyProperty().getValue().departamentFXProperty().getValue().equals("Planowanie")) {
            autor = multipleTicketsList.get(0).plannerFXPropertyProperty().getValue().getEmailFX();
        }
        if (multipleTicketsList.get(0).statusPropertyProperty().getValue().departamentFXProperty().getValue().equals("SCM")) {
            autor = multipleTicketsList.get(0).scmerFXPropertyProperty().getValue().getEmailFX();
        }
        if (multipleTicketsList.get(0).statusPropertyProperty().getValue().departamentFXProperty().getValue().equals("Zaopatrzenie")) {
            autor = multipleTicketsList.get(0).buyerFXPropertyProperty().getValue().getEmailFX();
        }

        String message = String.format("mailto:%s?subject=%s&body=%s", autor, urlEncode(subject), urlEncode(body));
        URI uri = URI.create(message);
        desktop.mail(uri);


    }
}
