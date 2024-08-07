package com.NikolaySHA.ExclusiveService.web.controller;

import com.NikolaySHA.ExclusiveService.model.dto.ProtocolDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.TransferProtocol;
import com.NikolaySHA.ExclusiveService.service.ProtocolService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
@RequestMapping("/protocol")
@Controller
public class ProtocolController{
    
    private final ProtocolService protocolService;
    
    public ProtocolController(ProtocolService protocolService) {
        this.protocolService = protocolService;
    }
    
    @GetMapping("/{id}")
    public void getProtocolPdf(@PathVariable Long id, HttpServletResponse response) throws DocumentException, IOException {
        protocolService.generatePdf(id, response);
    }
    @PostMapping("/delete/{appointmentId}/{id}")
    public String deleteProtocol(@PathVariable("id") Long id, @PathVariable("appointmentId") Long appointmentId) {
        protocolService.delete(id, appointmentId);
 
        return "redirect:/appointments/" + appointmentId;
    }
}
