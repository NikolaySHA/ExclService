package com.ExclusiveService.service;

import com.ExclusiveService.model.entity.TransferProtocol;

import java.util.List;

public interface TransferProtocolService {
    TransferProtocol createTransferProtocol();
    
    TransferProtocol getTransferProtocolById(Long id);
    
    List<TransferProtocol> getAllTransferProtocols();
    
    void generatePdf(TransferProtocol protocol);
    
}
