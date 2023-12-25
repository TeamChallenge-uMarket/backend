package com.example.securityumarket.controllers.pages.subscription;


import com.example.securityumarket.dto.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.dto.pages.subscription.SubscriptionRequest;
import com.example.securityumarket.models.Subscription;
import com.example.securityumarket.services.notification.Observed;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscriptions")
@AllArgsConstructor
@Tag(name = "Subscription  page", description = "This controller contains subscription page endpoints")
public class SubscriptionController {

    private final Observed observed;

    @PostMapping()
    public ResponseEntity<String> addSubscription(@ModelAttribute RequestSearchDTO requestSearchDTO,
                                                  @RequestBody SubscriptionRequest subscriptionRequest) {
        observed.addSubscription(requestSearchDTO, subscriptionRequest);
        return ResponseEntity.ok("Subscription added.");
    }

    @DeleteMapping("{subscription-id}")
    public ResponseEntity<String> deleteSubscription(@PathVariable("subscription-id") Long subscriptionId) {
        observed.removeSubscription(subscriptionId);
        return ResponseEntity.ok("Subscription deleted.");
    }
}
