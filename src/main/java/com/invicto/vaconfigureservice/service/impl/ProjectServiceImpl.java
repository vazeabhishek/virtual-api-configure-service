package com.invicto.vaconfigureservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.Collection;
import com.invicto.vaconfigureservice.exception.base.NoPermissionException;
import com.invicto.vaconfigureservice.exception.project.CollectionAlreadyExistException;
import com.invicto.vaconfigureservice.exception.project.CollectionInvalidRequest;
import com.invicto.vaconfigureservice.exception.project.CollectionNotExistException;
import com.invicto.vaconfigureservice.model.VoProject;
import com.invicto.vaconfigureservice.repository.CollectionRepository;
import com.invicto.vaconfigureservice.response.GenericResponse;
import com.invicto.vaconfigureservice.service.ProjectService;
import com.invicto.vaconfigureservice.util.RequestValidator;
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
    private CollectionRepository collectionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String SUCCESS = "success";

    @Override
    public Collection createProject(String userToken, VoProject voProject, Project project) {
        validateProjectRequest(voProject);
        Collection existingCollection = collectionRepository.findByProjectNameAndOrganization(voProject.getProjectName().toUpperCase(), project);
        if (Objects.isNull(existingCollection)) {
            Collection collection = new Collection();
            collection.setActive(true);
            collection.setCreatedBy(userToken);
            collection.setCollectionName(voProject.getProjectName().toUpperCase());
            collection.setCollectionOwnerUserToken(userToken);
            collection.setCreatedDate(LocalDateTime.now());
            collection.setProject(project);
            return collectionRepository.save(collection);
        } else
            throw new CollectionAlreadyExistException(voProject.getProjectName());
    }

    @Override
    public ResponseEntity<String> deleteProject(Long projectId) {
        Collection collection = collectionRepository.findByProjectId(projectId);
        if (Objects.nonNull(collection)) {
            collectionRepository.delete(collection);
            GenericResponse genericResponse = new GenericResponse(SUCCESS, String.valueOf(projectId));
            return new ResponseEntity<>(genericResponse.toJsonString(objectMapper), HttpStatus.ACCEPTED);
        } else
            throw new CollectionNotExistException(String.valueOf(projectId));
    }

    @Override
    public List<Collection> getProjectsByOrganization(Project project) {
        return collectionRepository.findByOrganization(project);
    }

    @Override
    public Collection getProjectByOrganizationAndId(Project project, Long id) {
        return collectionRepository.findByOrganizationAndProjectId(project, id);
    }

    @Override
    public Collection findProjectByIdAndOrganization(String userToken, Long projectId, Project project) {
        Collection collection = collectionRepository.findByOrganizationAndProjectId(project, projectId);
        if (Objects.nonNull(collection)) {
            if (collection.getCollectionOwnerUserToken().contentEquals(userToken)) {
                return collection;
            } else
                throw new NoPermissionException();
        } else
            throw new CollectionNotExistException(String.valueOf(projectId));
    }

    @Override
    public Collection findProjectByNameAndOrganization(String projName, Project project) {
        Collection collection = collectionRepository.findByProjectNameAndOrganization(projName.toUpperCase(), project);
        if (Objects.nonNull(collection))
            return collection;
        else
            throw new CollectionNotExistException(projName);
    }

    private boolean validateProjectRequest(VoProject voProject) {
        boolean status = false;
        if (RequestValidator.isValidProjectName(voProject.getProjectName()))
            status = true;
        else
            throw new CollectionInvalidRequest("Collection name not valid, Should not contain spaces or special chars", voProject.getProjectName());
        return status;
    }

}
