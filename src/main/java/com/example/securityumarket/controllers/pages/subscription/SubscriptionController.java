package com.example.securityumarket.controllers.pages.subscription;


import com.example.securityumarket.models.DTO.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.SubscriptionService;
import com.example.securityumarket.services.jpa.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscriptions")
@AllArgsConstructor
@Tag(name = "Subscription  page", description = "This controller contains subscription page endpoints")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<String> addSubscription(@ModelAttribute RequestSearchDTO requestSearchDTO) {
        Users authenticatedUser = userService.getAuthenticatedUser();
        subscriptionService.addSubscription(requestSearchDTO, authenticatedUser);
        return ResponseEntity.ok("Subscription added.");
    }

    @DeleteMapping("{subscription-id}")
    public ResponseEntity<String> deleteSubscription(@PathVariable("subscription-id") Long subscriptionId) {
        Users authenticatedUser = userService.getAuthenticatedUser();
        subscriptionService.deleteSubscription(subscriptionId, authenticatedUser);
        return ResponseEntity.ok("Subscription deleted.");
    }
}
