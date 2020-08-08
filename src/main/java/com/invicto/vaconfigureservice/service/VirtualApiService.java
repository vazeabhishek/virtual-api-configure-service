package com.invicto.vaconfigureservice.service;

import com.invicto.vaconfigureservice.entitiy.Collection;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.entitiy.VirtualApiSpecs;
import com.invicto.vaconfigureservice.model.VoVirtualApi;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VirtualApiService {
     ResponseEntity<String> createApi(String user, Collection collection, VoVirtualApi voVirtualApi);
     ResponseEntity<String> deleteApi(String user,Long apiId);
     ResponseEntity<String> toggleApi(String user,Long apiId);
     List<VirtualApi> getAllApisFromProject(Collection collection);
     List<VirtualApi> getAllActiveApisFromProject(Collection collection);
     List<VirtualApiSpecs> getApiSpecs(VirtualApi virtualApi);
     VirtualApi fetchApiByProjectAndId(Collection collection, Long id);
}
