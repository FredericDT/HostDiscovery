package onl.fdt.HostDiscovery.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Host implements Serializable {
    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    private HostReportLog lastReportLog;

    @ElementCollection
    private List<String> userGroupList;

    private String customLabel;

    public UUID getId() {
        return id;
    }

    public HostReportLog getLastReportLog() {
        return lastReportLog;
    }

    public List<String> getUserGroupList() {
        return userGroupList;
    }

    public String getCustomLabel() {
        return customLabel;
    }

    public void setCustomLabel(String customLabel) {
        this.customLabel = customLabel;
    }

    public Host(HostReportLog lastReportLog, List<String> userGroupList) {
        this.lastReportLog = lastReportLog;
        this.userGroupList = userGroupList;
    }

    public Host() {
        this.lastReportLog = null;
        this.userGroupList = Collections.emptyList();
    }

    public void setLastReportLog(HostReportLog lastReportLog) {
        this.lastReportLog = lastReportLog;
    }

    public void setUserGroupList(List<String> userGroupList) {
        this.userGroupList = userGroupList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return Objects.equals(id, host.id) &&
                Objects.equals(lastReportLog, host.lastReportLog) &&
                Objects.equals(userGroupList, host.userGroupList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
