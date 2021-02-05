package onl.fdt.HostDiscovery.payload.request;

import java.io.Serializable;

public class HostRegisterRequestPayload implements Serializable {
    private String hostname;

    public String getHostname() {
        return hostname;
    }
}
