package org.hits.backend.hackaton.rest.performers_email;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/performer/emails")
public class PerformesEmailController {

    @GetMapping
    public ResponseEntity<List<String>> getEmails() {
        return ResponseEntity.ok(List.of("inostaziss@gmail.com"));
    }
}
