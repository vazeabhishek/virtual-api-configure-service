package com.invicto.vaconfigureservice.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "PROJECT")
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "PROJECT",
        indexes = {@Index(name = "primary_index", columnList = "PROJECT_ID", unique = true),
                @Index(name = "secondary_index", columnList = "PROJECT_NAME", unique = false)},
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"PROJECT_NAME"}))
public class Project {
    @Id
    @Column(name = "PROJECT_ID", nullable = false)
    @GeneratedValue(generator = "project-sequence-generator")
    @GenericGenerator(
            name = "project-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "project_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private long projectId;
    @Column(name = "PROJECT_NAME", nullable = false)
    private String projectName;
    @Column(name = "PROJECT_OWNER_USER_TOKEN", nullable = false)
    private String projectOwnerUserToken;
    @Column(name = "PROJECT_TOKEN", nullable = false)
    private String projectToken;
    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;
    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy;
    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean isActive;
    @OneToMany(orphanRemoval = true, targetEntity = Collection.class, mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Collection> collectionList;

}
