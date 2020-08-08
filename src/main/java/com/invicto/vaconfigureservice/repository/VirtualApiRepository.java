package com.invicto.vaconfigureservice.repository;

import com.invicto.vaconfigureservice.entitiy.Collection;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VirtualApiRepository extends CrudRepository<VirtualApi, Long> {

    public VirtualApi findByCollectionAndRequestMethodAndVirtualApiPath(Collection collection, String method, String path);
    public VirtualApi findByVirtualApiId(Long id);
    public List<VirtualApi> findByCollection(Collection collection);
    public List<VirtualApi> findByCollectionAndStatus(Collection collection, boolean status);
    public VirtualApi findByCollectionAndVirtualApiIdAndStatus(Collection collection, Long id, boolean status);
}
