package com.invicto.vaconfigureservice.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "PROJECT")
@Getter
@Setter
@Table(name = "PROJECT",
        indexes = {@Index(name = "primary_index", columnList = "PROJECT_ID", unique = true)})
public class Project {
    @Id
    @Column(name = "PROJECT_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long projectId;
    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JsonIgnore
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
    @OneToMany(targetEntity = VirtualApi.class,mappedBy = "project",orphanRemoval = true,cascade = CascadeType.ALL)
    @JsonIgnore
    List<VirtualApi> virtualApisList;

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", projOwnerUserToken='" + projOwnerUserToken + '\'' +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
