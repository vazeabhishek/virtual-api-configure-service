package com.invicto.vaconfigureservice.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "VIRTUAL_API")
@Getter
@Setter
@Table(name = "VIRTUAL_API",
        indexes = {@Index(name = "primary_index", columnList = "VIRTUAL_API_ID", unique = true)},
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"REQUEST_METHOD", "VIRTUAL_API_PATH", "PROJECT_ID"}))
public class VirtualApi {
    @Id
    @Column(name = "VIRTUAL_API_ID")
    @GeneratedValue(generator = "api-sequence-generator")
    @GenericGenerator(
            name = "api-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "api_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Long virtualApiId;
    @Column(name = "VIRTUAL_API_NAME")
    private String virtualApiName;
    @Column(name = "VIRTUAL_API_PATH")
    private String virtualApiPath;
    @Column(name = "REQUEST_METHOD")
    private String requestMethod;
    @Column(name = "AVAILABLE_AT")
    private String availableAt;
    @ManyToOne(targetEntity = Collection.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "PROJECT_ID")
    @JsonIgnore
    private Collection collection;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "IS_ACTIVE")
    private boolean status;
    @OneToMany(mappedBy = "virtualApi", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<VirtualApiSpecs> virtualApiSpecs;

    @Override
    public String toString() {
        return "VirtualApi{" +
                "virtualApiId=" + virtualApiId +
                ", virtualApiName='" + virtualApiName + '\'' +
                ", virtualApiPath='" + virtualApiPath + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", availableAt='" + availableAt + '\'' +
                ", collection=" + collection +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", status=" + status +
                '}';
    }

}