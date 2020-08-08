package com.invicto.vaconfigureservice.service;

import com.invicto.vaconfigureservice.entitiy.Collection;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.model.VoOrganization;
import com.invicto.vaconfigureservice.model.VoOrganizationProject;
import com.invicto.vaconfigureservice.model.VoProject;
import com.invicto.vaconfigureservice.model.VoVirtualApi;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ProjectService {
    public ResponseEntity<String> createOrganization(String userToken, VoOrganization voOrganization);

    public ResponseEntity<String> deleteOrganization(String userToken, Long orgId);

    public ResponseEntity<List<Project>> findAllOrgnizationByUser(String userToken);

    public ResponseEntity<Project> findByOrganizationId(String userToken, Long orgId);

    public ResponseEntity<Project> findByOrganizationName(String organization);

    public ResponseEntity<String> addProject(String userToken, Long orgId, VoProject voProject);

    public ResponseEntity<String> removeProject(String userToken, Long orgId, Long projId);

    public ResponseEntity<List<Collection>> getAllProjects(Long orgId);

    public ResponseEntity<Collection> getProjectByOrganization(Long orgId, String projectName);

    public ResponseEntity<Collection> getProjectById(String userToken, Long orgId, Long projId);

    public ResponseEntity<List<VirtualApi>> getAllApis(Long orgId, Long projId);

    public ResponseEntity<List<VirtualApi>> getAllApis(VoOrganizationProject voOrganizationProject);

    public ResponseEntity<VirtualApi> getApisById(Long orgId, Long projId, Long ApiId);

    public ResponseEntity<String> createApi(String userToken, Long orgId, Long projId, VoVirtualApi voVirtualApi);

    public ResponseEntity<String> deleteApiById(String userToken, Long orgId, Long projId, Long apiId);

    public ResponseEntity<String> toggleApi(String userToken, Long orgId, Long projId, Long apiId);
}
