package onl.fdt.HostDiscovery.service;

import javax.servlet.http.HttpServletRequest;

public interface ResolveClientIpService {
    public String resolveClientIp(HttpServletRequest request);
}
