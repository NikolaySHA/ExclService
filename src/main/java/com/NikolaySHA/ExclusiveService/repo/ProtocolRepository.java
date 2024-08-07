package com.NikolaySHA.ExclusiveService.repo;


import com.NikolaySHA.ExclusiveService.model.entity.TransferProtocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProtocolRepository extends JpaRepository<TransferProtocol, Long> {
    List<TransferProtocol> findAll();
    Optional<TransferProtocol> findById(Long id);
    
}
