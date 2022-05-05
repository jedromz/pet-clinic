package com.jedromz.petclinic.authentication;

import com.google.common.collect.Lists;
import com.jedromz.petclinic.security.ApplicationUserRole;
import liquibase.pro.packaged.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FakeApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return Optional.empty();
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        "admin",
                        passwordEncoder.encode("admin"),
                        ApplicationUserRole.ADMIN.getGrantedAuthorities(),
                        true, true, true, true
                ),
                new ApplicationUser(
                        "john",
                        passwordEncoder.encode("password"),
                        ApplicationUserRole.ADMIN.getGrantedAuthorities(),
                        true, true, true, true
                ),
                new ApplicationUser(
                        "tom",
                        passwordEncoder.encode("password"),
                        ApplicationUserRole.VET.getGrantedAuthorities(),
                        true, true, true, true
                )
        );
    }
}
