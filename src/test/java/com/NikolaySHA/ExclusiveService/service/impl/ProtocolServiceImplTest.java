package com.NikolaySHA.ExclusiveService.service.impl;

import com.NikolaySHA.ExclusiveService.model.dto.ProtocolDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.TransferProtocol;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.repo.ProtocolRepository;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProtocolServiceImplTest {
    
    @Mock
    private ProtocolRepository protocolRepository;
    
    @Mock
    private ModelMapper modelMapper;
    
    @Mock
    private AppointmentService appointmentService;
    
    @InjectMocks
    private ProtocolServiceImpl protocolService;
    
    private Appointment appointment;
    private Car car;
    private User user;
    
    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Mad Max");
        
        car = new Car();
        car.setMake("AUDI");
        car.setModel("RS6");
        car.setLicensePlate("CB6666BC");
        
        appointment = new Appointment();
        appointment.setCar(car);
        appointment.setUser(user);
        appointment.setStatus(Status.COMPLETED);  // Change this status for testing isFinished = false scenario
        appointment.setProtocols(Collections.emptyList());
    }
    
    @Test
    void testCreateTransferProtocolFinishedTrue() {
        doNothing().when(appointmentService).save(any(Appointment.class));
        when(protocolRepository.save(any(TransferProtocol.class))).thenReturn(new TransferProtocol());
        
        protocolService.createTransferProtocol(appointment);
        
        verify(protocolRepository, times(1)).save(any(TransferProtocol.class));
        verify(appointmentService, times(1)).save(any(Appointment.class));
    }
    
    @Test
    void testCreateTransferProtocolFinishedFalse() {
        appointment.setStatus(Status.IN_PROGRESS);  // Set status to IN_PROGRESS for isFinished = true scenario
        
        doNothing().when(appointmentService).save(any(Appointment.class));
        when(protocolRepository.save(any(TransferProtocol.class))).thenReturn(new TransferProtocol());
        
        protocolService.createTransferProtocol(appointment);
        
        verify(protocolRepository, times(1)).save(any(TransferProtocol.class));
        verify(appointmentService, times(1)).save(any(Appointment.class));
    }
    @Test
    void testGetTransferProtocolById() {
        TransferProtocol protocol = new TransferProtocol();
        protocol.setId(1L);
        
        when(protocolRepository.findById(1L)).thenReturn(Optional.of(protocol));
        when(modelMapper.map(any(TransferProtocol.class), eq(ProtocolDTO.class))).thenReturn(new ProtocolDTO());
        
        ProtocolDTO protocolDTO = protocolService.getTransferProtocolById(1L);
        
        assertNotNull(protocolDTO);
        verify(protocolRepository, times(1)).findById(1L);
    }
    
    @Test
    void testGetAllTransferProtocols() {
        TransferProtocol protocol = new TransferProtocol();
        
        when(protocolRepository.findAll()).thenReturn(List.of(protocol));
        when(modelMapper.map(any(TransferProtocol.class), eq(ProtocolDTO.class))).thenReturn(new ProtocolDTO());
        
        List<ProtocolDTO> protocols = protocolService.getAllTransferProtocols();
        
        assertTrue(protocols.size() > 0);
        verify(protocolRepository, times(1)).findAll();
    }
    @Test
    void testDeleteProtocol() {
        TransferProtocol protocol = new TransferProtocol();
        protocol.setId(1L);
        
        when(protocolRepository.findById(1L)).thenReturn(Optional.of(protocol));
        doNothing().when(protocolRepository).delete(any(TransferProtocol.class));
        
        protocolService.deleteProtocol(1L);
        
        verify(protocolRepository, times(1)).findById(1L);
        verify(protocolRepository, times(1)).delete(protocol);
    }
 
    @Test
    void testFindById() {
        
        TransferProtocol protocol = new TransferProtocol();
        protocol.setId(1L);
        protocol.setLicensePlate("CBCBCBCB");
        
       
        when(protocolRepository.findById(1L)).thenReturn(Optional.of(protocol));
        Optional<TransferProtocol> result = protocolService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("CBCBCBCB", result.get().getLicensePlate());
        
        verify(protocolRepository, times(1)).findById(1L);
    }
    
    
    
}
