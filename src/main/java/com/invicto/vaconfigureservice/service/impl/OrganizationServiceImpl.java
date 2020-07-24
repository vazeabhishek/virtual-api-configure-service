package com.invicto.vaconfigureservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invicto.vaconfigureservice.entitiy.Organization;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.exception.api.ApiNotExistException;
import com.invicto.vaconfigureservice.exception.base.NoPermissionException;
import com.invicto.vaconfigureservice.exception.organization.OrgInvalidRequest;
import com.invicto.vaconfigureservice.exception.organization.OrganizationAlreadyExistsException;
import com.invicto.vaconfigureservice.exception.organization.OrganizationNotExistException;
import com.invicto.vaconfigureservice.exception.project.ProjectNotExistException;
import com.invicto.vaconfigureservice.model.VoOrganization;
import com.invicto.vaconfigureservice.model.VoOrganizationProject;
import com.invicto.vaconfigureservice.model.VoProject;
import com.invicto.vaconfigureservice.model.VoVirtualApi;
import com.invicto.vaconfigureservice.repository.OrganizationRepository;
import com.invicto.vaconfigureservice.response.GenericResponse;
import com.invicto.vaconfigureservice.service.OrganizationService;
import com.invicto.vaconfigureservice.service.ProjectService;
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
class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private VirtualApiService virtualApiService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String MSG_SUCCESS = "Success";

    @Override
    public ResponseEntity<String> createOrganization(String userToken, VoOrganization voOrganization) {
        validateOrgRequest(voOrganization);
        Organization existingOrg = organizationRepository.findByOrgName(voOrganization.getOrganizationName().toUpperCase());
        if (Objects.isNull(existingOrg)) {
            Organization organization = new Organization();
            organization.setOrgOwnerUserToken(userToken);
            organization.setCreatedBy(userToken);
            organization.setCreatedDate(LocalDateTime.now());
            organization.setOrgName(voOrganization.getOrganizationName().toUpperCase());
            organization.setActive(true);
            organization.setOrgToken(TokenGenrator.randomTokenGenrator());
            organizationRepository.save(organization);
            GenericResponse genericResponse = new GenericResponse(MSG_SUCCESS, String.valueOf(organization.getOrgId()));
            return new ResponseEntity<>(genericResponse.toJsonString(objectMapper), HttpStatus.CREATED);
        } else {
            throw new OrganizationAlreadyExistsException(voOrganization.getOrganizationName());
        }
    }

    @Override
    public ResponseEntity<String> deleteOrganization(String userToken, Long orgId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.isNull(organization))
            throw new OrganizationNotExistException(String.valueOf(orgId));
        else {
            if (organization.getCreatedBy().contentEquals(userToken))
                organizationRepository.delete(organization);
            else
                throw new NoPermissionException();
        }
        GenericResponse genericResponse = new GenericResponse(MSG_SUCCESS, String.valueOf(orgId));
        return new ResponseEntity<>(genericResponse.toJsonString(objectMapper), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<List<Organization>> findAllOrgnizationByUser(String userToken) {
        List<Organization> organizationList = organizationRepository.findByOrgOwnerUserTokenLikeByOrderByOrgId(userToken);
        return new ResponseEntity<>(organizationList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Organization> findByOrganizationId(String userToken, Long orgId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.nonNull(organization)) {
            if (organization.getOrgOwnerUserToken().contentEquals(userToken))
                return new ResponseEntity<>(organization, HttpStatus.OK);
            else
                throw new NoPermissionException();
        } else
            throw new OrganizationNotExistException(String.valueOf(orgId));
    }

    @Override
    public ResponseEntity<Organization> findByOrganizationName(String organization) {
        Organization organizationObj = organizationRepository.findByOrgName(organization.toUpperCase());
        if (Objects.nonNull(organization)) {
            return new ResponseEntity<>(organizationObj, HttpStatus.OK);
        } else
            throw new OrganizationNotExistException(organization);

    }

    @Override
    public ResponseEntity<String> addProject(String userToken, Long orgId, VoProject voProject) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.nonNull(organization)) {
            if (organization.getOrgOwnerUserToken().contentEquals(userToken)) {
                Project project = projectService.createProject(userToken, voProject, organization);
                GenericResponse genericResponse = new GenericResponse(MSG_SUCCESS, String.valueOf(project.getProjectId()));
                return new ResponseEntity<String>(genericResponse.toJsonString(objectMapper), HttpStatus.CREATED);
            } else
                throw new NoPermissionException();
        } else {
            throw new OrganizationNotExistException(String.valueOf(orgId));
        }
    }

    @Override
    public ResponseEntity<String> removeProject(String userToken, Long orgId, Long projId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.nonNull(organization)) {
            Project project = projectService.getProjectByOrganizationAndId(organization, projId);
            if (Objects.nonNull(project))
                return projectService.deleteProject(project.getProjectId());
            else
                throw new ProjectNotExistException(String.valueOf(projId));
        } else {
            throw new OrganizationNotExistException(String.valueOf(orgId));
        }
    }

    @Override
    public ResponseEntity<List<Project>> getAllProjects(Long orgId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.nonNull(organization)) {
            List<Project> projects = projectService.getProjectsByOrganization(organization);
            return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
        } else
            throw new OrganizationNotExistException(String.valueOf(orgId));
    }

    @Override
    public ResponseEntity<Project> getProjectByOrganization(Long orgId, String projectName) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.isNull(organization))
            throw new OrganizationNotExistException(String.valueOf(orgId));
        else {
            Project project = projectService.findProjectByNameAndOrganization(projectName, organization);
            ResponseEntity<Project> responseEntity = new ResponseEntity<>(project, HttpStatus.OK);
            return responseEntity;
        }
    }

    @Override
    public ResponseEntity<Project> getProjectById(String userToken, Long orgId, Long projId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.isNull(organization))
            throw new OrganizationNotExistException(String.valueOf(orgId));
        else {
            if (organization.getCreatedBy().contentEquals(userToken)) {
                Project project = projectService.findProjectByIdAndOrganization(userToken, projId, organization);
                ResponseEntity<Project> responseEntity = new ResponseEntity<>(project, HttpStatus.OK);
                return responseEntity;
            } else
                throw new NoPermissionException();
        }
    }

    @Override
    public ResponseEntity<List<VirtualApi>> getAllApis(Long orgId, Long projId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.nonNull(organization)) {
            Project project = projectService.getProjectByOrganizationAndId(organization, projId);
            if (Objects.nonNull(project)) {
                List<VirtualApi> virtualApiList = virtualApiService.getAllApisFromProject(project);
                return new ResponseEntity<>(virtualApiList, HttpStatus.OK);
            } else
                throw new ProjectNotExistException(String.valueOf(projId));
        } else
            throw new OrganizationNotExistException(String.valueOf(orgId));
    }

    @Override
    public ResponseEntity<List<VirtualApi>> getAllApis(VoOrganizationProject voOrganizationProject) {
        Organization organization = organizationRepository.findByOrgName(voOrganizationProject.getOrganizationName().toUpperCase());
        if (Objects.nonNull(organization)) {
            Project project = projectService.findProjectByNameAndOrganization(voOrganizationProject.getProjectName().toUpperCase(), organization);
            if (Objects.nonNull(project)) {
                List<VirtualApi> virtualApiList = virtualApiService.getAllActiveApisFromProject(project);
                return new ResponseEntity<>(virtualApiList, HttpStatus.OK);
            } else
                throw new ProjectNotExistException(voOrganizationProject.getProjectName());
        } else
            throw new OrganizationNotExistException(voOrganizationProject.getOrganizationName());
    }

    @Override
    public ResponseEntity<VirtualApi> getApisById(Long orgId, Long projId, Long ApiId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.nonNull(organization)) {
            Project project = projectService.getProjectByOrganizationAndId(organization, projId);
            if (Objects.nonNull(project)) {
                List<VirtualApi> virtualApiList = virtualApiService.getAllApisFromProject(project);
                Optional<VirtualApi> virtualApi = virtualApiList.stream().filter(vApi ->
                        vApi.getVirtualApiId().compareTo(ApiId) == 0
                ).findFirst();
                if (virtualApi.isPresent())
                    return new ResponseEntity<>(virtualApi.get(), HttpStatus.OK);
                else
                    throw new ApiNotExistException(String.valueOf(ApiId));
            } else
                throw new ProjectNotExistException(String.valueOf(projId));

        } else
            throw new OrganizationNotExistException(String.valueOf(orgId));
    }

    @Override
    public ResponseEntity<String> createApi(String userToken, Long orgId, Long projId, VoVirtualApi voVirtualApi) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.nonNull(organization)) {
            Project project = projectService.getProjectByOrganizationAndId(organization, projId);
            if (Objects.nonNull(project)) {
                return virtualApiService.createApi(userToken, project, voVirtualApi);
            } else
                throw new ProjectNotExistException(String.valueOf(projId));
        } else {
            throw new OrganizationNotExistException(String.valueOf(orgId));
        }
    }

    @Override
    public ResponseEntity<String> deleteApiById(String userToken, Long orgId, Long projId, Long apiId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.nonNull(organization)) {
            Project project = projectService.getProjectByOrganizationAndId(organization, projId);
            if (Objects.nonNull(project)) {
                return virtualApiService.deleteApi(userToken, apiId);
            } else
                throw new ProjectNotExistException(String.valueOf(projId));
        } else
            throw new OrganizationNotExistException(String.valueOf(orgId));
    }

    @Override
    public ResponseEntity<String> toggleApi(String userToken, Long orgId, Long projId, Long apiId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.nonNull(organization)) {
            Project project = projectService.getProjectByOrganizationAndId(organization, projId);
            if (Objects.nonNull(project)) {
                return virtualApiService.toggleApi(userToken, apiId);
            } else
                throw new ProjectNotExistException(String.valueOf(projId));
        } else
            throw new OrganizationNotExistException(String.valueOf(orgId));
    }

    private boolean validateOrgRequest(VoOrganization voOrganization) {
        if (RequestValidator.isValidProjectName(voOrganization.getOrganizationName()))
            return  true;
        else
            throw new OrgInvalidRequest("Organization name not valid", voOrganization.getOrganizationName());
    }
}
