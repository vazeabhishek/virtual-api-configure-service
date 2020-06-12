package com.invicto.vaconfigureservice.repository;

import com.invicto.vaconfigureservice.entitiy.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Integer> {

    Organization findByOrgId(Integer id);

    List<Organization> findByOrgName(String orgName);

    List<Organization> findByOrgOwnerUserTokenLike(String token);
}
