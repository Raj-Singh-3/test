package com.rs.portfolio.controller;

import com.rs.portfolio.dto.ContactRequest;
import com.rs.portfolio.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin("http://localhost:5173")
public class ContactController {

    private final MailService mailService;

    public ContactController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping
    public ResponseEntity<String> send(@RequestBody ContactRequest request) {

        mailService.sendVerificationMail(request);

        return ResponseEntity.ok("Verification Email Sent");

    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String token) {

        return ResponseEntity.ok(mailService.verify(token));

    }

}