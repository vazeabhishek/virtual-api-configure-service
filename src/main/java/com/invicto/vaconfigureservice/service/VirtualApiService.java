package com.invicto.vaconfigureservice.service;

import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.entitiy.VirtualApiSpecs;
import com.invicto.vaconfigureservice.model.VoVirtualApi;

import java.util.List;

public interface VirtualApiService {
     String  createApi(String user,VoVirtualApi voVirtualApi);
     Boolean deleteApi(String user,String apiId);
     List<VirtualApi> getAllApisFromProject(Project project);
     List<VirtualApiSpecs> getApiSpecs(VirtualApi virtualApi);
     VirtualApi fetchApiByProjectAndId(Project project, String id);
}
