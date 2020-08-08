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
import com.invicto.vaconfigureservice.model.VoOrganization;
import com.invicto.vaconfigureservice.model.VoOrganizationProject;
import com.invicto.vaconfigureservice.model.VoProject;
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
    public ResponseEntity<String> createOrganization(String userToken, VoOrganization voOrganization) {
        validateOrgRequest(voOrganization);
        Project existingOrg = projectRepository.findByOrgName(voOrganization.getOrganizationName().toUpperCase());
        if (Objects.isNull(existingOrg)) {
            Project project = new Project();
            project.setProjectOwnerUserToken(userToken);
            project.setCreatedBy(userToken);
            project.setCreatedDate(LocalDateTime.now());
            project.setProjectName(voOrganization.getOrganizationName().toUpperCase());
            project.setActive(true);
            project.setProjectToken(TokenGenrator.randomTokenGenrator());
            projectRepository.save(project);
            GenericResponse genericResponse = new GenericResponse(MSG_SUCCESS, String.valueOf(project.getProjectId()));
            return new ResponseEntity<>(genericResponse.toJsonString(objectMapper), HttpStatus.CREATED);
        } else {
            throw new ProjectAlreadyExistsException(voOrganization.getOrganizationName());
        }
    }

    @Override
    public ResponseEntity<String> deleteOrganization(String userToken, Long orgId) {
        Project project = projectRepository.findByOrgId(orgId);
        if (Objects.isNull(project))
            throw new ProjectNotExistException(String.valueOf(orgId));
        else {
            if (project.getCreatedBy().contentEquals(userToken))
                projectRepository.delete(project);
            else
                throw new NoPermissionException();
        }
        GenericResponse genericResponse = new GenericResponse(MSG_SUCCESS, String.valueOf(orgId));
        return new ResponseEntity<>(genericResponse.toJsonString(objectMapper), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<List<Project>> findAllOrgnizationByUser(String userToken) {
        List<Project> projectList = projectRepository.findByOrgOwnerUserTokenLike(userToken);
        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Project> findByOrganizationId(String userToken, Long orgId) {
        Project project = projectRepository.findByOrgId(orgId);
        if (Objects.nonNull(project)) {
            if (project.getProjectOwnerUserToken().contentEquals(userToken))
                return new ResponseEntity<>(project, HttpStatus.OK);
            else
                throw new NoPermissionException();
        } else
            throw new ProjectNotExistException(String.valueOf(orgId));
    }

    @Override
    public ResponseEntity<Project> findByOrganizationName(String organization) {
        Project projectObj = projectRepository.findByOrgName(organization.toUpperCase());
        if (Objects.nonNull(organization)) {
            return new ResponseEntity<>(projectObj, HttpStatus.OK);
        } else
            throw new ProjectNotExistException(organization);

    }

    @Override
    public ResponseEntity<String> addProject(String userToken, Long orgId, VoProject voProject) {
        Project project = projectRepository.findByOrgId(orgId);
        if (Objects.nonNull(project)) {
            if (project.getProjectOwnerUserToken().contentEquals(userToken)) {
                Collection collection = collectionService.createCollection(userToken, voProject, project);
                GenericResponse genericResponse = new GenericResponse(MSG_SUCCESS, String.valueOf(collection.getCollectionId()));
                return new ResponseEntity<String>(genericResponse.toJsonString(objectMapper), HttpStatus.CREATED);
            } else
                throw new NoPermissionException();
        } else {
            throw new ProjectNotExistException(String.valueOf(orgId));
        }
    }

    @Override
    public ResponseEntity<String> removeProject(String userToken, Long orgId, Long projId) {
        Project project = projectRepository.findByOrgId(orgId);
        if (Objects.nonNull(project)) {
            Collection collection = collectionService.getCollectionByProjectAndId(project, projId);
            if (Objects.nonNull(collection))
                return collectionService.deleteCollection(collection.getCollectionId());
            else
                throw new CollectionNotExistException(String.valueOf(projId));
        } else {
            throw new ProjectNotExistException(String.valueOf(orgId));
        }
    }

    @Override
    public ResponseEntity<List<Collection>> getAllProjects(Long orgId) {
        Project project = projectRepository.findByOrgId(orgId);
        if (Objects.nonNull(project)) {
            List<Collection> collections = collectionService.getCollectionByProject(project);
            return new ResponseEntity<List<Collection>>(collections, HttpStatus.OK);
        } else
            throw new ProjectNotExistException(String.valueOf(orgId));
    }

    @Override
    public ResponseEntity<Collection> getProjectByOrganization(Long orgId, String projectName) {
        Project project = projectRepository.findByOrgId(orgId);
        if (Objects.isNull(project))
            throw new ProjectNotExistException(String.valueOf(orgId));
        else {
            Collection collection = collectionService.findCollectionByNameAndProject(projectName, project);
            ResponseEntity<Collection> responseEntity = new ResponseEntity<>(collection, HttpStatus.OK);
            return responseEntity;
        }
    }

    @Override
    public ResponseEntity<Collection> getProjectById(String userToken, Long orgId, Long projId) {
        Project project = projectRepository.findByOrgId(orgId);
        if (Objects.isNull(project))
            throw new ProjectNotExistException(String.valueOf(orgId));
        else {
            if (project.getCreatedBy().contentEquals(userToken)) {
                Collection collection = collectionService.findCollectionByIdAndProject(userToken, projId, project);
                ResponseEntity<Collection> responseEntity = new ResponseEntity<>(collection, HttpStatus.OK);
                return responseEntity;
            } else
                throw new NoPermissionException();
        }
    }

    @Override
    public ResponseEntity<List<VirtualApi>> getAllApis(Long orgId, Long projId) {
        Project project = projectRepository.findByOrgId(orgId);
        if (Objects.nonNull(project)) {
            Collection collection = collectionService.getCollectionByProjectAndId(project, projId);
            if (Objects.nonNull(collection)) {
                List<VirtualApi> virtualApiList = virtualApiService.getAllApisFromProject(collection);
                return new ResponseEntity<>(virtualApiList, HttpStatus.OK);
            } else
                throw new CollectionNotExistException(String.valueOf(projId));
        } else
            throw new ProjectNotExistException(String.valueOf(orgId));
    }

    @Override
    public ResponseEntity<List<VirtualApi>> getAllApis(VoOrganizationProject voOrganizationProject) {
        Project project = projectRepository.findByOrgName(voOrganizationProject.getOrganizationName().toUpperCase());
        if (Objects.nonNull(project)) {
            Collection collection = collectionService.findCollectionByNameAndProject(voOrganizationProject.getProjectName().toUpperCase(), project);
            if (Objects.nonNull(collection)) {
                List<VirtualApi> virtualApiList = virtualApiService.getAllActiveApisFromProject(collection);
                return new ResponseEntity<>(virtualApiList, HttpStatus.OK);
            } else
                throw new CollectionNotExistException(voOrganizationProject.getProjectName());
        } else
            throw new ProjectNotExistException(voOrganizationProject.getOrganizationName());
    }

    @Override
    public ResponseEntity<VirtualApi> getApisById(Long orgId, Long projId, Long ApiId) {
        Project project = projectRepository.findByOrgId(orgId);
        if (Objects.nonNull(project)) {
            Collection collection = collectionService.getCollectionByProjectAndId(project, projId);
            if (Objects.nonNull(collection)) {
                List<VirtualApi> virtualApiList = virtualApiService.getAllApisFromProject(collection);
                Optional<VirtualApi> virtualApi = virtualApiList.stream().filter(vApi ->
                        vApi.getVirtualApiId().compareTo(ApiId) == 0
                ).findFirst();
                if (virtualApi.isPresent())
                    return new ResponseEntity<>(virtualApi.get(), HttpStatus.OK);
                else
                    throw new ApiNotExistException(String.valueOf(ApiId));
            } else
                throw new CollectionNotExistException(String.valueOf(projId));

        } else
            throw new ProjectNotExistException(String.valueOf(orgId));
    }

    @Override
    public ResponseEntity<String> createApi(String userToken, Long orgId, Long projId, VoVirtualApi voVirtualApi) {
        Project project = projectRepository.findByOrgId(orgId);
        if (Objects.nonNull(project)) {
            Collection collection = collectionService.getCollectionByProjectAndId(project, projId);
            if (Objects.nonNull(collection)) {
                return virtualApiService.createApi(userToken, collection, voVirtualApi);
            } else
                throw new CollectionNotExistException(String.valueOf(projId));
        } else {
            throw new ProjectNotExistException(String.valueOf(orgId));
        }
    }

    @Override
    public ResponseEntity<String> deleteApiById(String userToken, Long orgId, Long projId, Long apiId) {
        Project project = projectRepository.findByOrgId(orgId);
        if (Objects.nonNull(project)) {
            Collection collection = collectionService.getCollectionByProjectAndId(project, projId);
            if (Objects.nonNull(collection)) {
                return virtualApiService.deleteApi(userToken, apiId);
            } else
                throw new CollectionNotExistException(String.valueOf(projId));
        } else
            throw new ProjectNotExistException(String.valueOf(orgId));
    }

    @Override
    public ResponseEntity<String> toggleApi(String userToken, Long orgId, Long projId, Long apiId) {
        Project project = projectRepository.findByOrgId(orgId);
        if (Objects.nonNull(project)) {
            Collection collection = collectionService.getCollectionByProjectAndId(project, projId);
            if (Objects.nonNull(collection)) {
                return virtualApiService.toggleApi(userToken, apiId);
            } else
                throw new CollectionNotExistException(String.valueOf(projId));
        } else
            throw new ProjectNotExistException(String.valueOf(orgId));
    }

    private boolean validateOrgRequest(VoOrganization voOrganization) {
        if (RequestValidator.isValidProjectName(voOrganization.getOrganizationName()))
            return  true;
        else
            throw new ProjectInvalidRequest("Project name not valid, Should not contain spaces or special chars", voOrganization.getOrganizationName());
    }
}
