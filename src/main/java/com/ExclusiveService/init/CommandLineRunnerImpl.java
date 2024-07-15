package com.ExclusiveService.init;

import com.ExclusiveService.model.entity.UserRole;
import com.ExclusiveService.model.enums.UserRolesEnum;
import com.ExclusiveService.repo.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
  
    private final RoleRepository roleRepository;
    
    public CommandLineRunnerImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        long count = this.roleRepository.count();
        if (count > 0) {
            return;
        }
        List<UserRole> toInsert = Arrays.stream(UserRolesEnum.values())
                .map(role -> new UserRole(role))
                .collect(Collectors.toList());
        this.roleRepository.saveAll(toInsert);

    }
}
