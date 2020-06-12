package com.invicto.vaconfigureservice.entitiy;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "VIRTUAL_API_SPEC")
@Data
@Table(name = "VIRTUAL_API_SPEC")
public class VirtualApiSpecs {
    @Id
    @Column(name = "VIRTUAL_API_SPECS_ID")
    private String virtualApiId;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VIRTUAL_API_ID", nullable = false)
    private VirtualApi virtualApi;
    @Column(name = "REQUEST_METHOD")
    private String requestMethod;
    @Column(name = "REQUEST_PAYLOAD")
    private String requestPayload;
    @Column(name = "REQUEST_HEADERS")
    private String requestHeaders;
    @Column(name = "RESPONSE_PAYLOAD")
    private String responsePayload;
    @Column(name = "RESPONSE_CODE")
    private int responseCode;
    @Column(name = "RESPONSE_HEADERS")
    private String responseHeaders;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "CREATED_BY")
    private String createdBy;
}
