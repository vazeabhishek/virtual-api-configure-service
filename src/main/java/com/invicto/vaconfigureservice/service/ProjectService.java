package com.invicto.vaconfigureservice.service;

import com.invicto.vaconfigureservice.entitiy.Collection;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.model.VoProject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService {
    public Collection createProject(String userToken, VoProject voProject, Project project);
    public ResponseEntity<String> deleteProject(Long projectId);
    public List<Collection> getProjectsByOrganization(Project project);
    public Collection getProjectByOrganizationAndId(Project project, Long id);
    public Collection findProjectByIdAndOrganization(String userToken, Long projectId, Project orgId);
    public Collection findProjectByNameAndOrganization(String projName, Project project);
}
