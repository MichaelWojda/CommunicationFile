package mainDirectory.utils;

import mainDirectory.modelFX.TicketFX;

import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

public class Other {


    public static void sendEmail(TicketFX ticketFX) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        String autor="";
        String subject ="PLIK KOMUNIKACJI PRZYPOMNIENIE TICKET NR "+ticketFX.getIdProperty();
        String body ="Uwaga \n Powyższy ticket oczekuje na Twoje działanie od "+ticketFX.dataProperty().getValue();
        if(ticketFX.statusPropertyProperty().getValue().departamentFXProperty().getValue().equals("Planowanie")){
           autor = ticketFX.plannerFXPropertyProperty().getValue().getEmailFX();
        }
        if(ticketFX.statusPropertyProperty().getValue().departamentFXProperty().getValue().equals("SCM")){
            autor = ticketFX.scmerFXPropertyProperty().getValue().getEmailFX();
        }
        if(ticketFX.statusPropertyProperty().getValue().departamentFXProperty().getValue().equals("Zaopatrzenie")){
            autor = ticketFX.buyerFXPropertyProperty().getValue().getEmailFX();
        }

        String message = String.format("mailto:%s?subject=%s&body=%s",autor,urlEncode(subject),urlEncode(body));
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
}
