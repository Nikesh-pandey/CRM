package com.diyalo.mangoDiyalo.Service;

import com.diyalo.mangoDiyalo.Dto.ApiResponse;
import com.diyalo.mangoDiyalo.Dto.ContactUsRequest;
import com.diyalo.mangoDiyalo.Entities.CustomerContactUs;
import com.diyalo.mangoDiyalo.Repository.CustomerContactUsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactUsService {

    private final CustomerContactUsRepository customerContactUsRepository;

    public ResponseEntity<?> saveContactUs(ContactUsRequest request) {
        CustomerContactUs contactUs = CustomerContactUs.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .inquirySubject(request.getInquirySubject())
                .organizationName(request.getOrganizationName())
                .inquiryMessage(request.getInquiryMessage())
                .build();

        customerContactUsRepository.save(contactUs);

        return ApiResponse.created("Your inquiry has been submitted successfully", null);
    }
}

