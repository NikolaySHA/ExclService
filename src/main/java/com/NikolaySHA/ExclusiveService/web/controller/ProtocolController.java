package com.NikolaySHA.ExclusiveService.web.controller;

import com.NikolaySHA.ExclusiveService.service.ProtocolService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ProtocolController{
    
    private final ProtocolService protocolService;
    
    public ProtocolController(ProtocolService protocolService) {
        this.protocolService = protocolService;
    }
    
    @GetMapping("/protocol/{id}")
    public void getProtocolPdf(@PathVariable Long id, HttpServletResponse response) throws DocumentException, IOException {
        protocolService.generatePdf(id, response);
    }
}
