package com.NikolaySHA.ExclusiveService.service.impl;


import com.NikolaySHA.ExclusiveService.model.dto.ProtocolDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.TransferProtocol;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.repo.ProtocolRepository;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.ProtocolService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProtocolServiceImpl implements ProtocolService {
    private final ProtocolRepository protocolRepository;
    private final ModelMapper modelMapper;
    private final AppointmentService appointmentService;
    
    public ProtocolServiceImpl(ProtocolRepository protocolRepository, ModelMapper modelMapper, AppointmentService appointmentService) {
        this.protocolRepository = protocolRepository;
        this.modelMapper = modelMapper;
        this.appointmentService = appointmentService;
    }
    @Override
    @Transactional
    public void createTransferProtocol(Appointment data) {
        TransferProtocol transferProtocol = new TransferProtocol();
        transferProtocol.setDate(data.getDate());
        transferProtocol.setMake(data.getCar().getMake());
        transferProtocol.setModel(data.getCar().getModel());
        transferProtocol.setLicensePlate(data.getCar().getLicensePlate());
        transferProtocol.setCustomerName(data.getUser().getName());
        if (data.getStatus().equals(Status.IN_PROGRESS)) {
            transferProtocol.setFinished(false);
        } else {
            transferProtocol.setFinished(true);
        }
        protocolRepository.save(transferProtocol);
        
        List<TransferProtocol> protocols = data.getProtocols();
        protocols.add(transferProtocol);
        data.setProtocols(protocols);
        appointmentService.save(data);
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
