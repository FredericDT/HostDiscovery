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
    @Type(type = "uuid-char")
    private UUID id;

    @ElementCollection
    private List<String> userGroupList;

    private String customLabel;

    @OneToOne(cascade = CascadeType.DETACH)
    private HostReportLog lastReportLog;

    @OneToMany(cascade = CascadeType.ALL)
    private List<HostReportLog> reportLogList;

    public HostReportLog getLastReportLog() {
        return lastReportLog;
    }

    public List<HostReportLog> getReportLogList() {
        return reportLogList;
    }

    public UUID getId() {
        return id;
    }

    public List<String> getUserGroupList() {
        return userGroupList;
    }

    public void setLastReportLog(HostReportLog lastReportLog) {
        this.lastReportLog = lastReportLog;
    }

    public void setReportLogList(List<HostReportLog> reportLogList) {
        this.reportLogList = reportLogList;
    }

    public String getCustomLabel() {
        return customLabel;
    }

    public void setCustomLabel(String customLabel) {
        this.customLabel = customLabel;
    }

    public Host(List<String> userGroupList) {
        this.userGroupList = userGroupList;
    }

    public Host() {
        this.userGroupList = Collections.emptyList();
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
                Objects.equals(userGroupList, host.userGroupList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
