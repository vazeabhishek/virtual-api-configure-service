package com.invicto.vaconfigureservice.api;

import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.Collection;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.model.VoProject;
import com.invicto.vaconfigureservice.model.VoProjectCollection;
import com.invicto.vaconfigureservice.model.VoCollection;
import com.invicto.vaconfigureservice.model.VoVirtualApi;
import com.invicto.vaconfigureservice.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("projects")
    public ResponseEntity<String> createProject(@RequestHeader(name = "user") String userToken, @RequestBody VoProject voOrganization) {
        return projectService.createProject(userToken, voOrganization);
    }

    @PostMapping("projects/{projectId}/collections")
    public ResponseEntity<String> createCollection(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @RequestBody VoCollection voCollection) {
        return projectService.createCollection(userToken, orgId, voCollection);
    }

    @PostMapping("projects/{projectId}/collections/{collectionId}/apis")
    public ResponseEntity<String> createApi(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @PathVariable(name = "projId") Long projId, @RequestBody VoVirtualApi voVirtualApi) {
        return projectService.createApi(userToken, orgId, projId, voVirtualApi);
    }

    @DeleteMapping("projects/{projectId}")
    public ResponseEntity<String> deleteProject(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId) {
        return projectService.deleteProject(userToken, orgId);
    }

    @DeleteMapping("projects/{projectId}/collections/{collectionId}")
    public ResponseEntity<String> deleteCollection(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @PathVariable(name = "projId") Long projId) {
        return projectService.deleteCollection(userToken, orgId, projId);
    }

    @DeleteMapping("projects/{projectId}/collections/{collectionId}/apis/{apiId}")
    public ResponseEntity<String> deleteById(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @PathVariable(name = "projId") Long projId, @PathVariable(name = "apiId") Long apiId) {
        return projectService.deleteApiById(userToken, orgId, projId, apiId);
    }

    @GetMapping("projects/{projectId}/collections/{projId}/apis/{collectionId}/toggle")
    public ResponseEntity<String> toggleApi(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @PathVariable(name = "projId") Long projId, @PathVariable(name = "apiId") Long apiId) {
        return projectService.toggleApi(userToken, orgId, projId, apiId);
    }

    @GetMapping("collections")
    public ResponseEntity<List<Project>> getProject(@RequestHeader(name = "user") String userToken) {
        return projectService.findAllProjectsByUser(userToken);
    }

    @GetMapping("projects/{projectId}")
    public ResponseEntity<Project> getProjectDetails(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId) {
        return projectService.findByProjectId(userToken, orgId);
    }

    @GetMapping("projects/{projectId}/collections")
    public ResponseEntity<List<Collection>> getCollections(@PathVariable(name = "orgId") Long orgId) {
        return projectService.getAllCollections(orgId);
    }

    @GetMapping("projects/{projectId}/collections/{collectionId}")
    public ResponseEntity<Collection> getCollectionsById(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @PathVariable(name = "projId") Long projId) {
        return projectService.getCollectionById(userToken, orgId, projId);
    }

    @GetMapping("projects/{projectId}/collections/{collectionId}/apis")
    public ResponseEntity<List<VirtualApi>> getApis(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @PathVariable(name = "projId") Long projId) {
        return projectService.getAllApis(orgId, projId);
    }

    @PostMapping("/filter/apis")
    public ResponseEntity<List<VirtualApi>> filterApis(@RequestBody VoProjectCollection voCollectionProject) {
        return projectService.getAllApis(voCollectionProject);
    }

    @PostMapping("/filter/projects")
    public ResponseEntity<Project> filterProjects(@RequestBody VoProject voOrganization) {
        return projectService.findByProjectName(voOrganization.getProjectName());
    }

    @PostMapping("/filter/{projectId}/collections")
    public ResponseEntity<Collection> filterCollectionsByName(@PathVariable(name = "orgId") Long orgId, @RequestBody VoCollection voCollection) {
        return projectService.getCollectionsByProjectIdAndCollectionName(orgId, voCollection.getCollectionName());
    }

    @GetMapping("projects/{projectId}/collections/{collectionId}/apis/{apiId}")
    public ResponseEntity<VirtualApi> getApisById(@RequestHeader(name = "user") String userToken, @PathVariable(name = "orgId") Long orgId, @PathVariable(name = "projId") Long projId, @PathVariable(name = "apiId") Long apiId) {
        return projectService.getApisById(orgId, projId, apiId);
    }

}
