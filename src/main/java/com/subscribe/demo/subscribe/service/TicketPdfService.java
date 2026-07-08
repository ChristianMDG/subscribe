package com.subscribe.demo.subscribe.service;


import java.io.File;
import java.io.FileOutputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.SneakyThrows;
import org.apache.tika.metadata.Font;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.document.Document;

@Service
public class TicketPdfService {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault());

    @SneakyThrows
    public File generateTicket(
            String participantFullName,
            String courseTitle,
            java.time.Instant startDate,
            java.time.Instant endDate) {

        File file = File.createTempFile("ticket-", ".pdf");

        try (FileOutputStream out = new FileOutputStream(file)) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("TICKET D'INSCRIPTION", titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Participant : " + participantFullName, normalFont));
            document.add(new Paragraph("Cours : " + courseTitle, normalFont));
            document.add(new Paragraph("Date de début : " + DATE_FORMATTER.format(startDate), normalFont));
            document.add(new Paragraph("Date de fin : " + DATE_FORMATTER.format(endDate), normalFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Ce ticket est valable pour l'accès au cours.", normalFont));

            document.close();
        }

        return file;
    }
}