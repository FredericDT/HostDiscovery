package onl.fdt.HostDiscovery.service.impl;

import onl.fdt.HostDiscovery.entity.Host;
import onl.fdt.HostDiscovery.service.OidcPrincipalService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class OidcPrincipalServiceImpl implements OidcPrincipalService {

    private OidcUser getOidcUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object user =  authentication.getPrincipal();

        Assert.isInstanceOf(OidcUser.class, user, "Principal not an OidcUser");

        return (OidcUser) user;
    }

    private Map<String, Object> getAttributes() {
        OidcUser user = getOidcUser();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        GrantedAuthority grantedAuthority = authorities.parallelStream().filter(o -> {
            return "ROLE_USER".equals(((GrantedAuthority) o).getAuthority());
        }).findFirst().orElseThrow(() -> new AccessDeniedException("no authority ROLE_USER"));

        Assert.isInstanceOf(OidcUserAuthority.class, grantedAuthority, "ROLE_USER not an OidcUserAuthority");

        OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) grantedAuthority;

        Map<String, Object> attributes = oidcUserAuthority.getAttributes();

        return attributes;
    }

    @Override
    public List<String> getUserGroup() {

        Map<String, Object> attributes = getAttributes();

        Object groupsObject = attributes.get("groups");

        Assert.isInstanceOf(List.class, groupsObject, "groups not a list");

        List<String> groups = new ArrayList<>();

        groups.add(getPrincipalName());
        groups.addAll((List<String>) groupsObject);

        return groups;
    }

    @Override
    public String getPrincipalName() {
        return getOidcUser().getName();
    }

    public boolean hasPermission(Collection<String> userGroupList) {
        return getUserGroup().containsAll(userGroupList);
    }

    public void hasPermissionOrThrowAccessDeniedException(Collection<String> userGroupList) {
        if (!hasPermission(userGroupList)) {
            throw new AccessDeniedException(("No permission to user group"));
        }
    }

    @Override
    public boolean hasPermission(Host host) {
        return hasPermission(host.getUserGroupList());
    }

    public void hasPermissionOrThrowAccessDeniedException(Host host) {
        if (!hasPermission(host)) {
            throw new AccessDeniedException(String.format("No permission to host %s", host.getId().toString()));
        }
    }

    @Override
    public String getPicture() {
        return getOidcUser().getPicture();
    }
}
