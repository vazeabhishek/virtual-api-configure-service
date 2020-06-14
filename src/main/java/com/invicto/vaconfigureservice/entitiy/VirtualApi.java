package com.invicto.vaconfigureservice.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "VIRTUAL_API")
@Data
@Table(name = "VIRTUAL_API",
        indexes = {@Index(name = "primary_index", columnList = "VIRTUAL_API_ID", unique = true)},
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"VIRTUAL_API_PATH", "PROJECT_ID"}))
public class VirtualApi {
    @Id
    @Column(name = "VIRTUAL_API_ID")
    private String virtualApiId;
    @Column(name = "VIRTUAL_API_NAME")
    private String virtualApiName;
    @Column(name = "VIRTUAL_API_PATH")
    private String virtualApiPath;
    @Column(name = "REQUEST_METHOD")
    private String requestMethod;
    @ManyToOne(targetEntity = Project.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "PROJECT_ID")
    private Project project;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @OneToMany(mappedBy = "virtualApi", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<VirtualApiSpecs> virtualApiSpecs;
}