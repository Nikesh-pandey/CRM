package com.diyalo.mangoDiyalo.Controller;

import com.diyalo.mangoDiyalo.Dto.ContactUsRequest;
import com.diyalo.mangoDiyalo.Service.ContactUsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContactUsController {

    private final ContactUsService contactUsService;

    @PostMapping("/contact-us")
    public ResponseEntity<?> submitContactUs(@Valid @RequestBody ContactUsRequest request) {
        return contactUsService.saveContactUs(request);
    }
}
