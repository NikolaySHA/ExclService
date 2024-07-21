package com.ExclusiveService.repo;

import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.model.entity.UserRole;
import com.ExclusiveService.model.enums.UserRolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u JOIN u.roles r " +
            "WHERE (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) " +
            "AND (:role IS NULL OR r.role = :role)")
    List<User> searchUsers(@Param("name") String name,
                           @Param("email") String email,
                           @Param("role") UserRolesEnum role);
    
    @Query("SELECT u FROM User u JOIN FETCH u.roles")
    List<User> findAllWithRoles();
}

