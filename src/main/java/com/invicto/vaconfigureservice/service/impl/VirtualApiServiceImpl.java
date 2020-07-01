package com.invicto.vaconfigureservice.service.impl;

import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.entitiy.VirtualApiSpecs;
import com.invicto.vaconfigureservice.model.VoVirtualApi;
import com.invicto.vaconfigureservice.repository.VirtualApiRepository;
import com.invicto.vaconfigureservice.repository.VirtualApiSpecsRepository;
import com.invicto.vaconfigureservice.service.VirtualApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VirtualApiServiceImpl implements VirtualApiService {

    @Autowired
    private VirtualApiRepository virtualApiRepository;
    @Autowired
    private VirtualApiSpecsRepository virtualApiSpecsRepository;

    @Override
    public String createApi(String user, VoVirtualApi voVirtualApi) {
        VirtualApi virtualApi = new VirtualApi();
        virtualApi.setCreatedBy(user);
        virtualApi.setCreatedDate(LocalDateTime.now());
        virtualApi.setVirtualApiName(voVirtualApi.getName());
        voVirtualApi.setRequestMethod(virtualApi.getRequestMethod());
        List<VirtualApiSpecs> virtualApiSpecsList = new ArrayList<>();
        voVirtualApi.getVoVirtualApiSpecList().stream().forEach(voVirtualApiSpec -> {
            VirtualApiSpecs virtualApiSpecs = new VirtualApiSpecs();
            virtualApiSpecs.setCreatedBy(user);
            virtualApiSpecs.setCreatedDate(LocalDateTime.now());
            virtualApiSpecs.setRequestPayload(voVirtualApiSpec.getRequestPayLoad());
            virtualApiSpecs.setResponsePayload(voVirtualApiSpec.getResponsePayload());
            virtualApiSpecsList.add(virtualApiSpecs);
        });
        virtualApi.setVirtualApiSpecs(virtualApiSpecsList);
        virtualApiRepository.save(virtualApi);
        return ""+virtualApi.getVirtualApiId();
    }

    @Override
    public Boolean deleteApi(String user, String apiId) {
        return null;
    }

    @Override
    public List<VirtualApi> getAllApisFromProject(Project project) {
        return null;
    }

    @Override
    public List<VirtualApiSpecs> getApiSpecs(VirtualApi virtualApi) {
        return null;
    }

    @Override
    public VirtualApi fetchApiByProjectAndId(Project project, String id) {
        return virtualApiRepository.findByProjectAndVirtualApiId(project, id);
    }
}
