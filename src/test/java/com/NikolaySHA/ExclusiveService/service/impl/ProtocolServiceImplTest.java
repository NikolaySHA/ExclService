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
import static org.mockito.ArgumentMatchers.eq;
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
    private TransferProtocol transferProtocol;
    private ProtocolDTO protocolDTO;
    
    @BeforeEach
    void setUp() {
        // Set up User, Car, and Appointment
        User user = new User();
        user.setName("Mad Max");
        
        Car car = new Car();
        car.setMake("AUDI");
        car.setModel("RS6");
        car.setLicensePlate("CB6666BC");
        
        appointment = new Appointment();
        appointment.setCar(car);
        appointment.setUser(user);
        appointment.setStatus(Status.COMPLETED);
        appointment.setProtocols(Collections.emptyList());
        
        transferProtocol = new TransferProtocol();
        transferProtocol.setId(1L);
        transferProtocol.setLicensePlate("CBCBCBCB");
        
        protocolDTO = new ProtocolDTO();
        protocolDTO.setId(1L);
        protocolDTO.setLicensePlate("CBCBCBCB");
    }
    
    @Test
    void testCreateTransferProtocolFinishedTrue() {
        when(modelMapper.map(any(Appointment.class), eq(TransferProtocol.class))).thenReturn(transferProtocol);
        when(protocolRepository.save(any(TransferProtocol.class))).thenReturn(transferProtocol);
        doNothing().when(appointmentService).save(any(Appointment.class));
        
        protocolService.createTransferProtocol(appointment);
        
        verify(protocolRepository, times(1)).save(any(TransferProtocol.class));
        verify(appointmentService, times(1)).save(any(Appointment.class));
        assertEquals(1, appointment.getProtocols().size());  // Check that the protocol was added
    }
    
    @Test
    void testCreateTransferProtocolFinishedFalse() {
        appointment.setStatus(Status.IN_PROGRESS);
        
        when(modelMapper.map(any(Appointment.class), eq(TransferProtocol.class))).thenReturn(transferProtocol);
        when(protocolRepository.save(any(TransferProtocol.class))).thenReturn(transferProtocol);
        doNothing().when(appointmentService).save(any(Appointment.class));
        
        protocolService.createTransferProtocol(appointment);
        
        verify(protocolRepository, times(1)).save(any(TransferProtocol.class));
        verify(appointmentService, times(1)).save(any(Appointment.class));
        assertEquals(1, appointment.getProtocols().size());  // Check that the protocol was added
    }
    
    @Test
    void testGetTransferProtocolById() {
        when(protocolRepository.findById(1L)).thenReturn(Optional.of(transferProtocol));
        when(modelMapper.map(any(TransferProtocol.class), eq(ProtocolDTO.class))).thenReturn(protocolDTO);
        
        ProtocolDTO result = protocolService.getTransferProtocolById(1L);
        
        assertNotNull(result);
        assertEquals("CBCBCBCB", result.getLicensePlate());
        verify(protocolRepository, times(1)).findById(1L);
    }
    
    @Test
    void testGetAllTransferProtocols() {
        when(protocolRepository.findAll()).thenReturn(List.of(transferProtocol));
        when(modelMapper.map(any(TransferProtocol.class), eq(ProtocolDTO.class))).thenReturn(protocolDTO);
        
        List<ProtocolDTO> result = protocolService.getAllTransferProtocols();
        
        assertNotNull(result);
        assertTrue(result.size() > 0);
        assertEquals("CBCBCBCB", result.get(0).getLicensePlate());
        verify(protocolRepository, times(1)).findAll();
    }
    
    @Test
    void testDeleteProtocol() {
        when(protocolRepository.findById(1L)).thenReturn(Optional.of(transferProtocol));
        doNothing().when(protocolRepository).delete(any(TransferProtocol.class));
        
        protocolService.deleteProtocol(1L);
        
        verify(protocolRepository, times(1)).findById(1L);
        verify(protocolRepository, times(1)).delete(transferProtocol);
    }
    
    @Test
    void testFindById() {
        when(protocolRepository.findById(1L)).thenReturn(Optional.of(transferProtocol));
        
        Optional<TransferProtocol> result = protocolService.findById(1L);
        
        assertTrue(result.isPresent());
        assertEquals("CBCBCBCB", result.get().getLicensePlate());
        verify(protocolRepository, times(1)).findById(1L);
    }
}
