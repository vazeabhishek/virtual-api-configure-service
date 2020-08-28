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
    public ResponseEntity<String> createCollection(@RequestHeader(name = "user") String userToken, @PathVariable(name = "projectId") Long projectId, @RequestBody VoCollection voCollection) {
        return projectService.createCollection(userToken, projectId, voCollection);
    }

    @PostMapping("projects/{projectId}/collections/{collectionId}/apis")
    public ResponseEntity<String> createApi(@RequestHeader(name = "user") String userToken, @PathVariable(name = "projectId") Long projectId, @PathVariable(name = "collectionId") Long collectionId, @RequestBody VoVirtualApi voVirtualApi) {
        return projectService.createApi(userToken, projectId, collectionId, voVirtualApi);
    }

    @DeleteMapping("projects/{projectId}")
    public ResponseEntity<String> deleteProject(@RequestHeader(name = "user") String userToken, @PathVariable(name = "projectId") Long projectId) {
        return projectService.deleteProject(userToken, projectId);
    }

    @DeleteMapping("projects/{projectId}/collections/{collectionId}")
    public ResponseEntity<String> deleteCollection(@RequestHeader(name = "user") String userToken, @PathVariable(name = "projectId") Long projectId, @PathVariable(name = "collectionId") Long collectionId) {
        return projectService.deleteCollection(userToken, projectId, collectionId);
    }

    @DeleteMapping("projects/{projectId}/collections/{collectionId}/apis/{apiId}")
    public ResponseEntity<String> deleteById(@RequestHeader(name = "user") String userToken, @PathVariable(name = "projectId") Long projectId, @PathVariable(name = "collectionId") Long collectionId, @PathVariable(name = "apiId") Long apiId) {
        return projectService.deleteApiById(userToken, projectId, collectionId, apiId);
    }

    @GetMapping("projects/{projectId}/collections/{collectionId}/apis/{apiId}/toggle")
    public ResponseEntity<String> toggleApi(@RequestHeader(name = "user") String userToken, @PathVariable(name = "projectId") Long projectId, @PathVariable(name = "collectionId") Long collectionId, @PathVariable(name = "apiId") Long apiId) {
        return projectService.toggleApi(userToken, projectId, collectionId, apiId);
    }

    @GetMapping("projects")
    public ResponseEntity<List<Project>> getProject(@RequestHeader(name = "user") String userToken) {
        return projectService.findAllProjectsByUser(userToken);
    }

    @GetMapping("projects/{projectId}")
    public ResponseEntity<Project> getProjectDetails(@RequestHeader(name = "user") String userToken, @PathVariable(name = "projectId") Long projectId) {
        return projectService.findByProjectId(userToken, projectId);
    }

    @GetMapping("projects/{projectId}/collections")
    public ResponseEntity<List<Collection>> getCollections(@PathVariable(name = "projectId") Long projectId) {
        return projectService.getAllCollections(projectId);
    }

    @GetMapping("projects/{projectId}/collections/{collectionId}")
    public ResponseEntity<Collection> getCollectionsById(@RequestHeader(name = "user") String userToken, @PathVariable(name = "projectId") Long projectId, @PathVariable(name = "collectionId") Long collectionId) {
        return projectService.getCollectionById(userToken, projectId, collectionId);
    }

    @GetMapping("projects/{projectId}/collections/{collectionId}/apis")
    public ResponseEntity<List<VirtualApi>> getApis(@RequestHeader(name = "user") String userToken, @PathVariable(name = "projectId") Long projectId, @PathVariable(name = "collectionId") Long collectionId) {
        return projectService.getAllApis(projectId, collectionId);
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
    public ResponseEntity<Collection> filterCollectionsByName(@PathVariable(name = "projectId") Long projectId, @RequestBody VoCollection voCollection) {
        return projectService.getCollectionsByProjectIdAndCollectionName(projectId, voCollection.getCollectionName());
    }

    @GetMapping("projects/{projectId}/collections/{collectionId}/apis/{apiId}")
    public ResponseEntity<VirtualApi> getApisById(@RequestHeader(name = "user") String userToken, @PathVariable(name = "projectId") Long projectId, @PathVariable(name = "collectionId") Long collectionId, @PathVariable(name = "apiId") Long apiId) {
        return projectService.getApisById(projectId, collectionId, apiId);
    }

}
