package com.NikolaySHA.ExclusiveService.service;

import com.NikolaySHA.ExclusiveService.model.dto.ProtocolDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.TransferProtocol;

import java.util.List;
import java.util.Optional;

public interface ProtocolService {
    void createTransferProtocol(Appointment data);
    
    ProtocolDTO getTransferProtocolById(Long id);
    
    List<ProtocolDTO> getAllTransferProtocols();
    
    void deleteProtocol(Long id);
    
    Optional<TransferProtocol> findById(Long id);
}
