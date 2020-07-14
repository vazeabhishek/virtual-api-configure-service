package com.invicto.vaconfigureservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invicto.vaconfigureservice.entitiy.Organization;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.entitiy.VirtualApiSpecs;
import com.invicto.vaconfigureservice.exception.ApiNotExistException;
import com.invicto.vaconfigureservice.model.VoVirtualApi;
import com.invicto.vaconfigureservice.repository.VirtualApiRepository;
import com.invicto.vaconfigureservice.repository.VirtualApiSpecsRepository;
import com.invicto.vaconfigureservice.response.GenericResponse;
import com.invicto.vaconfigureservice.service.VirtualApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class VirtualApiServiceImpl implements VirtualApiService {

    @Autowired
    private VirtualApiRepository virtualApiRepository;
    @Autowired
    private VirtualApiSpecsRepository virtualApiSpecsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String SUCCESS = "success";

    @Override
    public ResponseEntity<String> createApi(String user, Project project, VoVirtualApi voVirtualApi) {
        VirtualApi virtualApi = new VirtualApi();
        virtualApi.setProject(project);
        virtualApi.setCreatedBy(user);
        virtualApi.setCreatedDate(LocalDateTime.now());
        virtualApi.setVirtualApiName(voVirtualApi.getApiName());
        virtualApi.setVirtualApiPath(voVirtualApi.getPath());
        virtualApi.setRequestMethod(voVirtualApi.getMethod());
        List<VirtualApiSpecs> virtualApiSpecsList = new ArrayList<>();
        voVirtualApi.getVoVirtualApiSpecList().stream().forEach(voVirtualApiSpec -> {
            VirtualApiSpecs virtualApiSpecs = new VirtualApiSpecs();
            virtualApiSpecs.setCreatedBy(user);
            virtualApiSpecs.setCreatedDate(LocalDateTime.now());
            virtualApiSpecs.setRequestPayload(voVirtualApiSpec.getReqPayload());
            virtualApiSpecs.setResponsePayload(voVirtualApiSpec.getRespPayload());
            virtualApiSpecs.setVirtualApi(virtualApi);
            virtualApiSpecsList.add(virtualApiSpecs);
        });
        virtualApi.setVirtualApiSpecs(virtualApiSpecsList);
        virtualApiRepository.save(virtualApi);
        GenericResponse genericResponse = new GenericResponse(SUCCESS, String.valueOf(virtualApi.getVirtualApiId()));
        return new ResponseEntity<>(genericResponse.toJsonString(objectMapper), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteApi(String user, Long apiId) {
        VirtualApi virtualApi = virtualApiRepository.findByVirtualApiId(apiId);
        if (Objects.nonNull(virtualApi)) {
            virtualApiRepository.delete(virtualApi);
            GenericResponse genericResponse = new GenericResponse(SUCCESS, String.valueOf(apiId));
            return new ResponseEntity<>(genericResponse.toJsonString(objectMapper), HttpStatus.ACCEPTED);
        } else {
            throw new ApiNotExistException(String.valueOf(apiId));
        }
    }

    @Override
    public List<VirtualApi> getAllApisFromProject(Project project) {
        return virtualApiRepository.findByProject(project);
    }

    @Override
    public List<VirtualApiSpecs> getApiSpecs(VirtualApi virtualApi) {
        return virtualApiSpecsRepository.findByVirtualApi(virtualApi);
    }

    @Override
    public VirtualApi fetchApiByProjectAndId(Project project, Long id) {
        return virtualApiRepository.findByProjectAndVirtualApiId(project, id);
    }
}
