package onl.fdt.HostDiscovery.service.impl;

import onl.fdt.HostDiscovery.entity.Host;
import onl.fdt.HostDiscovery.entity.HostReportLog;
import onl.fdt.HostDiscovery.entity.repository.HostRepository;
import onl.fdt.HostDiscovery.exception.NotFoundException;
import onl.fdt.HostDiscovery.service.HostService;
import onl.fdt.HostDiscovery.service.OidcPrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class HostServiceImpl implements HostService {

    @Autowired
    HostRepository hostRepository;

    @Autowired
    private OidcPrincipalService oidcPrincipalService;

    @Override
    public Host get(UUID id) {
        Host host = hostRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("host %s not found", id.toString())));

        oidcPrincipalService.hasPermissionOrThrowAccessDeniedException(host);

        return host;
    }

    @Override
    public Host add(Host host) {
        Host realHostCandidate = new Host();

        if (host == null || host.getUserGroupList() == null || host.getUserGroupList().size() == 0) {
            realHostCandidate.setUserGroupList(Collections.singletonList(oidcPrincipalService.getPrincipalName()));
        } else {
            realHostCandidate.setUserGroupList(host.getUserGroupList());
        }
        if (host != null) {
            realHostCandidate.setCustomLabel(host.getCustomLabel());
        }

        return hostRepository.save(realHostCandidate);
    }

    @Override
    public boolean delete(UUID id) {
        Host host = hostRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("host %s not found", id.toString())));

        oidcPrincipalService.hasPermissionOrThrowAccessDeniedException(host);

        hostRepository.delete(host);
        return true;
    }

    @Override
    public Host update(Host host) {
        Host oldHost = hostRepository.findById(host.getId()).orElseThrow(() -> new NotFoundException(String.format("host %s not found", host.getId().toString())));
        oidcPrincipalService.hasPermissionOrThrowAccessDeniedException(oldHost);
        oidcPrincipalService.hasPermissionOrThrowAccessDeniedException(host);

        if (host.getUserGroupList() != null && host.getUserGroupList().size() > 0) {
            oldHost.setUserGroupList(host.getUserGroupList());
        }
        oldHost.setCustomLabel(host.getCustomLabel());

        return hostRepository.save(oldHost);
    }

    @Override
    public List<Host> all(List<String> userGroupList) {
        oidcPrincipalService.hasPermissionOrThrowAccessDeniedException(userGroupList);
        return hostRepository.findDistinctByUserGroupListIn(userGroupList);
    }

    @Override
    public List<Host> all() {
        return all(oidcPrincipalService.getUserGroup());
    }

    @Override
    public List<HostReportLog> getHostReportLogByHostId(UUID id) {
        Host host = get(id);
        List<HostReportLog> reportLogList = host.getReportLogList();
        if (reportLogList.size() > 100) {
            reportLogList = reportLogList.subList(0, 100);
        }
        return reportLogList;
    }

}
