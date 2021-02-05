package onl.fdt.HostDiscovery.payload.response;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserResponsePayload {

    private String userName;
    private List<String> userGroupList;
//    private String picture;

//    public String getPicture() {
//        return picture;
//    }

    public String getUserName() {
        return userName;
    }

    public List<String> getUserGroupList() {
        return userGroupList;
    }

    public UserResponsePayload(String userName, List<String> userGroupList) {
        this.userName = userName;
        this.userGroupList = userGroupList;
    }
}
