package onl.fdt.HostDiscovery.entity.repository;

import onl.fdt.HostDiscovery.entity.Host;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface HostRepository extends JpaRepository<Host, UUID> {

    List<Host> findDistinctByUserGroupListIn(Collection<String> userGroupList);

}
