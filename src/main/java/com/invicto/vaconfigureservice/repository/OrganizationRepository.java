package com.invicto.vaconfigureservice.repository;

import com.invicto.vaconfigureservice.entitiy.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    Organization findByOrgId(Long id);

    Organization findByOrgName(String orgName);

    List<Organization> findByOrgOwnerUserTokenLikeByOrderByOrgId(String token);

}
