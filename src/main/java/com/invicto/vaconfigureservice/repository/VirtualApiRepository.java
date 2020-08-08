package com.invicto.vaconfigureservice.repository;

import com.invicto.vaconfigureservice.entitiy.Collection;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VirtualApiRepository extends CrudRepository<VirtualApi, Long> {

    public VirtualApi findByProjectAndRequestMethodAndVirtualApiPath(Collection collection, String method, String path);

    public VirtualApi findByVirtualApiId(Long id);

    public List<VirtualApi> findByVirtualApiNameLike(String name);

    public List<VirtualApi> findByProject(Collection collection);
    public List<VirtualApi> findByProjectAndStatus(Collection collection, boolean status);

    public VirtualApi findByProjectAndVirtualApiIdAndStatus(Collection collection, Long id, boolean status);
}
