package onl.fdt.HostDiscovery.service.impl;

import onl.fdt.HostDiscovery.entity.Host;
import onl.fdt.HostDiscovery.entity.HostReportLog;
import onl.fdt.HostDiscovery.entity.repository.HostReportLogRepository;
import onl.fdt.HostDiscovery.entity.repository.HostRepository;
import onl.fdt.HostDiscovery.exception.NotFoundException;
import onl.fdt.HostDiscovery.service.HostDiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class HostDiscoveryServiceImpl implements HostDiscoveryService {
    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private HostReportLogRepository hostReportLogRepository;

    @Transactional
    @Override
    public HostReportLog register(UUID hostId, String hostname, String clientIp) {
        Host host = hostRepository.findById(hostId).orElseThrow(() -> new NotFoundException(String.format("host %s not found", hostId.toString())));
        HostReportLog hostReportLog = new HostReportLog.Builder()
                .setHost(host)
                .setHostname(hostname)
                .setTime(ZonedDateTime.now())
                .setIp(clientIp)
                .build();
        host.setLastReportLog(hostReportLog);
        hostReportLogRepository.save(hostReportLog);
        hostRepository.save(host);
        return hostReportLog;
    }
}
