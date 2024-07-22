package com.NikolaySHA.ExclusiveService.repo;

import com.NikolaySHA.ExclusiveService.model.entity.UserRole;
import com.NikolaySHA.ExclusiveService.model.enums.UserRolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole(UserRolesEnum role);
}
