package com.invicto.vaconfigureservice.api;

import com.invicto.vaconfigureservice.entitiy.Organization;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.model.VoOrganization;
import com.invicto.vaconfigureservice.model.VoOrganizationProject;
import com.invicto.vaconfigureservice.model.VoProject;
import com.invicto.vaconfigureservice.model.VoVirtualApi;
import com.invicto.vaconfigureservice.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("orgs")
    public ResponseEntity<String> createOrganization(@RequestHeader(name = "user") String userToken, @RequestBody VoOrganization voOrganization) {
        return organizationService.createOrganization(userToken, voOrganization);
    }

    @PostMapping("orgs/{orgId}/projects")
    public ResponseEntity<String> createProject(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @RequestBody VoProject voProject) {
        return organizationService.addProject(userToken, orgId, voProject);
    }

    @PostMapping("orgs/{orgId}/projects/{projId}/apis")
    public ResponseEntity<String> createApi(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @PathVariable(name = "projId") Long projId, @RequestBody VoVirtualApi voVirtualApi) {
        return organizationService.createApi(userToken, orgId, projId, voVirtualApi);
    }

    @DeleteMapping("orgs/{orgId}")
    public ResponseEntity<String> deleteOrganization(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId) {
        return organizationService.deleteOrganization(userToken, orgId);
    }

    @DeleteMapping("orgs/{orgId}/projects/{projId}")
    public ResponseEntity<String> deleteProject(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @PathVariable(name = "projId") Long projId) {
        return organizationService.removeProject(userToken, orgId, projId);
    }

    @DeleteMapping("orgs/{orgId}/projects/{projId}/apis/{apiId}")
    public ResponseEntity<String> deleteById(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @PathVariable(name = "projId") Long projId, @PathVariable(name = "apiId") Long apiId) {
        return organizationService.deleteApiById(userToken, orgId, projId, apiId);
    }

    @GetMapping("orgs/{orgId}/projects/{projId}/apis/{apiId}/toggle")
    public ResponseEntity<String> toggleApi(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @PathVariable(name = "projId") Long projId, @PathVariable(name = "apiId") Long apiId) {
        return organizationService.toggleApi(userToken, orgId, projId, apiId);
    }

    @GetMapping("orgs")
    public ResponseEntity<List<Organization>> getOrganization(@RequestHeader(name = "user") String userToken) {
        return organizationService.findAllOrgnizationByUser(userToken);
    }

    @GetMapping("orgs/{orgId}")
    public ResponseEntity<Organization> getOrganizationDetails(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId) {
        return organizationService.findByOrganizationId(userToken, orgId);
    }

    @GetMapping("orgs/{orgId}/projects")
    public ResponseEntity<List<Project>> getProjects(@PathVariable(name = "orgId") Long orgId) {
        return organizationService.getAllProjects(orgId);
    }

    @GetMapping("orgs/{orgId}/projects/{projId}")
    public ResponseEntity<Project> getProjectsById(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @PathVariable(name = "projId") Long projId) {
        return organizationService.getProjectById(userToken, orgId, projId);
    }

    @GetMapping("orgs/{orgId}/projects/{projId}/apis")
    public ResponseEntity<List<VirtualApi>> getApis(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @PathVariable(name = "projId") Long projId) {
        return organizationService.getAllApis(orgId, projId);
    }

    @PostMapping("/filter/apis")
    public ResponseEntity<List<VirtualApi>> filterApis(@RequestBody VoOrganizationProject voOrganizationProject) {
        return organizationService.getAllApis(voOrganizationProject);
    }

    @GetMapping("orgs/{orgId}/projects/{projId}/apis/{apiId}")
    public ResponseEntity<VirtualApi> getApisById(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @PathVariable(name = "projId") Long projId, @PathVariable(name = "apiId") Long apiId) {
        return organizationService.getApisById(orgId, projId, apiId);
    }

}
