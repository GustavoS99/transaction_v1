package com.emazon.transaction_v1.infrastructure.input.rest;

import com.emazon.transaction_v1.application.dto.SupplyRequest;
import com.emazon.transaction_v1.application.handler.ISupplyHandler;
import com.emazon.transaction_v1.infrastructure.input.rest.util.PathDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.emazon.transaction_v1.infrastructure.input.rest.util.PathDefinition.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(SUPPLY)
public class SupplyRestController {

    private final ISupplyHandler supplyHandler;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supply added", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad supply request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Supply already exists", content = @Content)
    })
    @Operation(summary = "Add a new supply")
    @PostMapping(ADD)
    public ResponseEntity<Void> add(@RequestBody SupplyRequest supplyRequest) {
        supplyHandler.addSupply(supplyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
