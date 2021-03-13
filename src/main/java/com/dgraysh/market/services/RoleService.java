package com.dgraysh.market.services;

import com.dgraysh.market.entities.Role;
import com.dgraysh.market.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Collection<Role> findByName(String roleName) {
        return Collections.singletonList(roleRepository.findByName(roleName));
    }
}
