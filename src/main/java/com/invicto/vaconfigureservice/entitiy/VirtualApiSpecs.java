package com.invicto.vaconfigureservice.entitiy;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "VIRTUAL_API_SPEC")
@Data
@Table(name = "VIRTUAL_API_SPEC",
        indexes = {@Index(name = "primary_index", columnList = "VIRTUAL_API_ID", unique = true)})
public class VirtualApiSpecs {
    @Id
    @Column(name = "VIRTUAL_API_ID")
    private String virtualApiId;
    @Column(name = "REQUEST_METHOD")
    private String requestMethod;
    @Column(name = "REQUEST_PAYLOAD")
    private String requestPayload;
    @Column(name = "RESPONSE_PAYLOAD")
    private String responsePayload;
    @Column(name = "RESPONSE_CODE")
    private int responseCode;
    @Column(name = "CREATED)DATE")
    private LocalDateTime createdDate;
    @Column(name = "CREATED_BY")
    private String createdBy;
}
