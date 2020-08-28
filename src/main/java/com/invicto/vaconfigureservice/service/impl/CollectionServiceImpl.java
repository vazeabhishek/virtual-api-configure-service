package com.invicto.vaconfigureservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.Collection;
import com.invicto.vaconfigureservice.exception.base.NoPermissionException;
import com.invicto.vaconfigureservice.exception.project.CollectionAlreadyExistException;
import com.invicto.vaconfigureservice.exception.project.CollectionInvalidRequest;
import com.invicto.vaconfigureservice.exception.project.CollectionNotExistException;
import com.invicto.vaconfigureservice.model.VoCollection;
import com.invicto.vaconfigureservice.repository.CollectionRepository;
import com.invicto.vaconfigureservice.response.GenericResponse;
import com.invicto.vaconfigureservice.service.CollectionService;
import com.invicto.vaconfigureservice.util.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String SUCCESS = "success";

    @Override
    public Collection createCollection(String userToken, VoCollection voCollection, Project project) {
        validateProjectRequest(voCollection);
        Collection existingCollection = collectionRepository.findByCollectionNameAndProject(voCollection.getCollectionName().toUpperCase(), project);
        if (Objects.isNull(existingCollection)) {
            Collection collection = new Collection();
            collection.setActive(true);
            collection.setCreatedBy(userToken);
            collection.setCollectionName(voCollection.getCollectionName().toUpperCase());
            collection.setCollectionOwnerUserToken(userToken);
            collection.setCreatedDate(LocalDateTime.now());
            collection.setProject(project);
            return collectionRepository.save(collection);
        } else
            throw new CollectionAlreadyExistException(voCollection.getCollectionName());
    }

    @Override
    public ResponseEntity<String> deleteCollection(Long projectId) {
        Collection collection = collectionRepository.findByCollectionId(projectId);
        if (Objects.nonNull(collection)) {
            collectionRepository.delete(collection);
            GenericResponse genericResponse = new GenericResponse(SUCCESS, String.valueOf(projectId));
            return new ResponseEntity<>(genericResponse.toJsonString(objectMapper), HttpStatus.ACCEPTED);
        } else
            throw new CollectionNotExistException(String.valueOf(projectId));
    }

    @Override
    public List<Collection> getCollectionByProject(Project project) {
        return collectionRepository.findByProject(project);
    }

    @Override
    public Collection getCollectionByProjectAndId(Project project, Long id) {
        return collectionRepository.findByProjectAndCollectionId(project, id);
    }

    @Override
    public Collection findCollectionByIdAndProject(String userToken, Long projectId, Project project) {
        Collection collection = collectionRepository.findByProjectAndCollectionId(project, projectId);
        if (Objects.nonNull(collection)) {
            if (collection.getCollectionOwnerUserToken().contentEquals(userToken)) {
                return collection;
            } else
                throw new NoPermissionException();
        } else
            throw new CollectionNotExistException(String.valueOf(projectId));
    }

    @Override
    public Collection findCollectionByNameAndProject(String projName, Project project) {
        Collection collection = collectionRepository.findByCollectionNameAndProject(projName.toUpperCase(), project);
        if (Objects.nonNull(collection))
            return collection;
        else
            throw new CollectionNotExistException(projName);
    }

    private boolean validateProjectRequest(VoCollection voCollection) {
        boolean status = false;
        if (RequestValidator.isValidProjectName(voCollection.getCollectionName()))
            status = true;
        else
            throw new CollectionInvalidRequest("Collection name not valid, Should not contain spaces or special chars", voCollection.getCollectionName());
        return status;
    }

}
