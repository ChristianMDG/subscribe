package com.subscribe.demo.service.event;

import com.subscribe.demo.endpoint.event.model.SubscriptionCreatedEvent;
import com.subscribe.demo.file.bucket.BucketComponent;
import com.subscribe.demo.mail.Email;
import com.subscribe.demo.mail.Mailer;
import com.subscribe.demo.subscribe.entity.Subscription;
import com.subscribe.demo.subscribe.entity.SubscriptionStatus;
import com.subscribe.demo.subscribe.repository.SubscriptionRepository;
import com.subscribe.demo.subscribe.service.TicketPdfService;
import jakarta.mail.internet.InternetAddress;
import java.io.File;
import java.time.Duration;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionCreatedEventService implements Consumer<SubscriptionCreatedEvent> {

  private static final DateTimeFormatter DATE_FORMATTER =
      DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault());

  private final TicketPdfService ticketPdfService;
  private final BucketComponent bucketComponent;
  private final Mailer mailer;
  private final SubscriptionRepository subscriptionRepository;

  @SneakyThrows
  @Override
  public void accept(SubscriptionCreatedEvent event) {

    // 1. Génération du PDF
    String participantFullName = event.getUserFirstName() + " " + event.getUserLastName();
    File ticketFile =
        ticketPdfService.generateTicket(
            participantFullName,
            event.getCourseTitle(),
            event.getCourseStartDate(),
            event.getCourseEndDate());

    // 2. Upload sur S3
    String bucketKey = "tickets/" + event.getSubscriptionId() + ".pdf";
    bucketComponent.upload(ticketFile, bucketKey);

    // 3. URL présignée, valable 7 jours
    String downloadUrl = bucketComponent.presign(bucketKey, Duration.ofDays(7)).toString();

    // 4. Email de confirmation
    String subject = "Confirmation d'inscription - Téléchargez votre ticket";
    String body = buildEmailBody(event, downloadUrl);
    mailer.accept(
        new Email(
            new InternetAddress(event.getUserEmail()),
            List.of(),
            List.of(),
            subject,
            body,
            List.of()));

    // 5. Mise à jour du statut de la Subscription
    Subscription subscription =
        subscriptionRepository
            .findById(event.getSubscriptionId())
            .orElseThrow(
                () ->
                    new IllegalStateException(
                        "Subscription introuvable lors du traitement asynchrone: "
                            + event.getSubscriptionId()));
    subscription.setStatus(SubscriptionStatus.CONFIRMED);
    subscription.setTicketUrl(downloadUrl);
    subscriptionRepository.save(subscription);
  }

  private String buildEmailBody(SubscriptionCreatedEvent event, String downloadUrl) {
    return """
           Bonjour %s %s,

           Vous êtes bien inscrit au cours : %s

           Détails du cours :
           - Titre : %s
           - Date de début : %s
           - Date de fin : %s

           📄 Votre ticket est disponible ici : %s
           (Lien valable 7 jours)

           Merci de votre inscription.

           Cordialement,
           L'équipe de formation
           """
        .formatted(
            event.getUserFirstName(),
            event.getUserLastName(),
            event.getCourseTitle(),
            event.getCourseTitle(),
            DATE_FORMATTER.format(event.getCourseStartDate()),
            DATE_FORMATTER.format(event.getCourseEndDate()),
            downloadUrl);
  }
}
