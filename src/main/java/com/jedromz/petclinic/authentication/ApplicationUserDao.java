package com.jedromz.petclinic.authentication;

import org.checkerframework.checker.nullness.Opt;

import java.util.Optional;

public interface ApplicationUserDao {

    Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
