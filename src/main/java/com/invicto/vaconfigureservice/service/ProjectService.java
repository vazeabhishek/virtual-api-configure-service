package com.invicto.vaconfigureservice.service;

import com.invicto.vaconfigureservice.entitiy.Collection;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.model.VoProject;
import com.invicto.vaconfigureservice.model.VoProjectCollection;
import com.invicto.vaconfigureservice.model.VoCollection;
import com.invicto.vaconfigureservice.model.VoVirtualApi;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ProjectService {
    public ResponseEntity<String> createProject(String userToken, VoProject voOrganization);

    public ResponseEntity<String> deleteProject(String userToken, Long projectId);

    public ResponseEntity<List<Project>> findAllProjectsByUser(String userToken);

    public ResponseEntity<Project> findByProjectId(String userToken, Long projectId);

    public ResponseEntity<Project> findByProjectName(String projectName);

    public ResponseEntity<String> createCollection(String userToken, Long projectId, VoCollection voCollection);

    public ResponseEntity<String> deleteCollection(String userToken, Long projectId, Long collectionId);

    public ResponseEntity<List<Collection>> getAllCollections(Long projectId);

    public ResponseEntity<Collection> getCollectionsByProjectIdAndCollectionName(Long projectId, String collectionName);

    public ResponseEntity<Collection> getCollectionById(String userToken, Long orgId, Long projId);

    public ResponseEntity<List<VirtualApi>> getAllApis(Long orgId, Long projId);

    public ResponseEntity<List<VirtualApi>> getAllApis(VoProjectCollection voCollectionProject);

    public ResponseEntity<VirtualApi> getApisById(Long projectId, Long collectionId, Long ApiId);

    public ResponseEntity<String> createApi(String userToken, Long projectId, Long collectionId, VoVirtualApi voVirtualApi);

    public ResponseEntity<String> deleteApiById(String userToken, Long projectId, Long collectionId, Long apiId);

    public ResponseEntity<String> toggleApi(String userToken, Long collectionId, Long projectId, Long apiId);
}
