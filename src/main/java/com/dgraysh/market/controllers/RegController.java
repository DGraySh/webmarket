package com.dgraysh.market.controllers;

import com.dgraysh.market.entities.User;
import com.dgraysh.market.exceptions.MarketError;
import com.dgraysh.market.services.RoleService;
import com.dgraysh.market.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reg")
@RequiredArgsConstructor
public class RegController {
    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if ((userService.findByUsername(user.getUsername()) != null) || (userService.findByEmail(user.getEmail()) != null)) {
            return new ResponseEntity<>(new MarketError(HttpStatus.UNAUTHORIZED.value(),
                    String.format("User with the same credentials '%s' or '%s' already exists, choose another ones",
                            user.getUsername(), user.getEmail())), HttpStatus.UNAUTHORIZED);
        }
        user.setRoles(roleService.findByName("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return ResponseEntity.ok(user);
    }

}
