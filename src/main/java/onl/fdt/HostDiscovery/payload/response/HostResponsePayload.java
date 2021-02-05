package onl.fdt.HostDiscovery.payload.response;

import onl.fdt.HostDiscovery.entity.Host;
import onl.fdt.HostDiscovery.entity.HostReportLog;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class HostResponsePayload implements Serializable {

    private UUID id;

    private HostReportLogResponsePayload lastReportLog;

    private List<String> userGroupList;

    private String customLabel;

    public static HostResponsePayload build(Host host) {
        HostResponsePayload payload = new HostResponsePayload();
        payload.id = host.getId();
        payload.userGroupList = host.getUserGroupList();
        payload.customLabel = host.getCustomLabel();

        HostReportLog lastHostReportLog = host.getLastReportLog();

        if (lastHostReportLog != null) {
            payload.lastReportLog = HostReportLogResponsePayload.build(lastHostReportLog);
        }

        return payload;

    }

    public UUID getId() {
        return id;
    }

    public HostReportLogResponsePayload getLastReportLog() {
        return lastReportLog;
    }

    public List<String> getUserGroupList() {
        return userGroupList;
    }

    public String getCustomLabel() {
        return customLabel;
    }
}
