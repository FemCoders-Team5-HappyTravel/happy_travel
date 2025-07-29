    package com.femcoders.happy_travel.controllers;

    import com.femcoders.happy_travel.dtos.destination.DestinationRequest;
    import com.femcoders.happy_travel.dtos.destination.DestinationResponse;
    import com.femcoders.happy_travel.models.User;
    import com.femcoders.happy_travel.security.UserDetailsImpl;
    import com.femcoders.happy_travel.services.DestinationService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.Parameter;
    import io.swagger.v3.oas.annotations.Parameters;
    import io.swagger.v3.oas.annotations.responses.ApiResponse;
    import io.swagger.v3.oas.annotations.security.SecurityRequirement;
    import lombok.RequiredArgsConstructor;
    import org.springdoc.core.annotations.ParameterObject;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
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
    @SecurityRequirement(name = "bearerAuth")
    public class DestinationController {

        private final DestinationService destinationService;

        @Operation(summary = "Get destinations of the authenticated user")
        @ApiResponse(responseCode = "200", description = "List of destinations")
        @GetMapping("/user")
        public ResponseEntity<List<DestinationResponse>> getDestinationsByAuthenticatedUser(Authentication authentication) {
            String username = authentication.getName();
            List<DestinationResponse> destinations = destinationService.getDestinationsByUsername(username);
            return ResponseEntity.ok(destinations);
        }

        @Operation(summary = "Get destinations by specific user ID (owner-only access)")
        @ApiResponse(responseCode = "200", description = "List of destinations for given user ID")
        @ApiResponse(responseCode = "403", description = "Forbidden - not owner")
        @GetMapping("/user/{userId}")
        public ResponseEntity<List<DestinationResponse>> getDestinationsByUser(
                @Parameter(description = "User ID") @PathVariable Long userId,
                Authentication authentication
        ) {
            String loggedInUsername;
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                loggedInUsername = ((UserDetails) principal).getUsername();
            } else {
                loggedInUsername = principal.toString();
            }

            boolean isOwner = destinationService.isUserOwner(userId, loggedInUsername);
            if (!isOwner) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            List<DestinationResponse> destinations = destinationService.getDestinationsByUserId(userId);
            return ResponseEntity.ok(destinations);
        }

        @Operation(summary = "Create a new destination for the authenticated user")
        @ApiResponse(responseCode = "200", description = "Destination created")
        @PostMapping("/user/userId")
        @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
        public ResponseEntity<DestinationResponse> createDestination(
                @AuthenticationPrincipal UserDetailsImpl currentUserDetails,
                @RequestBody DestinationRequest destinationRequest
        ) {
            User authenticatedUser = currentUserDetails.getUser();
            Long userId = authenticatedUser.getId();
            DestinationResponse response = destinationService.createDestination(userId, destinationRequest);
            return ResponseEntity.ok(response);
        }

        @Operation(summary = "Get all destinations")
        @ApiResponse(responseCode = "200", description = "List of all destinations")
        @GetMapping
        public ResponseEntity<List<DestinationResponse>> getAllDestinations() {
            return ResponseEntity.ok(destinationService.getAllDestinations());
        }

        @Operation(summary = "Get a destination by ID")
        @ApiResponse(responseCode = "200", description = "Destination details")
        @ApiResponse(responseCode = "404", description = "Destination not found")
        @GetMapping("/:id")
        public ResponseEntity<DestinationResponse> getDestinationById(
                @Parameter(description = "Destination ID") @PathVariable Long id
        ) {
            return ResponseEntity.ok(destinationService.getDestinationById(id));
        }

        @Operation(summary = "Update an existing destination")
        @ApiResponse(responseCode = "200", description = "Destination updated")
        @PutMapping("/{id}")
        public ResponseEntity<DestinationResponse> updateDestination(
                @Parameter(description = "Destination ID") @PathVariable Long id,
                @ModelAttribute DestinationRequest request
        ) {
            return ResponseEntity.ok(destinationService.updateDestination(id, request));
        }

        @Operation(summary = "Delete a destination")
        @ApiResponse(responseCode = "204", description = "Destination deleted")
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteDestination(
                @Parameter(description = "Destination ID") @PathVariable Long id
        ) {
            destinationService.deleteDestination(id);
            return ResponseEntity.noContent().build();
        }

        @GetMapping("/page")
        @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
        @Operation(summary = "Get paginated destinations")
        @Parameters({
                @Parameter(name = "page", description = "Page number (0..N)", example = "0"),
                @Parameter(name = "size", description = "Page size", example = "10"),
                @Parameter(name = "sort", description = "Sort criteria: property,asc|desc", example = "name,asc")
        })
        public ResponseEntity<Page<DestinationResponse>> getDestinationsPage(@ParameterObject Pageable pageable) {
            return ResponseEntity.ok(destinationService.getDestinationsPage(pageable));
        }


    }
