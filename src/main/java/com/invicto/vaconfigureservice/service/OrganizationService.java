package com.invicto.vaconfigureservice.service;

import com.invicto.vaconfigureservice.entitiy.Organization;
import com.invicto.vaconfigureservice.model.VoOrganization;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface OrganizationService {
    public ResponseEntity<String> createOrganization(String userToken,VoOrganization voOrganization);
    public ResponseEntity<String> deleteOrganization(String userToken,Integer orgId);
    public ResponseEntity<List<Organization>> findAllOrgnizationByUser(String userToken);
}
