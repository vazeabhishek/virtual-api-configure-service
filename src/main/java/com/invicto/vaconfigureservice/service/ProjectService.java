package com.invicto.vaconfigureservice.service;

import com.invicto.vaconfigureservice.entitiy.Organization;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.model.VoProject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService {
    public Project createProject(String userToken, VoProject voProject, Organization organization);
    public ResponseEntity<String> deleteProject(Long projectId);
    public List<Project> getProjectsByOrganization(Organization organization);
    public Project getProjectByOrganizationAndId(Organization organization, Long id);
    public Project findProjectByIdAndOrganization(Long projectId, Organization orgId);
}
