package com.invicto.vaconfigureservice.service;

import com.invicto.vaconfigureservice.entitiy.Collection;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.model.VoProject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CollectionService {
    public Collection createCollection(String userToken, VoProject voProject, Project project);
    public ResponseEntity<String> deleteCollection(Long projectId);
    public List<Collection> getCollectionByProject(Project project);
    public Collection getCollectionByProjectAndId(Project project, Long id);
    public Collection findCollectionByIdAndProject(String userToken, Long projectId, Project orgId);
    public Collection findCollectionByNameAndProject(String projName, Project project);
}
