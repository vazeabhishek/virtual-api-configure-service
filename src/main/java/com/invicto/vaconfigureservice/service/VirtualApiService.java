package com.invicto.vaconfigureservice.service;

import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.entitiy.VirtualApiSpecs;
import com.invicto.vaconfigureservice.model.VoVirtualApi;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VirtualApiService {
     ResponseEntity<String> createApi(String user,Project project, VoVirtualApi voVirtualApi);
     ResponseEntity<String> deleteApi(String user,Long apiId);
     List<VirtualApi> getAllApisFromProject(Project project);
     List<VirtualApiSpecs> getApiSpecs(VirtualApi virtualApi);
     VirtualApi fetchApiByProjectAndId(Project project, Long id);
}
