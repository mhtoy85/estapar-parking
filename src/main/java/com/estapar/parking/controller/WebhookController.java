package com.estapar.parking.controller;

import com.estapar.parking.dto.request.WebhookEventRequest;
import com.estapar.parking.service.WebhookService;
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

    private final WebhookService webhookService;

    @PostMapping
    public ResponseEntity<Void> handleEvent(@RequestBody WebhookEventRequest event) {
        webhookService.processEvent(event);
        return ResponseEntity.ok().build();
    }
}


