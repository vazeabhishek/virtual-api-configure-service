package com.invicto.vaconfigureservice.service.impl;

import com.invicto.vaconfigureservice.entitiy.Organization;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.model.VoProject;
import com.invicto.vaconfigureservice.repository.ProjectRepository;
import com.invicto.vaconfigureservice.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project createProject(String userToken, VoProject voProject, Organization organization) {
        Project project = new Project();
        project.setActive(true);
        project.setCreatedBy(userToken);
        project.setProjectName(voProject.getProjectName());
        project.setProjOwnerUserToken(userToken);
        project.setCreatedDate(LocalDateTime.now());
        project.setOrganization(organization);
        return projectRepository.save(project);
    }

    @Override
    public ResponseEntity<String> deleteProject(Long projectId) {
        Project project = projectRepository.findByProjectId(projectId);
        if (Objects.nonNull(project)) {
            projectRepository.delete(project);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public List<Project> getProjectsByOrganization(Organization organization) {
        return projectRepository.findByOrganization(organization);
    }

}
