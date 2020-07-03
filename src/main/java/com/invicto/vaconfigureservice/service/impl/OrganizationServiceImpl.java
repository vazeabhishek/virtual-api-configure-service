package com.invicto.vaconfigureservice.service.impl;

import com.invicto.vaconfigureservice.entitiy.Organization;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.entitiy.VirtualApiSpecs;
import com.invicto.vaconfigureservice.model.VoOrganization;
import com.invicto.vaconfigureservice.model.VoProject;
import com.invicto.vaconfigureservice.model.VoVirtualApi;
import com.invicto.vaconfigureservice.repository.OrganizationRepository;
import com.invicto.vaconfigureservice.service.OrganizationService;
import com.invicto.vaconfigureservice.service.ProjectService;
import com.invicto.vaconfigureservice.service.VirtualApiService;
import com.invicto.vaconfigureservice.util.TokenGenrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private VirtualApiService virtualApiService;

    @Override
    public ResponseEntity<String> createOrganization(String userToken, VoOrganization voOrganization) {
        Organization organization = new Organization();
        organization.setOrgOwnerUserToken(userToken);
        organization.setCreatedBy(userToken);
        organization.setCreatedDate(LocalDateTime.now());
        organization.setOrgName(voOrganization.getOrganizationName());
        organization.setActive(true);
        organization.setOrgToken(TokenGenrator.randomTokenGenrator());
        organizationRepository.save(organization);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteOrganization(String userToken, Long orgId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        organizationRepository.delete(organization);
        return null;
    }

    @Override
    public ResponseEntity<List<Organization>> findAllOrgnizationByUser(String userToken) {
        List<Organization> organizationList = organizationRepository.findByOrgOwnerUserTokenLike(userToken);
        return new ResponseEntity<List<Organization>>(organizationList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addProject(String userToken, Long orgId, VoProject voProject) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.nonNull(organization)) {
            Project project = projectService.createProject(userToken, voProject, organization);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Override
    public ResponseEntity<String> removeProject(String userToken, Long orgId, Long projId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.nonNull(organization)) {
            return projectService.deleteProject(projId);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public ResponseEntity<List<Project>> getAllProjects(Long orgId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.nonNull(organization)) {
            List<Project> projects = projectService.getProjectsByOrganization(organization);
            return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public ResponseEntity<List<VirtualApi>> getAllApis(Long orgId, Long projId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if (Objects.nonNull(organization)) {
            Project project = projectService.getProjectByOrganizationAndId(organization, projId);
            if (Objects.nonNull(project)) {
                List<VirtualApi> virtualApiList = virtualApiService.getAllApisFromProject(project);
                return new ResponseEntity<>(virtualApiList, HttpStatus.OK);
            }
        }
        return null;
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
                if(virtualApi.isPresent())
                    return new ResponseEntity<>(virtualApi.get(),HttpStatus.OK);
            }

        }
        return null;
    }

    @Override
    public ResponseEntity<String> createApi(String userToken, Long orgId, Long projId, VoVirtualApi voVirtualApi) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        Project project = projectService.getProjectByOrganizationAndId(organization, projId);
        if (Objects.nonNull(project)) {
            // check if user has permission to this project
            List<VirtualApi> virtualApiList = project.getVirtualApisList();
            VirtualApi virtualApi = new VirtualApi();
            virtualApi.setVirtualApiName(voVirtualApi.getName());
            virtualApi.setCreatedDate(LocalDateTime.now());
            virtualApi.setProject(project);
            virtualApi.setCreatedBy(userToken);
            virtualApi.setVirtualApiPath(voVirtualApi.getPath());
            virtualApi.setRequestMethod(voVirtualApi.getRequestMethod());
            List<VirtualApiSpecs> virtualApiSpecsList = new LinkedList<>();
            voVirtualApi.getVoVirtualApiSpecList().stream().forEach(voVirtualApiSpec -> {
                VirtualApiSpecs virtualApiSpecs = new VirtualApiSpecs();
                virtualApiSpecs.setResponsePayload(voVirtualApiSpec.getResponsePayload());
                virtualApiSpecs.setRequestPayload(voVirtualApiSpec.getRequestPayLoad());
                virtualApiSpecs.setCreatedDate(LocalDateTime.now());
                virtualApiSpecs.setVirtualApi(virtualApi);
                virtualApiSpecs.setResponseCode(voVirtualApiSpec.getReponseStatusCode());
                virtualApiSpecsList.add(virtualApiSpecs);

            });
            virtualApi.setVirtualApiSpecs(virtualApiSpecsList);
            virtualApiList.add(virtualApi);
            organizationRepository.save(organization);

        }
        return null;
    }


}
