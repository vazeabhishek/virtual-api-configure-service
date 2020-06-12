package com.invicto.vaconfigureservice.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "ORGANIZATION")
@Data
@Table(name = "ORGANIZATION",
        indexes = {@Index(name = "primary_index", columnList = "ORG_ID", unique = true),
                @Index(name = "secondary_index", columnList = "ORG_NAME", unique = false)})
public class Organization {
    @Id
    @Column(name = "ORG_ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long orgId;
    @Column(name = "ORG_NAME", nullable = false)
    private String orgName;
    @Column(name = "ORG_OWNER_USER_TOKEN", nullable = false)
    private String orgOwnerUserToken;
    @Column(name = "ORG_TOKEN", nullable = false)
    private String orgToken;
    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;
    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy;
    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean isActive;
    @OneToMany(orphanRemoval = true, targetEntity = Project.class, mappedBy = "organization",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Project> projectList;
}
