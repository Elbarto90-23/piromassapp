package com.postgresql.pirotecnicamassa.controller;


import com.postgresql.pirotecnicamassa.model.Ordine;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ordini")
@CrossOrigin("*") // Permette richieste dal frontend
public class OrdineController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/invia-email")
    public String inviaEmail(@RequestBody Ordine ordine, @RequestParam String emailUtente) {
        try {
            // Invia email all'amministratore
            MimeMessage messageAdmin = mailSender.createMimeMessage();
            MimeMessageHelper helperAdmin = new MimeMessageHelper(messageAdmin, true, "UTF-8");

            helperAdmin.setFrom("ecommerce@pirotecnicamassa.com");
            helperAdmin.setReplyTo(emailUtente);
            helperAdmin.setTo(ordine.getEmail());
            helperAdmin.setSubject("Nuovo Ordine Ricevuto");
            helperAdmin.setText(generaCorpoEmail(ordine, emailUtente), true);

            mailSender.send(messageAdmin);

            // Invia email di conferma all'utente
            inviaEmailConfermaUtente(ordine, emailUtente);

            return "Email inviata con successo!";
        } catch (MessagingException e) {
            return "Errore nell'invio dell'email: " + e.getMessage();
        }
    }

    private void inviaEmailConfermaUtente(Ordine ordine, String emailUtente) {
        try {
            MimeMessage messageUtente = mailSender.createMimeMessage();
            MimeMessageHelper helperUtente = new MimeMessageHelper(messageUtente, true, "UTF-8");

            helperUtente.setFrom("ecommerce@pirotecnicamassa.com");
            helperUtente.setTo(emailUtente);
            helperUtente.setSubject("Conferma Ricezione Ordine");
            helperUtente.setText(generaCorpoEmailConfermaUtente(ordine), true);

            mailSender.send(messageUtente);
        } catch (MessagingException e) {
            e.printStackTrace(); // Gestione dell'errore
        }
    }

    private String generaCorpoEmail(Ordine ordine, String emailUtente) {
        StringBuilder sb = new StringBuilder();
        double totaleSpesa = 0.0;

        sb.append("<h2>Nuovo Ordine Ricevuto</h2>");
        sb.append("<p>Abbiamo ricevuto il tuo ordine con successo.</p>");
        sb.append("<p><strong>Email dell'utente loggato:</strong> ").append(emailUtente).append("</p>");
        sb.append("<ul>");

        for (var prodotto : ordine.getProdotti()) {
            double subTotale = prodotto.getQuantita() * prodotto.getPrezzo();
            totaleSpesa += subTotale;

            sb.append("<li>")
                    .append(prodotto.getQuantita())
                    .append(" x ")
                    .append(prodotto.getNome())
                    .append(" - €")
                    .append(prodotto.getPrezzo())
                    .append(" (Totale: €")
                    .append(String.format("%.2f", subTotale))
                    .append(")</li>");
        }

        sb.append("</ul>");
        sb.append("<h3>Totale Ordine: €").append(String.format("%.2f", totaleSpesa)).append("</h3>");
        sb.append("<p>Grazie per aver acquistato con noi!</p>");
        return sb.toString();
    }

    private String generaCorpoEmailConfermaUtente(Ordine ordine) {
        StringBuilder sb = new StringBuilder();
        double totaleSpesa = 0.0;

        sb.append("<h2>Conferma Ricezione Ordine</h2>");
        sb.append("<p>Gentile cliente, abbiamo ricevuto il tuo ordine con successo.</p>");
        sb.append("<p>Il tuo ordine sarà elaborato dal nostro servizio entro 48 ore lavorative.</p>");
        sb.append("<h3>Dettagli dell'ordine:</h3>");
        sb.append("<ul>");

        for (var prodotto : ordine.getProdotti()) {
            double subTotale = prodotto.getQuantita() * prodotto.getPrezzo();
            totaleSpesa += subTotale;

            sb.append("<li>")
                    .append(prodotto.getQuantita())
                    .append(" x ")
                    .append(prodotto.getNome())
                    .append(" - €")
                    .append(prodotto.getPrezzo())
                    .append(" (Totale: €")
                    .append(String.format("%.2f", subTotale))
                    .append(")</li>");
        }

        sb.append("</ul>");
        sb.append("<h3>Totale Ordine: €").append(String.format("%.2f", totaleSpesa)).append("</h3>");
        sb.append("<p>Grazie per aver acquistato con noi!</p>");
        return sb.toString();
    }
}