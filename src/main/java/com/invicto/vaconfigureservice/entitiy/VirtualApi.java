package com.invicto.vaconfigureservice.entitiy;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "VIRTUAL_API")
@Data
@Table(name = "VIRTUAL_API",
        indexes = {@Index(name = "primary_index", columnList = "VIRTUAL_API_ID", unique = true)})
public class VirtualApi {
    @Id
    @Column(name = "VIRTUAL_API_ID")
    private String virtualApiId;
    @Column(name = "VIRTUAL_API_NAME")
    private String virtualApiName;
    @ManyToOne(targetEntity = Project.class, fetch = FetchType.EAGER)
    private Project project;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @OneToOne(mappedBy = "virtualApi")
    private VirtualApiSpecs virtualApiSpecs;
}
