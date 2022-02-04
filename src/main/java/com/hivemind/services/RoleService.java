package com.hivemind.services;

import com.hivemind.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleService {
    private final RoleRepository roleRepository;
    private final AccountService accountService;

    @Autowired
    public RoleService(RoleRepository roleRepository, AccountService accountService) {
        this.roleRepository = roleRepository;
        this.accountService = accountService;
    }

}
