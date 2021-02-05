package onl.fdt.HostDiscovery.entity.repository;

import onl.fdt.HostDiscovery.entity.Host;
import onl.fdt.HostDiscovery.entity.HostReportLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HostReportLogRepository extends JpaRepository<HostReportLog, UUID> {

    List<HostReportLog> findByHostOrderByTimeDesc(Host host);
}
