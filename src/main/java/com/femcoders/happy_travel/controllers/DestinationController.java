package com.femcoders.happy_travel.controllers;

import com.femcoders.happy_travel.dtos.destination.DestinationRequest;
import com.femcoders.happy_travel.dtos.destination.DestinationResponse;
import com.femcoders.happy_travel.models.User;
import com.femcoders.happy_travel.security.UserDetailsImpl;
import com.femcoders.happy_travel.services.DestinationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/destinations")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/user")
    public ResponseEntity<List<DestinationResponse>> getDestinationsByAuthenticatedUser(Authentication authentication) {
        String username = authentication.getName();
        List<DestinationResponse> destinations = destinationService.getDestinationsByUsername(username);
        return ResponseEntity.ok(destinations);
    }


    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DestinationResponse>> getDestinationsByUser(
            @PathVariable Long userId,
            Authentication authentication
    ) {
        // Extract username from authenticated principal
        String loggedInUsername;
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            loggedInUsername = ((UserDetails) principal).getUsername();
        } else {
            loggedInUsername = principal.toString();
        }

        // Call your service to check if loggedInUsername matches userId's owner or do the validation here
        // Assuming destinationService has a method to get username by userId (or adapt accordingly)

        boolean isOwner = destinationService.isUserOwner(userId, loggedInUsername);
        if (!isOwner) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<DestinationResponse> destinations = destinationService.getDestinationsByUserId(userId);
        return ResponseEntity.ok(destinations);
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping ("/user/userId")
    public ResponseEntity<DestinationResponse> createDestination(
            @AuthenticationPrincipal UserDetailsImpl currentUserDetails,
            @RequestBody DestinationRequest destinationRequest
    ) {
        User authenticatedUser = currentUserDetails.getUser();
        Long userId = authenticatedUser.getId();
        DestinationResponse response = destinationService.createDestination(userId, destinationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DestinationResponse>> getAllDestinations() {
        return ResponseEntity.ok(destinationService.getAllDestinations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationResponse> getDestinationById(@PathVariable Long id) {
        return ResponseEntity.ok(destinationService.getDestinationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinationResponse> updateDestination(
            @PathVariable Long id,
            @ModelAttribute DestinationRequest request
    ) {
        return ResponseEntity.ok(destinationService.updateDestination(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.noContent().build();
    }
}