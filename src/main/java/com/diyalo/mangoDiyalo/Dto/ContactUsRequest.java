package com.diyalo.mangoDiyalo.Dto;

import com.diyalo.mangoDiyalo.Enums.CustomerEnquiryTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactUsRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    private String email;

    private String phoneNumber;

    @NotNull(message = "Inquiry subject is required")
    private CustomerEnquiryTypeEnum inquirySubject;

    private String organizationName;

    @NotBlank
    private String inquiryMessage;

}
