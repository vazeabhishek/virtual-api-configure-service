package com.invicto.vaconfigureservice.repository;

import com.invicto.vaconfigureservice.entitiy.Collection;
import com.invicto.vaconfigureservice.entitiy.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends CrudRepository<Collection, Long> {

    Collection findByCollectionId(Long id);
    Collection findByCollectionNameAndProject(String collectionName, Project project);
    List<Collection> findByProject(Project project);
    Collection findByProjectAndCollectionId(Project project, Long id);

}
