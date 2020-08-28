package com.invicto.vaconfigureservice.repository;

import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import com.invicto.vaconfigureservice.entitiy.VirtualApiSpecs;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VirtualApiSpecsRepository extends CrudRepository<VirtualApiSpecs, String> {
    public List<VirtualApiSpecs> findByVirtualApi(VirtualApi virtualApi);
}
