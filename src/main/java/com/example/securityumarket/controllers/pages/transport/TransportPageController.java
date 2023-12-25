package com.example.securityumarket.controllers.pages.transport;

import com.example.securityumarket.dto.pages.transport.TransportDetailsResponse;
import com.example.securityumarket.dto.transports.TransportDTO;
import com.example.securityumarket.services.pages.TransportPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transport")
@RequiredArgsConstructor
@Tag(name = "Transport page", description = "This controller contains transport page endpoints")
public class TransportPageController {

    private final TransportPageService transportPageService;

    @Operation(
            summary = "Get transport",
            description = "This endpoint returns a transport params by transport id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient Permissions", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
    })
    @GetMapping("/{transport-id}")
    public ResponseEntity<? extends TransportDTO> getTransport(@PathVariable("transport-id") Long transportId) {
        return transportPageService.getTransport(transportId);
    }

    @Operation(
            summary = "Get transport details: is transport favorite and count of transport views",
            description = "This endpoint returns a transport details by transport id."
    )
    @GetMapping("/details/{transport-id}")
    public ResponseEntity<TransportDetailsResponse> getTransportDetails(@PathVariable("transport-id") Long transportId) {
        return ResponseEntity.ok(transportPageService.getTransportDetails(transportId));
    }
}
