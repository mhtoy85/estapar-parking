package com.estapar.parking.interfaces.controller;

import com.estapar.parking.interfaces.dto.request.WebhookEventRequest;
import com.estapar.parking.application.usecases.WebhookUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private final WebhookUseCase webhookUseCase;

    @PostMapping
    public ResponseEntity<Void> handleEvent(@RequestBody WebhookEventRequest event) {
        webhookUseCase.processEvent(event);
        return ResponseEntity.ok().build();
    }
}


