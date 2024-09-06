package com.example.tuneguessrserver.user;

import com.example.tuneguessrserver.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
