package onl.fdt.HostDiscovery.controller;

import onl.fdt.HostDiscovery.entity.Host;
import onl.fdt.HostDiscovery.payload.request.HostRegisterRequestPayload;
import onl.fdt.HostDiscovery.payload.response.HostReportLogResponsePayload;
import onl.fdt.HostDiscovery.payload.response.HostResponsePayload;
import onl.fdt.HostDiscovery.service.HostDiscoveryService;
import onl.fdt.HostDiscovery.service.HostService;
import onl.fdt.HostDiscovery.service.ResolveClientIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/host")
public class HostController {
    @Autowired
    private ResolveClientIpService resolveClientIpService;
    @Autowired
    private HostDiscoveryService hostDiscoveryService;
    @Autowired
    private HostService hostService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "register/{id}")
    public HostReportLogResponsePayload register(@PathVariable UUID id, @RequestBody HostRegisterRequestPayload hostRegisterRequestPayload, HttpServletRequest request) {
        return HostReportLogResponsePayload.build(
                hostDiscoveryService.register(
                        id,
                        hostRegisterRequestPayload.getHostname(),
                        resolveClientIpService.resolveClientIp(request)
                )
        );
    }

    @GetMapping(value = "{id}")
    public HostResponsePayload get(@PathVariable UUID id) {
        return HostResponsePayload.build(
                hostService.get(id)
        );
    }

    @GetMapping(value = "{id}/reportLog")
    public List<HostReportLogResponsePayload> getReportLog(@PathVariable UUID id) {
        return hostService.getHostReportLogByHostId(id)
                .parallelStream()
                .map(HostReportLogResponsePayload::build)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(value = "{id}")
    public boolean delete(@PathVariable UUID id) {
        return hostService.delete(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HostResponsePayload put(@RequestBody Host host) {
        return HostResponsePayload.build(
                hostService.add(host)
        );
    }

    @GetMapping
    public List<HostResponsePayload> get() {
        return hostService.all()
                .parallelStream().map(HostResponsePayload::build)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "{id}")
    public HostResponsePayload update(@PathVariable UUID id, @RequestBody Host host) {
        return HostResponsePayload.build(
                hostService.update(host)
        );
    }

}
