package com.invicto.vaconfigureservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.Collection;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.exception.api.ApiNotExistException;
import com.invicto.vaconfigureservice.exception.base.NoPermissionException;
import com.invicto.vaconfigureservice.exception.organization.ProjectInvalidRequest;
import com.invicto.vaconfigureservice.exception.organization.ProjectAlreadyExistsException;
import com.invicto.vaconfigureservice.exception.organization.ProjectNotExistException;
import com.invicto.vaconfigureservice.exception.project.CollectionNotExistException;
import com.invicto.vaconfigureservice.model.VoProject;
import com.invicto.vaconfigureservice.model.VoProjectCollection;
import com.invicto.vaconfigureservice.model.VoCollection;
import com.invicto.vaconfigureservice.model.VoVirtualApi;
import com.invicto.vaconfigureservice.repository.ProjectRepository;
import com.invicto.vaconfigureservice.response.GenericResponse;
import com.invicto.vaconfigureservice.service.ProjectService;
import com.invicto.vaconfigureservice.service.CollectionService;
import com.invicto.vaconfigureservice.service.VirtualApiService;
import com.invicto.vaconfigureservice.util.RequestValidator;
import com.invicto.vaconfigureservice.util.TokenGenrator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private VirtualApiService virtualApiService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String MSG_SUCCESS = "Success";

    @Override
    public ResponseEntity<String> createProject(String userToken, VoProject voOrganization) {
        validateOrgRequest(voOrganization);
        Project existingOrg = projectRepository.findByProjectName(voOrganization.getProjectName().toUpperCase());
        if (Objects.isNull(existingOrg)) {
            Project project = new Project();
            project.setProjectOwnerUserToken(userToken);
            project.setCreatedBy(userToken);
            project.setCreatedDate(LocalDateTime.now());
            project.setProjectName(voOrganization.getProjectName().toUpperCase());
            project.setActive(true);
            project.setProjectToken(TokenGenrator.randomTokenGenrator());
            projectRepository.save(project);
            GenericResponse genericResponse = new GenericResponse(MSG_SUCCESS, String.valueOf(project.getProjectId()));
            return new ResponseEntity<>(genericResponse.toJsonString(objectMapper), HttpStatus.CREATED);
        } else {
            throw new ProjectAlreadyExistsException(voOrganization.getProjectName());
        }
    }

    @Override
    public ResponseEntity<String> deleteProject(String userToken, Long projId) {
        Project project = projectRepository.findByProjectId(projId);
        if (Objects.isNull(project))
            throw new ProjectNotExistException(String.valueOf(projId));
        else {
            if (project.getCreatedBy().contentEquals(userToken))
                projectRepository.delete(project);
            else
                throw new NoPermissionException();
        }
        GenericResponse genericResponse = new GenericResponse(MSG_SUCCESS, String.valueOf(projId));
        return new ResponseEntity<>(genericResponse.toJsonString(objectMapper), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<List<Project>> findAllProjectsByUser(String userToken) {
        List<Project> projectList = projectRepository.findByProjectOwnerUserTokenLike(userToken);
        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Project> findByProjectId(String userToken, Long projectId) {
        Project project = projectRepository.findByProjectId(projectId);
        if (Objects.nonNull(project)) {
            if (project.getProjectOwnerUserToken().contentEquals(userToken))
                return new ResponseEntity<>(project, HttpStatus.OK);
            else
                throw new NoPermissionException();
        } else
            throw new ProjectNotExistException(String.valueOf(projectId));
    }

    @Override
    public ResponseEntity<Project> findByProjectName(String projectName) {
        Project projectObj = projectRepository.findByProjectName(projectName.toUpperCase());
        if (Objects.nonNull(projectName)) {
            return new ResponseEntity<>(projectObj, HttpStatus.OK);
        } else
            throw new ProjectNotExistException(projectName);

    }

    @Override
    public ResponseEntity<String> createCollection(String userToken, Long projId, VoCollection voCollection) {
        Project project = projectRepository.findByProjectId(projId);
        if (Objects.nonNull(project)) {
            if (project.getProjectOwnerUserToken().contentEquals(userToken)) {
                Collection collection = collectionService.createCollection(userToken, voCollection, project);
                GenericResponse genericResponse = new GenericResponse(MSG_SUCCESS, String.valueOf(collection.getCollectionId()));
                return new ResponseEntity<String>(genericResponse.toJsonString(objectMapper), HttpStatus.CREATED);
            } else
                throw new NoPermissionException();
        } else {
            throw new ProjectNotExistException(String.valueOf(projId));
        }
    }

    @Override
    public ResponseEntity<String> deleteCollection(String userToken, Long projectId, Long collectionId) {
        Project project = projectRepository.findByProjectId(projectId);
        if (Objects.nonNull(project)) {
            Collection collection = collectionService.getCollectionByProjectAndId(project, collectionId);
            if (Objects.nonNull(collection))
                return collectionService.deleteCollection(collection.getCollectionId());
            else
                throw new CollectionNotExistException(String.valueOf(collectionId));
        } else {
            throw new ProjectNotExistException(String.valueOf(projectId));
        }
    }

    @Override
    public ResponseEntity<List<Collection>> getAllCollections(Long projId) {
        Project project = projectRepository.findByProjectId(projId);
        if (Objects.nonNull(project)) {
            List<Collection> collections = collectionService.getCollectionByProject(project);
            return new ResponseEntity<List<Collection>>(collections, HttpStatus.OK);
        } else
            throw new ProjectNotExistException(String.valueOf(projId));
    }

    @Override
    public ResponseEntity<Collection> getCollectionsByProjectIdAndCollectionName(Long projectId, String collectionName) {
        Project project = projectRepository.findByProjectId(projectId);
        if (Objects.isNull(project))
            throw new ProjectNotExistException(String.valueOf(projectId));
        else {
            Collection collection = collectionService.findCollectionByNameAndProject(collectionName, project);
            ResponseEntity<Collection> responseEntity = new ResponseEntity<>(collection, HttpStatus.OK);
            return responseEntity;
        }
    }

    @Override
    public ResponseEntity<Collection> getCollectionById(String userToken, Long projectId, Long collectionId) {
        Project project = projectRepository.findByProjectId(projectId);
        if (Objects.isNull(project))
            throw new ProjectNotExistException(String.valueOf(projectId));
        else {
            if (project.getCreatedBy().contentEquals(userToken)) {
                Collection collection = collectionService.findCollectionByIdAndProject(userToken, collectionId, project);
                ResponseEntity<Collection> responseEntity = new ResponseEntity<>(collection, HttpStatus.OK);
                return responseEntity;
            } else
                throw new NoPermissionException();
        }
    }

    @Override
    public ResponseEntity<List<VirtualApi>> getAllApis(Long projectId, Long collectionId) {
        Project project = projectRepository.findByProjectId(projectId);
        if (Objects.nonNull(project)) {
            Collection collection = collectionService.getCollectionByProjectAndId(project, collectionId);
            if (Objects.nonNull(collection)) {
                List<VirtualApi> virtualApiList = virtualApiService.getAllApisFromProject(collection);
                return new ResponseEntity<>(virtualApiList, HttpStatus.OK);
            } else
                throw new CollectionNotExistException(String.valueOf(collectionId));
        } else
            throw new ProjectNotExistException(String.valueOf(projectId));
    }

    @Override
    public ResponseEntity<List<VirtualApi>> getAllApis(VoProjectCollection voCollectionProject) {
        Project project = projectRepository.findByProjectName(voCollectionProject.getProjectName().toUpperCase());
        if (Objects.nonNull(project)) {
            Collection collection = collectionService.findCollectionByNameAndProject(voCollectionProject.getCollectionName().toUpperCase(), project);
            if (Objects.nonNull(collection)) {
                List<VirtualApi> virtualApiList = virtualApiService.getAllActiveApisFromProject(collection);
                return new ResponseEntity<>(virtualApiList, HttpStatus.OK);
            } else
                throw new CollectionNotExistException(voCollectionProject.getCollectionName());
        } else
            throw new ProjectNotExistException(voCollectionProject.getProjectName());
    }

    @Override
    public ResponseEntity<VirtualApi> getApisById(Long projectId, Long collectionId, Long apiId) {
        Project project = projectRepository.findByProjectId(projectId);
        if (Objects.nonNull(project)) {
            Collection collection = collectionService.getCollectionByProjectAndId(project, collectionId);
            if (Objects.nonNull(collection)) {
                List<VirtualApi> virtualApiList = virtualApiService.getAllApisFromProject(collection);
                Optional<VirtualApi> virtualApi = virtualApiList.stream().filter(vApi ->
                        vApi.getVirtualApiId().compareTo(apiId) == 0
                ).findFirst();
                if (virtualApi.isPresent())
                    return new ResponseEntity<>(virtualApi.get(), HttpStatus.OK);
                else
                    throw new ApiNotExistException(String.valueOf(apiId));
            } else
                throw new CollectionNotExistException(String.valueOf(collectionId));

        } else
            throw new ProjectNotExistException(String.valueOf(projectId));
    }

    @Override
    public ResponseEntity<String> createApi(String userToken, Long projectId, Long collectionId, VoVirtualApi voVirtualApi) {
        Project project = projectRepository.findByProjectId(projectId);
        if (Objects.nonNull(project)) {
            Collection collection = collectionService.getCollectionByProjectAndId(project, collectionId);
            if (Objects.nonNull(collection)) {
                return virtualApiService.createApi(userToken, collection, voVirtualApi);
            } else
                throw new CollectionNotExistException(String.valueOf(collectionId));
        } else {
            throw new ProjectNotExistException(String.valueOf(projectId));
        }
    }

    @Override
    public ResponseEntity<String> deleteApiById(String userToken, Long projectId, Long collectionId, Long apiId) {
        Project project = projectRepository.findByProjectId(projectId);
        if (Objects.nonNull(project)) {
            Collection collection = collectionService.getCollectionByProjectAndId(project, collectionId);
            if (Objects.nonNull(collection)) {
                return virtualApiService.deleteApi(userToken, apiId);
            } else
                throw new CollectionNotExistException(String.valueOf(collectionId));
        } else
            throw new ProjectNotExistException(String.valueOf(projectId));
    }

    @Override
    public ResponseEntity<String> toggleApi(String userToken, Long projectId, Long collectionId, Long apiId) {
        Project project = projectRepository.findByProjectId(projectId);
        if (Objects.nonNull(project)) {
            Collection collection = collectionService.getCollectionByProjectAndId(project, collectionId);
            if (Objects.nonNull(collection)) {
                return virtualApiService.toggleApi(userToken, apiId);
            } else
                throw new CollectionNotExistException(String.valueOf(collectionId));
        } else
            throw new ProjectNotExistException(String.valueOf(projectId));
    }

    private boolean validateOrgRequest(VoProject voProject) {
        if (RequestValidator.isValidProjectName(voProject.getProjectName()))
            return true;
        else
            throw new ProjectInvalidRequest("Project name not valid, Should not contain spaces or special chars", voProject.getProjectName());
    }
}
