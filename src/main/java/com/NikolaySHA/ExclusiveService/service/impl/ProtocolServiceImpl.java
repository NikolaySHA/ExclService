package com.NikolaySHA.ExclusiveService.service.impl;


import com.NikolaySHA.ExclusiveService.model.dto.AddAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.dto.ProtocolDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.TransferProtocol;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.repo.ProtocolRepository;
import com.NikolaySHA.ExclusiveService.service.ProtocolService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProtocolServiceImpl implements ProtocolService {
    private final ProtocolRepository protocolRepository;
    private final ModelMapper modelMapper;
    
    public ProtocolServiceImpl(ProtocolRepository protocolRepository, ModelMapper modelMapper) {
        this.protocolRepository = protocolRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public void createTransferProtocol(Appointment data) {
      TransferProtocol transferProtocol = new TransferProtocol();
      transferProtocol.setDate(data.getDate());
      transferProtocol.setMake(data.getCar().getMake());
      transferProtocol.setModel(data.getCar().getModel());
      transferProtocol.setLicensePlate(data.getCar().getLicensePlate());
      transferProtocol.setCustomerName(data.getUser().getName());
      if (data.getStatus().equals(Status.IN_PROGRESS)) {
          transferProtocol.setFinished(false);
      }else {
          transferProtocol.setFinished(true);
      }
      protocolRepository.save(transferProtocol);
    }
    
    @Override
    public ProtocolDTO getTransferProtocolById(Long id) {
        return protocolRepository.findById(id)
                .map(this::map).orElseThrow(() -> new IllegalArgumentException("Not found!"));
    }
    
    @Override
    public List<ProtocolDTO> getAllTransferProtocols() {
        
        return protocolRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
    private ProtocolDTO map(TransferProtocol data){
        return this.modelMapper.map(data, ProtocolDTO.class);
    }
    
    @Override
    public Optional<TransferProtocol> findById(Long id) {
        return protocolRepository.findById(id);
    }
    
    @Override
    public void deleteProtocol(Long id) {
        protocolRepository.delete(protocolRepository.findById(id).get());
    }
}
