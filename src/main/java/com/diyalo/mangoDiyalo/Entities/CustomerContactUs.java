package com.diyalo.mangoDiyalo.Entities;

import com.diyalo.mangoDiyalo.Enums.CustomerEnquiryTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "customer_contact_us")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomerContactUs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column
    private CustomerEnquiryTypeEnum inquirySubject;

    @Column
    private String organizationName;

    @Column(nullable = false)
    private String inquiryMessage;

    @Column
    @CreationTimestamp
    private Date createdDate;

    @Builder.Default
    @Column
    private Boolean followUpStatus = false;

    @Column
    private Date followUpDate;

    @ManyToOne
    @JoinColumn(name = "followed_up_by", nullable = true)
    private User followedUpBy;

    @Builder.Default
    @Column
    private Boolean delFlg = false;
}
