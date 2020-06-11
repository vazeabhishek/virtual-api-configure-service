package com.invicto.vaconfigureservice.entitiy;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "PROJECT")
@Data
@Table(name = "PROJECT",
        indexes = {@Index(name = "primary_index", columnList = "PROJECT_ID", unique = true)})
public class Project {
    @Id
    @Column(name = "PROJECT_ID")
    private int projectId;
    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.EAGER)
    private Organization organization;
    @Column(name = "PROJECT_NAME")
    private String projectName;
    @Column(name = "PROJECT_OWNER_USERTOKEN")
    private String projOwnerUserToken;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "IS_ACTIVE")
    private boolean isActive;
    @OneToMany(targetEntity = VirtualApi.class,mappedBy = "virtualApiId",orphanRemoval = true)
    List<VirtualApi> virtualApisList;
}
