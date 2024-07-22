package com.ExclusiveService.web.controller;


import com.ExclusiveService.model.entity.TransferProtocol;
import com.ExclusiveService.service.TransferProtocolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfer-protocols")
public class TransferProtocolController {
    
    private final TransferProtocolService transferProtocolService;
    
    public TransferProtocolController(TransferProtocolService transferProtocolService) {
        this.transferProtocolService = transferProtocolService;
    }
    
    @PostMapping
    public TransferProtocol createTransferProtocol() {
        return transferProtocolService.createTransferProtocol();
    }
    
    @GetMapping("/{id}")
    public TransferProtocol getTransferProtocolById(@PathVariable Long id) {
        return transferProtocolService.getTransferProtocolById(id);
    }
    
    @GetMapping
    public List<TransferProtocol> getAllTransferProtocols() {
        return transferProtocolService.getAllTransferProtocols();
    }
}
