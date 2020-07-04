package com.invicto.vaconfigureservice.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long virtualApiId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private VirtualApi virtualApi;
    @Column(name = "REQUEST_PAYLOAD")
    private String requestPayload;
    @Column(name = "RESPONSE_PAYLOAD")
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
                "virtualApiId='" + virtualApiId + '\'' +
                ", requestPayload='" + requestPayload + '\'' +
                ", responsePayload='" + responsePayload + '\'' +
                ", responseCode=" + responseCode +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
