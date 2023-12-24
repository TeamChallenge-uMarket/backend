package com.example.securityumarket.controllers.pages.subscription;


import com.example.securityumarket.models.DTO.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.services.notification.Observed;
import com.example.securityumarket.services.pages.SubscriptionPageService;
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
    public ResponseEntity<String> addSubscription(@ModelAttribute RequestSearchDTO requestSearchDTO) {
        observed.addSubscription(requestSearchDTO);
        return ResponseEntity.ok("Subscription added.");
    }

    @DeleteMapping("{subscription-id}")
    public ResponseEntity<String> deleteSubscription(@PathVariable("subscription-id") Long subscriptionId) {
        observed.removeSubscription(subscriptionId);
        return ResponseEntity.ok("Subscription deleted.");
    }
}
