package com.NikolaySHA.ExclusiveService.repo;


import com.NikolaySHA.ExclusiveService.model.entity.TransferProtocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtocolRepository extends JpaRepository<TransferProtocol, Long> {

}
