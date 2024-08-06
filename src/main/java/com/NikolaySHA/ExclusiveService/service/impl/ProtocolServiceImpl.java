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

import java.util.ArrayList;
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
        
        TransferProtocol transferProtocol = modelMapper.map(data, TransferProtocol.class);
        if (data.getStatus().equals(Status.COMPLETED)) {
            transferProtocol.setFinished(false);
        } else {
            transferProtocol.setFinished(true);
        }
        protocolRepository.save(transferProtocol);
        List<TransferProtocol> protocols = new ArrayList<>(data.getProtocols());
        protocols.add(transferProtocol);
        data.setProtocols(protocols);
        appointmentService.save(data);
//        TODO: Generate pdf and send it to printer;
    }
    @Override
    public ProtocolDTO getTransferProtocolById(Long id) {
        return protocolRepository.findById(id)
                .map(this::map).orElseThrow(() -> new IllegalArgumentException("Not found!"));
    }
    private ProtocolDTO map(TransferProtocol data){
        return this.modelMapper.map(data, ProtocolDTO.class);
    }
}
