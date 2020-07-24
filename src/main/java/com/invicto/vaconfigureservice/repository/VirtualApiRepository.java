package com.invicto.vaconfigureservice.repository;

import com.invicto.vaconfigureservice.entitiy.Project;
import com.invicto.vaconfigureservice.entitiy.VirtualApi;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VirtualApiRepository extends CrudRepository<VirtualApi, Long> {

    public VirtualApi findByProjectAndRequestMethodAndVirtualApiPath(Project project, String method, String path);

    public VirtualApi findByVirtualApiId(Long id);

    public List<VirtualApi> findByVirtualApiNameLikeByOrderByVirtualApiId(String name);

    public List<VirtualApi> findByProjectByOrderByVirtualApiId(Project project);
    public List<VirtualApi> findByProjectAndStatusByOrderByVirtualApiId(Project project,boolean status);

    public VirtualApi findByProjectAndVirtualApiIdAndStatus(Project project, Long id, boolean status);
}
