package onl.fdt.HostDiscovery.service;

import onl.fdt.HostDiscovery.entity.HostReportLog;

import java.util.UUID;

public interface HostDiscoveryService {
    public HostReportLog register(UUID hostId, String hostname, String ip);
}
