package onl.fdt.HostDiscovery.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
public class HostReportLog implements Serializable {
    public static class Builder {

        private ZonedDateTime time;
        private String ip;
        private String hostname;

        public Builder setTime(ZonedDateTime time) {
            this.time = time;
            return this;
        }

        public Builder setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder setHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public HostReportLog build() {
            return new HostReportLog(time, ip, hostname);
        }
    }

    @Id
    @GeneratedValue
    @Type(type="uuid-char")
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

    public HostReportLog(ZonedDateTime time, String ip, String hostname) {
        this.time = time;
        this.ip = ip;
        this.hostname = hostname;
    }

    public HostReportLog() {
    }
}
