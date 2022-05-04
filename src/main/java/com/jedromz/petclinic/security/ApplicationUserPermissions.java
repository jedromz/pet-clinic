package com.jedromz.petclinic.security;

import lombok.Getter;

@Getter
public enum ApplicationUserPermissions {
    PET_READ("pet:read"),
    PET_WRITE("pet:write"),
    VET_READ("vet:read"),
    VET_WRITE("vet:write"),
    VISIT_READ("visit:read"),
    VISIT_WRITE("visit:write");

    private final String permission;

    ApplicationUserPermissions(String permission) {
        this.permission = permission;
    }
}
