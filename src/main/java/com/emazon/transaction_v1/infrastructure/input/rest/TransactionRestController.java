package com.emazon.transaction_v1.infrastructure.input.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.emazon.transaction_v1.infrastructure.input.rest.util.PathDefinition.*;

@RestController
@RequestMapping(TRANSACTIONS)
@RequiredArgsConstructor
@Validated
public class TransactionRestController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supply added", content = @Content),
            @ApiResponse(responseCode = "409", description = "Supply already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad supply request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @Operation(summary = "Add a new supply")
    @PostMapping(SUPPLY)
    public ResponseEntity<Void> addSupply() {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supply added", content = @Content),
            @ApiResponse(responseCode = "409", description = "Supply already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad supply request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @Operation(summary = "Add a new supply")
    @PostMapping(BUYS)
    public ResponseEntity<Void> buy() {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
