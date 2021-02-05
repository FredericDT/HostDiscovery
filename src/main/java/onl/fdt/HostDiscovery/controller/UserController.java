package onl.fdt.HostDiscovery.controller;

import onl.fdt.HostDiscovery.payload.response.UserResponsePayload;
import onl.fdt.HostDiscovery.service.OidcPrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    OidcPrincipalService oidcPrincipalService;

    @GetMapping
    public UserResponsePayload get() {
        return new UserResponsePayload(oidcPrincipalService.getPrincipalName(), oidcPrincipalService.getUserGroup());
    }

//    @GetMapping("detail")
//    public Object getDetail() {
//        return SecurityContextHolder.getContext().getAuthentication();
//    }

}
