package com.invicto.vaconfigureservice.repository;

import com.invicto.vaconfigureservice.entitiy.Collection;
import com.invicto.vaconfigureservice.entitiy.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends CrudRepository<Collection, Long> {

    Collection findByProjectId(Long id);

    Collection findByProjectName(String prjName);

    Collection findByProjectNameAndOrganization(String prjName, Project project);

    List<Collection> findByProjOwnerUserTokenLike(String token);

    List<Collection> findByOrganization(Project project);

    Collection findByProjectIdAndOrganization(Long projectId, Project project);

    Collection findByOrganizationAndProjectId(Project project, Long id);

}
