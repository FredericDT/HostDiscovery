package onl.fdt.HostDiscovery.controller;

import onl.fdt.HostDiscovery.service.ResolveClientIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("api/v1")
@RestController
public class IpController {
    @Autowired
    ResolveClientIpService resolveClientIpService;

    @GetMapping("ip")
    public String getClientIp(HttpServletRequest request) {
        return resolveClientIpService.resolveClientIp(request);
    }
}
