package com.invicto.vaconfigureservice.repository;

        import com.invicto.vaconfigureservice.entitiy.Project;
        import com.invicto.vaconfigureservice.entitiy.VirtualApi;
        import org.springframework.data.repository.CrudRepository;

        import java.util.List;

public interface VirtualApiRepository extends CrudRepository<VirtualApi, Long> {
    public VirtualApi findByVirtualApiId(Long id);

    public List<VirtualApi> findByVirtualApiNameLike(String name);

    public List<VirtualApi> findByProject(Project project);

    public VirtualApi findByProjectAndVirtualApiIdAndStatus(Project project, Long id, boolean status);
}
