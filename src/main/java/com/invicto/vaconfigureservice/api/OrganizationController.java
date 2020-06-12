package com.invicto.vaconfigureservice.api;

import com.invicto.vaconfigureservice.entitiy.Organization;
import com.invicto.vaconfigureservice.model.VoOrganization;
import com.invicto.vaconfigureservice.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/organizations")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<String> createOrganization(@RequestHeader(name = "user") String userToken,@RequestBody VoOrganization voOrganization){
        return organizationService.createOrganization(userToken,voOrganization);
    }

    @DeleteMapping("/{orgId}")
    public ResponseEntity<String> deleteOrganization(@RequestHeader(name = "user") String userToken,@PathVariable(name = "orgId") Integer orgId){
        return organizationService.deleteOrganization(userToken,orgId);
    }

    @GetMapping
    public ResponseEntity<List<Organization>> getOrganization(@RequestHeader(name = "user") String userToken){
        return organizationService.findAllOrgnizationByUser(userToken);
    }

}
