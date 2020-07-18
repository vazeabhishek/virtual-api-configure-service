package com.invicto.vaconfigureservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invicto.vaconfigureservice.entitiy.Organization;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.exception.NoPermissionException;
import com.invicto.vaconfigureservice.exception.ProjectAlreadyExistException;
import com.invicto.vaconfigureservice.exception.ProjectNotExistException;
import com.invicto.vaconfigureservice.model.VoProject;
import com.invicto.vaconfigureservice.repository.ProjectRepository;
import com.invicto.vaconfigureservice.response.GenericResponse;
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

    @Autowired
    private ObjectMapper objectMapper;

    private final String SUCCESS = "success";

    @Override
    public Project createProject(String userToken, VoProject voProject, Organization organization) {
        Project existingProject = projectRepository.findByProjectNameAndOrganization(voProject.getProjectName().toUpperCase(), organization);
        if (Objects.isNull(existingProject)) {
            Project project = new Project();
            project.setActive(true);
            project.setCreatedBy(userToken);
            project.setProjectName(voProject.getProjectName().toUpperCase());
            project.setProjOwnerUserToken(userToken);
            project.setCreatedDate(LocalDateTime.now());
            project.setOrganization(organization);
            return projectRepository.save(project);
        } else
            throw new ProjectAlreadyExistException(voProject.getProjectName());
    }

    @Override
    public ResponseEntity<String> deleteProject(Long projectId) {
        Project project = projectRepository.findByProjectId(projectId);
        if (Objects.nonNull(project)) {
            projectRepository.delete(project);
            GenericResponse genericResponse = new GenericResponse(SUCCESS, String.valueOf(projectId));
            return new ResponseEntity<>(genericResponse.toJsonString(objectMapper), HttpStatus.ACCEPTED);
        } else
            throw new ProjectNotExistException(String.valueOf(projectId));
    }

    @Override
    public List<Project> getProjectsByOrganization(Organization organization) {
        return projectRepository.findByOrganization(organization);
    }

    @Override
    public Project getProjectByOrganizationAndId(Organization organization, Long id) {
        return projectRepository.findByOrganizationAndProjectId(organization, id);
    }

    @Override
    public Project findProjectByIdAndOrganization(String userToken, Long projectId, Organization organization) {
        Project project = projectRepository.findByOrganizationAndProjectId(organization, projectId);
        if (Objects.nonNull(project)) {
            if (project.getProjOwnerUserToken().contentEquals(userToken)) {
                return project;
            } else
                throw new NoPermissionException();
        } else
            throw new ProjectNotExistException(String.valueOf(projectId));
    }

    @Override
    public Project findProjectByNameAndOrganization(String projName, Organization organization) {
        Project project = projectRepository.findByProjectNameAndOrganization(projName.toUpperCase(), organization);
        if (Objects.nonNull(project))
            return project;
        else
            throw new ProjectNotExistException(projName);
    }

}
