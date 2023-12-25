package com.example.securityumarket.controllers.pages.subscription;


import com.example.securityumarket.dto.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.dto.pages.subscription.SubscriptionRequest;
import com.example.securityumarket.dto.pages.subscription.SubscriptionResponse;
import com.example.securityumarket.models.Subscription;
import com.example.securityumarket.services.notification.Observed;
import com.example.securityumarket.services.pages.SubscriptionPageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions")
@AllArgsConstructor
@Tag(name = "Subscription  page", description = "This controller contains subscription page endpoints")
public class SubscriptionController {

    private final SubscriptionPageService subscriptionPageService;

    @GetMapping
    public ResponseEntity<List<SubscriptionResponse>> getSubscriptions(){
        return ResponseEntity.ok(subscriptionPageService.getSubscriptions());
    }

    @PostMapping()
    public ResponseEntity<String> addSubscription(@ModelAttribute RequestSearchDTO requestSearchDTO,
                                                  @RequestBody SubscriptionRequest subscriptionRequest) {
        subscriptionPageService.addSubscription(requestSearchDTO, subscriptionRequest);
        return ResponseEntity.ok("Subscription added.");
    }

    @DeleteMapping("{subscription-id}")
    public ResponseEntity<String> deleteSubscription(@PathVariable("subscription-id") Long subscriptionId) {
        subscriptionPageService.removeSubscription(subscriptionId);
        return ResponseEntity.ok("Subscription deleted.");
    }
}
