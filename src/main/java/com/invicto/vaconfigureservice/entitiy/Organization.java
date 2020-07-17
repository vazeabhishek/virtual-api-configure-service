package com.invicto.vaconfigureservice.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "ORGANIZATION")
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "ORGANIZATION",
        indexes = {@Index(name = "primary_index", columnList = "ORG_ID", unique = true),
                @Index(name = "secondary_index", columnList = "ORG_NAME", unique = false)},
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"ORG_NAME"}))
public class Organization {
    @Id
    @Column(name = "ORG_ID", nullable = false)
    @GeneratedValue(generator = "org-sequence-generator")
    @GenericGenerator(
            name = "org-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "org_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
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
    @OneToMany(orphanRemoval = true, targetEntity = Project.class, mappedBy = "organization", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Project> projectList;

    @Override
    public String toString() {
        return "Organization{" +
                "orgId=" + orgId +
                ", orgName='" + orgName + '\'' +
                ", orgOwnerUserToken='" + orgOwnerUserToken + '\'' +
                ", orgToken='" + orgToken + '\'' +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
