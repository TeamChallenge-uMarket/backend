package com.example.securityumarket.controllers.pages;

import com.example.securityumarket.dto.pages.hidden.response.HiddenTransportResponse;
import com.example.securityumarket.dto.pages.hidden.response.HiddenUserResponse;
import com.example.securityumarket.services.pages.HiddenTransportPageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-page")
@AllArgsConstructor
@Tag(name = "Hidden transport page",
        description = "This controller contains the hidden transport page endpoints")
public class HiddenTransportController {

    private final HiddenTransportPageService hiddenTransportPageService;

    @GetMapping("/hidden/transports")
    public ResponseEntity<List<HiddenTransportResponse>> getAllHiddenTransport(){
        return ResponseEntity.ok(hiddenTransportPageService.getAllHiddenTransport());
    }

    @GetMapping("/hidden/users")
    public ResponseEntity<List<HiddenUserResponse>> getAllHiddenUsers(){
        return ResponseEntity.ok(hiddenTransportPageService.getAllHiddenUsers());
    }

    @PutMapping("/hide/transport/{transport-id}")
    public ResponseEntity<String> hideTransport(
            @PathVariable("transport-id") Long transportId) {
        hiddenTransportPageService.hideTransport(transportId);
        return ResponseEntity.ok("Transport successfully hidden");
    }

    @PutMapping("/unhide/transport/{transport-id}")
    public ResponseEntity<String> unhideTransport(
            @PathVariable("transport-id") Long transportId) {
        hiddenTransportPageService.unhideTransport(transportId);
        return ResponseEntity.ok("Transport successfully unhidden");
    }

    @PutMapping("/hide-all/transport/{transport-id}")
    public ResponseEntity<String> hideAllTransport(
            @PathVariable("transport-id") Long transportId) {
        hiddenTransportPageService.hideAllTransport(transportId);
        return ResponseEntity.ok("All transports successfully hidden");
    }

    @PutMapping("/unhide-all/transport/{transport-id}")
    public ResponseEntity<String> unhideAllTransport(
            @PathVariable("transport-id") Long transportId) {
        hiddenTransportPageService.unhideAllTransport(transportId);
        return ResponseEntity.ok("All transports successfully unhidden");
    }

    @PutMapping("/hide-all/user/{user-id}")
    public ResponseEntity<String> hideAllTransportByUser(
            @PathVariable("user-id") Long userId) {
        hiddenTransportPageService.hideAllTransportByUser(userId);
        return ResponseEntity.ok("All transports by User successfully hidden");
    }

    @PutMapping("/unhide-all/user/{user-id}")
    public ResponseEntity<String> unhideAllTransportByUserId(
            @PathVariable("user-id") Long userId) {
        hiddenTransportPageService.unhideAllTransportByUserId(userId);
        return ResponseEntity.ok("All transports by User successfully unhidden");
    }
}
