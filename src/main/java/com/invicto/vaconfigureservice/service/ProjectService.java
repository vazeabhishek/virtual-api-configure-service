package com.invicto.vaconfigureservice.service;

import com.invicto.vaconfigureservice.entitiy.Collection;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.model.VoProject;
import com.invicto.vaconfigureservice.model.VoOrganizationProject;
import com.invicto.vaconfigureservice.model.VoCollection;
import com.invicto.vaconfigureservice.model.VoVirtualApi;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ProjectService {
    public ResponseEntity<String> createProject(String userToken, VoProject voOrganization);

    public ResponseEntity<String> deleteProject(String userToken, Long orgId);

    public ResponseEntity<List<Project>> findAllProjectsByUser(String userToken);

    public ResponseEntity<Project> findByProjectId(String userToken, Long orgId);

    public ResponseEntity<Project> findByProjectName(String organization);

    public ResponseEntity<String> createCollection(String userToken, Long orgId, VoCollection voCollection);

    public ResponseEntity<String> deleteCollection(String userToken, Long orgId, Long projId);

    public ResponseEntity<List<Collection>> getAllCollections(Long projId);

    public ResponseEntity<Collection> getCollectionsByProject(Long orgId, String projectName);

    public ResponseEntity<Collection> getCollectionById(String userToken, Long orgId, Long projId);

    public ResponseEntity<List<VirtualApi>> getAllApis(Long orgId, Long projId);

    public ResponseEntity<List<VirtualApi>> getAllApis(VoOrganizationProject voOrganizationProject);

    public ResponseEntity<VirtualApi> getApisById(Long orgId, Long projId, Long ApiId);

    public ResponseEntity<String> createApi(String userToken, Long orgId, Long projId, VoVirtualApi voVirtualApi);

    public ResponseEntity<String> deleteApiById(String userToken, Long orgId, Long projId, Long apiId);

    public ResponseEntity<String> toggleApi(String userToken, Long orgId, Long projId, Long apiId);
}
