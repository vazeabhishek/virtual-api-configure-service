package com.invicto.vaconfigureservice.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "VIRTUAL_API")
@Getter
@Setter
@Table(name = "VIRTUAL_API",
        indexes = {@Index(name = "primary_index", columnList = "VIRTUAL_API_ID", unique = true)},
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"REQUEST_METHOD","VIRTUAL_API_PATH", "PROJECT_ID"}))
public class VirtualApi {
    @Id
    @Column(name = "VIRTUAL_API_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long virtualApiId;
    @Column(name = "VIRTUAL_API_NAME")
    private String virtualApiName;
    @Column(name = "VIRTUAL_API_PATH")
    private String virtualApiPath;
    @Column(name = "REQUEST_METHOD")
    private String requestMethod;
    @ManyToOne(targetEntity = Project.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "PROJECT_ID")
    @JsonIgnore
    private Project project;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @OneToMany(mappedBy = "virtualApi", fetch = FetchType.EAGER,cascade = CascadeType.ALL)

    private List<VirtualApiSpecs> virtualApiSpecs;

    @Override
    public String toString() {
        return "VirtualApi{" +
                "virtualApiId='" + virtualApiId + '\'' +
                ", virtualApiName='" + virtualApiName + '\'' +
                ", virtualApiPath='" + virtualApiPath + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}