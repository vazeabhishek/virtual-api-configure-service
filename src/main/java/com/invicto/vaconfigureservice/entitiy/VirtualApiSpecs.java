package com.invicto.vaconfigureservice.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "VIRTUAL_API_SPEC")
@Getter
@Setter
@Table(name = "VIRTUAL_API_SPEC")
public class VirtualApiSpecs {
    @Id
    @Column(name = "VIRTUAL_API_SPECS_ID")
    @GeneratedValue(generator = "api-specs-sequence-generator")
    @GenericGenerator(
            name = "api-specs-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "api_specs_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Long virtualApiSpecsId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private VirtualApi virtualApi;
    @Column(name = "REQUEST_PAYLOAD",columnDefinition="TEXT")
    private String requestPayload;
    @Column(name = "REQUEST_PAYLOAD_ORIGINAL",columnDefinition="TEXT")
    private String requestPayloadOriginal;
    @Column(name = "RESPONSE_PAYLOAD",columnDefinition="TEXT")
    private String responsePayload;
    @Column(name = "RESPONSE_CODE")
    private int responseCode;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "CREATED_BY")
    private String createdBy;

    @Override
    public String toString() {
        return "VirtualApiSpecs{" +
                "virtualApiId='" + virtualApiSpecsId + '\'' +
                ", requestPayload='" + requestPayload + '\'' +
                ", responsePayload='" + responsePayload + '\'' +
                ", responseCode=" + responseCode +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
