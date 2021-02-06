package onl.fdt.HostDiscovery.service;

import onl.fdt.HostDiscovery.entity.Host;
import onl.fdt.HostDiscovery.entity.HostReportLog;

import java.util.List;
import java.util.UUID;

public interface HostService {

    public Host get(UUID id);

    public Host add(Host host);

    public boolean delete(UUID id);

    public Host update(Host host);

    public List<Host> all(List<String> userGroupList);

    public List<Host> all();

    public List<HostReportLog> getHostReportLogByHostId(UUID id);

}
