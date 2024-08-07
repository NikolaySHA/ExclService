package com.NikolaySHA.ExclusiveService.service;

import com.NikolaySHA.ExclusiveService.model.dto.ProtocolDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.TransferProtocol;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProtocolService {
    void createTransferProtocol(Appointment data);
    void generatePdf(Long id, HttpServletResponse response) throws IOException, DocumentException;
    
    ProtocolDTO getTransferProtocolById(Long id);
    
    void delete(Long id, Long appointment_id);
}
