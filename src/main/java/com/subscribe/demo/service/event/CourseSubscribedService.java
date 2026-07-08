package com.subscribe.demo.service.event;

import com.subscribe.demo.endpoint.event.model.CourseSubscribed;
import com.subscribe.demo.mail.Email;
import com.subscribe.demo.mail.Mailer;
import com.subscribe.demo.subscribe.entity.Subscription;
import com.subscribe.demo.subscribe.repository.SubscriptionRepository;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseSubscribedService implements Consumer<CourseSubscribed> {

  private final SubscriptionRepository subscriptionRepository;
  private final Mailer mailer;

  @SneakyThrows
  @Override
  public void accept(CourseSubscribed event) {
    Subscription subscription =
        subscriptionRepository.findById(event.getSubscriptionId()).orElseThrow();

    var email =
        new Email(
            new InternetAddress(subscription.getUser().getEmail()),
            List.of(),
            List.of(),
            "Confirmation d'inscription",
            "Bonjour "
                + subscription.getUser().getFirstName()
                + ", votre inscription au cours \""
                + subscription.getCourse().getTitle()
                + "\" est confirmée.",
            List.of());
    mailer.accept(email);
  }
}
