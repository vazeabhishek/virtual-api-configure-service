package com.invicto.vaconfigureservice.api;

import com.invicto.vaconfigureservice.entitiy.Organization;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.model.VoOrganization;
import com.invicto.vaconfigureservice.model.VoProject;
import com.invicto.vaconfigureservice.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @PostMapping("org/")
    public ResponseEntity<String> createOrganization(@RequestHeader(name = "user") String userToken, @RequestBody VoOrganization voOrganization) {
        return organizationService.createOrganization(userToken, voOrganization);
    }

    @DeleteMapping("org/{orgId}")
    public ResponseEntity<String> deleteOrganization(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId) {
        return organizationService.deleteOrganization(userToken, orgId);
    }

    @GetMapping("org")
    public ResponseEntity<List<Organization>> getOrganization(@RequestHeader(name = "user") String userToken) {
        return organizationService.findAllOrgnizationByUser(userToken);
    }

    @PostMapping("org/{orgId}/projects")
    public ResponseEntity<String> createProject(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @RequestBody VoProject voProject) {
        return organizationService.addProject(userToken, orgId, voProject);
    }

    @DeleteMapping("org/{orgId}/projects/{projId}")
    public ResponseEntity<String> deleteProject(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @PathVariable(name = "projId") Long projId) {
        return organizationService.removeProject(userToken, orgId, projId);
    }

    @GetMapping("org/{orgId}/projects")
    public ResponseEntity<List<Project>> getProjects(@PathVariable(name = "orgId") Long orgId) {
        return organizationService.getAllProjects(orgId);
    }
    @PostMapping("org/{orgId}/projects/{projId}/apis")
    public ResponseEntity<String> createApi(@RequestHeader(name = "user") String userToken, @RequestBody VoOrganization voOrganization) {
        return null;
    }
    @GetMapping("org/{orgId}/projects/{projId}/apis")
    public ResponseEntity<String> getApis(@RequestHeader(name = "user") String userToken, @RequestBody VoOrganization voOrganization) {
        return null;
    }
    @GetMapping("org/{orgId}/projects/{projId}/apis/{apiId}")
    public ResponseEntity<String> getApisById(@RequestHeader(name = "user") String userToken, @RequestBody VoOrganization voOrganization) {
        return null;
    }
    @DeleteMapping("org/{orgId}/projects/{projId}/apis/{apiId}")
    public ResponseEntity<String> deleteById(@RequestHeader(name = "user") String userToken, @RequestBody VoOrganization voOrganization) {
        return null;
    }
}
