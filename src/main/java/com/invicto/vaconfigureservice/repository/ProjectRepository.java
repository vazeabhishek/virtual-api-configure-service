package com.invicto.vaconfigureservice.repository;

import com.invicto.vaconfigureservice.entitiy.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByProjectId(Long id);
    Project findByProjectName(String orgName);
    List<Project> findByProjectOwnerUserTokenLike(String token);
}
