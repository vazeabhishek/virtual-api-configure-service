package com.invicto.vaconfigureservice.service.impl;

import com.invicto.vaconfigureservice.entitiy.Organization;
import com.invicto.vaconfigureservice.model.VoOrganization;
import com.invicto.vaconfigureservice.repository.OrganizationRepository;
import com.invicto.vaconfigureservice.service.OrganizationService;
import com.invicto.vaconfigureservice.util.TokenGenrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public ResponseEntity<String> createOrganization(String userToken,VoOrganization voOrganization) {
        Organization organization = new Organization();
        organization.setOrgOwnerUserToken(userToken);
        organization.setCreatedBy(userToken);
        organization.setCreatedDate(LocalDateTime.now());
        organization.setOrgName(voOrganization.getOrganizationName());
        organization.setActive(true);
        organization.setOrgToken(TokenGenrator.randomTokenGenrator());
        System.out.println("Id is "+organization.getOrgId());
        organizationRepository.save(organization);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteOrganization(String userToken,Integer orgId) {
        Organization organization = organizationRepository.findByOrgId(orgId);
        organizationRepository.delete(organization);
        return null;
    }

    @Override
    public ResponseEntity<List<Organization>> findAllOrgnizationByUser(String userToken) {
        List<Organization> organizationList = organizationRepository.findByOrgOwnerUserTokenLike(userToken);
        return new ResponseEntity<List<Organization>>(organizationList, HttpStatus.OK);
    }
}
