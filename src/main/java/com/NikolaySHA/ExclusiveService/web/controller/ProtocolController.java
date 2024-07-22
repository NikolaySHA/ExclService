package com.NikolaySHA.ExclusiveService.web.controller;

import com.NikolaySHA.ExclusiveService.model.dto.ProtocolDTO;
import com.NikolaySHA.ExclusiveService.model.dto.ShowCarDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.TransferProtocol;
import com.NikolaySHA.ExclusiveService.service.ProtocolService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/protocols")
public class ProtocolController {
    private final ProtocolService protocolService;
    private final ModelMapper modelMapper;
    
    public ProtocolController(ProtocolService protocolService, ModelMapper modelMapper) {
        this.protocolService = protocolService;
        this.modelMapper = modelMapper;
    }
    
@GetMapping("/{id}")
    public String viewProtocol(@PathVariable("id") Long id, ProtocolDTO data, RedirectAttributes redirectAttributes, Model model) {
        Optional<TransferProtocol> protocolOptional = protocolService.findById(id);
        if (protocolOptional.isEmpty()){
            redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
            return "redirect:/error/contact-admin";
        }
        TransferProtocol protocol = protocolOptional.get();
        data = modelMapper.map(protocol, ProtocolDTO.class);
        model.addAttribute("protocolData", data);
        return "transfer-protocol";
    }
}
