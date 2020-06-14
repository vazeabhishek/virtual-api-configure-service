package com.invicto.vaconfigureservice.service;

import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.model.VoVirtualApi;
import org.springframework.http.ResponseEntity;

public interface VirtualApiService {
    public ResponseEntity<String> createApi(String user,VoVirtualApi voVirtualApi);
    public ResponseEntity<String> deleteApi(String user,String apiId);
    public ResponseEntity<String> getAllApisFromProject(Project project);
    public ResponseEntity<String> getApiSpecs(VirtualApi virtualApi);
}
