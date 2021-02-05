package onl.fdt.HostDiscovery.service;

import onl.fdt.HostDiscovery.entity.Host;

import java.util.Collection;
import java.util.List;

public interface OidcPrincipalService {
    public List<String> getUserGroup();

    public String getPrincipalName();

    public boolean hasPermission(Host host);

    public boolean hasPermission(Collection<String> userGroupList);

    public void hasPermissionOrThrowAccessDeniedException(Collection<String> userGroupList);

    public void hasPermissionOrThrowAccessDeniedException(Host host);

    public String getPicture();
}
