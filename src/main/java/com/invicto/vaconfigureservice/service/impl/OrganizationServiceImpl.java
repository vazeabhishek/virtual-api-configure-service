package com.invicto.vaconfigureservice.service.impl;

import com.invicto.vaconfigureservice.entitiy.Organization;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
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
import java.util.List;
import java.util.Objects;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private VirtualApiService virtualApiService;

    @Override
    public ResponseEntity<String> createOrganization(String userToken,VoOrganization voOrganization) {
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
    public ResponseEntity<String> deleteOrganization(String userToken,Long orgId) {
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
    public ResponseEntity<String> addProject(String userToken,Long orgId, VoProject voProject) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if(Objects.nonNull(organization)){
            Project project = projectService.createProject(userToken, voProject, organization);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Override
    public ResponseEntity<String> removeProject(String userToken, Long orgId, Long projId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if(Objects.nonNull(organization)){
           return projectService.deleteProject(projId);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public ResponseEntity<List<Project>> getAllProjects(Long orgId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        if(Objects.nonNull(organization)){
            List<Project> projects =  projectService.getProjectsByOrganization(organization);
            return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<String> createApi(String userToken, Long orgId, Long projId, VoVirtualApi voVirtualApi) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        Project project = projectService.getProjectByOrganizationAndId(organization,projId);
        if(Objects.nonNull(project)){
            // check if user has permission to this project
            List<VirtualApi> virtualApiList = project.getVirtualApisList();
            VirtualApi virtualApi = new VirtualApi();
            virtualApiList.add(virtualApi);
            organizationRepository.save(organization);
        }
        return null;
    }
}
