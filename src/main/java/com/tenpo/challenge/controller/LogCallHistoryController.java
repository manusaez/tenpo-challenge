package com.tenpo.challenge.controller;

import com.tenpo.challenge.dto.LogCallHistoryDto;
import com.tenpo.challenge.service.LogCallHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LogCallHistoryController {

    private final LogCallHistoryService logCallHistoryService;

    @Operation(summary = "Get the list of all stored logs, paginable")
    @ApiResponse(responseCode = "200", description = "List obtained correctly.")
    @ApiResponse(responseCode = "500", description = "Problem at the server.")
    @GetMapping("/call-history")
    public ResponseEntity<Flux<LogCallHistoryDto>> getAll(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok().body(logCallHistoryService.getAll(PageRequest.of(page, size)));
    }
}
