package com.jedromz.petclinic.security;


import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.jedromz.petclinic.security.ApplicationUserPermissions.*;

@Getter
public enum ApplicationUserRole {
    VET(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(VET_READ, VET_WRITE, PET_WRITE, PET_READ, VISIT_READ,VISIT_WRITE)),
    JUNIOR_ADMIN(Sets.newHashSet(VET_READ, PET_READ, VISIT_READ));
    private final Set<ApplicationUserPermissions> permissions;

    ApplicationUserRole(Set<ApplicationUserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }


}
