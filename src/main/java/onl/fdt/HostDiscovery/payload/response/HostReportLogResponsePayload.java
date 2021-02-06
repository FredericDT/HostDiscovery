package onl.fdt.HostDiscovery.payload.response;

import onl.fdt.HostDiscovery.entity.HostReportLog;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

public class HostReportLogResponsePayload implements Serializable {

    public static HostReportLogResponsePayload build(HostReportLog hostReportLog) {
        HostReportLogResponsePayload hostReportLogResponsePayload = new HostReportLogResponsePayload();

        hostReportLogResponsePayload.id = hostReportLog.getId();
        hostReportLogResponsePayload.hostname = hostReportLog.getHostname();
        hostReportLogResponsePayload.ip = hostReportLog.getIp();
        hostReportLogResponsePayload.time = hostReportLog.getTime();

        return hostReportLogResponsePayload;
    }

    private UUID id;

    private ZonedDateTime time;
    private String ip;
    private String hostname;

    public UUID getId() {
        return id;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public String getIp() {
        return ip;
    }

    public String getHostname() {
        return hostname;
    }
}