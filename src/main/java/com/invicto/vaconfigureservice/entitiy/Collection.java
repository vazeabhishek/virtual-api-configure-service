package com.invicto.vaconfigureservice.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "COLLECTION")
@Getter
@Setter
@Table(name = "COLLECTION",
        indexes = {@Index(name = "primary_index", columnList = "COLLECTION_ID", unique = true)})
public class Collection {
    @Id
    @Column(name = "COLLECTION_ID")
    @GeneratedValue(generator = "collection-sequence-generator")
    @GenericGenerator(
            name = "collection-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "collection_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Long collectionId;
    @ManyToOne(targetEntity = Project.class, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JsonIgnore
    private Project project;
    @Column(name = "COLLECTION_NAME")
    private String collectionName;
    @Column(name = "COLLECTION_OWNER_USERTOKEN")
    private String collectionOwnerUserToken;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "IS_ACTIVE")
    private boolean isActive;
    @OneToMany(targetEntity = VirtualApi.class, mappedBy = "collection", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    List<VirtualApi> virtualApisList;

    @Override
    public String toString() {
        return "Collection{" +
                "collectionId=" + collectionId +
                ", collectionName='" + collectionName + '\'' +
                ", collectionOwnerUserToken='" + collectionOwnerUserToken + '\'' +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", isActive=" + isActive +
                ", virtualApisList=" + virtualApisList +
                '}';
    }
}
