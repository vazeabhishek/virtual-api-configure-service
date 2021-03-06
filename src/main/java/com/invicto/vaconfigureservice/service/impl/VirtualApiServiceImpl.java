package com.invicto.vaconfigureservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invicto.vaconfigureservice.entitiy.Collection;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.entitiy.VirtualApiSpecs;
import com.invicto.vaconfigureservice.exception.api.ApiAlreadyExistException;
import com.invicto.vaconfigureservice.exception.api.ApiInvalidRequest;
import com.invicto.vaconfigureservice.exception.api.ApiNotExistException;
import com.invicto.vaconfigureservice.model.VoVirtualApi;
import com.invicto.vaconfigureservice.repository.VirtualApiRepository;
import com.invicto.vaconfigureservice.repository.VirtualApiSpecsRepository;
import com.invicto.vaconfigureservice.response.GenericResponse;
import com.invicto.vaconfigureservice.service.VirtualApiService;
import com.invicto.vaconfigureservice.util.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
class VirtualApiServiceImpl implements VirtualApiService {

    @Autowired
    private VirtualApiRepository virtualApiRepository;
    @Autowired
    private VirtualApiSpecsRepository virtualApiSpecsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${com.invicto.host-service-url}")
    private String serverUrl;

    private final String SUCCESS = "success";

    @Override
    public ResponseEntity<String> createApi(String user, Collection collection, VoVirtualApi voVirtualApi) {
        validateApiRequest(voVirtualApi);
        VirtualApi existingVirtualApi = virtualApiRepository.findByCollectionAndRequestMethodAndVirtualApiPath(collection, voVirtualApi.getMethod(), voVirtualApi.getPath());
        if (Objects.isNull(existingVirtualApi)) {
            VirtualApi virtualApi = new VirtualApi();
            virtualApi.setCollection(collection);
            virtualApi.setCreatedBy(user);
            virtualApi.setCreatedDate(LocalDateTime.now());
            virtualApi.setVirtualApiName(voVirtualApi.getApiName());
            virtualApi.setVirtualApiPath(voVirtualApi.getPath());
            virtualApi.setRequestMethod(voVirtualApi.getMethod());
            virtualApi.setAvailableAt(buildHostPath(collection, voVirtualApi.getPath()));
            virtualApi.setStatus(true);
            List<VirtualApiSpecs> virtualApiSpecsList = new ArrayList<>();
            voVirtualApi.getVoVirtualApiSpecList().stream().forEach(voVirtualApiSpec -> {
                VirtualApiSpecs virtualApiSpecs = new VirtualApiSpecs();
                virtualApiSpecs.setCreatedBy(user);
                virtualApiSpecs.setCreatedDate(LocalDateTime.now());
                virtualApiSpecs.setRequestPayload(removeUnnecessaryChracters(voVirtualApiSpec.getReqPayload()));
                virtualApiSpecs.setRequestHeaders(voVirtualApiSpec.getReqHeaders());
                virtualApiSpecs.setRequestPayloadOriginal(voVirtualApiSpec.getReqPayload());
                virtualApiSpecs.setResponsePayload(voVirtualApiSpec.getRespPayload());
                virtualApiSpecs.setResponseHeaders(voVirtualApiSpec.getRespHeaders());
                virtualApiSpecs.setResponseCode(voVirtualApiSpec.getHttpStatus());
                virtualApiSpecs.setVirtualApi(virtualApi);
                virtualApiSpecsList.add(virtualApiSpecs);
            });
            virtualApi.setVirtualApiSpecs(virtualApiSpecsList);
            virtualApiRepository.save(virtualApi);
            GenericResponse genericResponse = new GenericResponse(SUCCESS, String.valueOf(virtualApi.getVirtualApiId()));
            return new ResponseEntity<>(genericResponse.toJsonString(objectMapper), HttpStatus.CREATED);
        } else
            throw new ApiAlreadyExistException(voVirtualApi.getPath());
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
    public ResponseEntity<String> toggleApi(String user, Long apiId) {
        VirtualApi virtualApi = virtualApiRepository.findByVirtualApiId(apiId);
        if (Objects.nonNull(virtualApi)) {
            if (virtualApi.isStatus())
                virtualApi.setStatus(false);
            else
                virtualApi.setStatus(true);
            virtualApiRepository.save(virtualApi);
            GenericResponse genericResponse = new GenericResponse(SUCCESS, String.valueOf(apiId));
            return new ResponseEntity<>(genericResponse.toJsonString(objectMapper), HttpStatus.ACCEPTED);
        } else {
            throw new ApiNotExistException(String.valueOf(apiId));
        }
    }

    @Override
    public List<VirtualApi> getAllApisFromProject(Collection collection) {
        return virtualApiRepository.findByCollection(collection);
    }

    @Override
    public List<VirtualApi> getAllActiveApisFromProject(Collection collection) {
        return virtualApiRepository.findByCollectionAndStatus(collection, true);
    }

    @Override
    public List<VirtualApiSpecs> getApiSpecs(VirtualApi virtualApi) {
        return virtualApiSpecsRepository.findByVirtualApi(virtualApi);
    }

    @Override
    public VirtualApi fetchApiByProjectAndId(Collection collection, Long id) {
        return virtualApiRepository.findByCollectionAndVirtualApiIdAndStatus(collection, id, true);
    }

    private String buildHostPath(Collection collection, String apiPath) {
        if (!apiPath.startsWith("/"))
            apiPath = "/" + apiPath;
        String apiUrl = serverUrl + "/" + collection.getProject().getProjectName().trim().toLowerCase() + "/" + collection.getCollectionName().trim().toLowerCase() + "" + apiPath;
        return apiUrl;
    }

    private String removeUnnecessaryChracters(String s) {
        return s.replaceAll("\\s+", "");
    }

    private boolean validateApiRequest(VoVirtualApi voVirtualApi) {
        boolean status = false;
        if (RequestValidator.isValidApiMethod(voVirtualApi.getMethod()))
            status = true;
        else
            throw new ApiInvalidRequest("Method name not valid, Should not contain spaces or special chars", voVirtualApi.getMethod());

        if (RequestValidator.isValidPath(voVirtualApi.getPath()))
            status = true;
        else
            throw new ApiInvalidRequest("Path is not valid, Ex: /employee/1", voVirtualApi.getPath());
        return status;
    }
}
