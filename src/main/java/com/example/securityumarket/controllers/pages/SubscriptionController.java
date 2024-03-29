package com.example.securityumarket.controllers.pages;


import com.example.securityumarket.dto.pages.catalog.request.RequestSearch;
import com.example.securityumarket.dto.pages.subscription.SubscriptionRequest;
import com.example.securityumarket.dto.pages.subscription.SubscriptionResponse;
import com.example.securityumarket.dto.pages.subscription.SubscriptionTransportsResponse;
import com.example.securityumarket.services.pages.SubscriptionPageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("{subscription-id}")
    public ResponseEntity<SubscriptionTransportsResponse> getSubscription(
            @PathVariable("subscription-id") Long subscriptionId){
        return ResponseEntity.ok(subscriptionPageService.getSubscription(subscriptionId));
    }

    @PostMapping()
    public ResponseEntity<String> addSubscription(@ModelAttribute RequestSearch requestSearch,
                                                  @RequestBody SubscriptionRequest subscriptionRequest) {
        subscriptionPageService.addSubscription(requestSearch, subscriptionRequest);
        return ResponseEntity.ok("Subscription added.");
    }

    @DeleteMapping("{subscription-id}")
    public ResponseEntity<String> deleteSubscription(@PathVariable("subscription-id") Long subscriptionId) {
        subscriptionPageService.removeSubscription(subscriptionId);
        return ResponseEntity.ok("Subscription deleted.");
    }

    @PutMapping("{subscription-id}")
    public ResponseEntity<String> updateSubscription(@PathVariable("subscription-id") Long subscriptionId,
                                                     @ModelAttribute RequestSearch requestSearch) {
        subscriptionPageService.updateSubscriptionParameters(subscriptionId, requestSearch);
        return ResponseEntity.ok("Subscription updated.");
    }

    @PatchMapping("{subscription-id}")
    public ResponseEntity<String> updateSubscriptionParameters(@PathVariable("subscription-id") Long subscriptionId,
                                                               @ModelAttribute SubscriptionRequest subscriptionRequest) {
        subscriptionPageService.updateSubscription(subscriptionId, subscriptionRequest);
        return ResponseEntity.ok("Subscription updated.");
    }
}
