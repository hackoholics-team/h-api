package app.hackoholics.api.endpoint.controller;

import app.hackoholics.api.endpoint.mapper.SubscriptionMapper;
import app.hackoholics.api.endpoint.rest.model.Subscription;
import app.hackoholics.api.service.SubscriptionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class SubscriptionController {
  private final SubscriptionService service;
  private final SubscriptionMapper subscriptionMapper;

  @PutMapping("/users/{uId}/subscriptions")
  public Subscription crupdateSubscription(
      @PathVariable("uId") String uId, @RequestBody Subscription subscription) {
    return subscriptionMapper.toRest(service.crupdate(subscriptionMapper.toDomain(subscription)));
  }

  @GetMapping("/users/{uId}/subscriptions")
  public Subscription getSubscription(@PathVariable("uId") String uId) {
    var subscription = service.getByUserId(uId);
    return subscription == null ? null : subscriptionMapper.toRest(subscription);
  }
}
