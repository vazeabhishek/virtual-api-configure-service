package com.invicto.vaconfigureservice.repository;

import com.invicto.vaconfigureservice.entitiy.Organization;
import com.invicto.vaconfigureservice.entitiy.Project;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByProjectId(Long id);

    Project findByProjectName(String prjName);

    Project findByProjectNameAndOrganization(String prjName, Organization organization);

    List<Project> findByProjOwnerUserTokenLike(String token);

    List<Project> findByOrganization(Organization organization);

    Project findByProjectIdAndOrganization(Long projectId, Organization organization);

    Project findByOrganizationAndProjectId(Organization organization, Long id);

}
