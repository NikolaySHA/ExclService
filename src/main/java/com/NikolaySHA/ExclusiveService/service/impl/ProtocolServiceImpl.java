package com.NikolaySHA.ExclusiveService.service.impl;


import com.NikolaySHA.ExclusiveService.model.dto.ProtocolDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.TransferProtocol;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.repo.AppointmentRepository;
import com.NikolaySHA.ExclusiveService.repo.ProtocolRepository;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.ProtocolService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProtocolServiceImpl implements ProtocolService {
    private final ProtocolRepository protocolRepository;
    private final ModelMapper modelMapper;
    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;
    
    @Override
    @Transactional
    public void createTransferProtocol(Appointment data) {
        
        TransferProtocol transferProtocol = new TransferProtocol();
        transferProtocol.setDate(data.getDate());
        transferProtocol.setLicensePlate(data.getCar().getLicensePlate());
        transferProtocol.setModel(data.getCar().getModel());
        transferProtocol.setMake(data.getCar().getMake());
        transferProtocol.setCustomerName(data.getCar().getOwner().getName());
        
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
    }
    @Override
    public ProtocolDTO getTransferProtocolById(Long id) {
        return protocolRepository.findById(id)
                .map(this::map).orElseThrow(() -> new IllegalArgumentException("Not found!"));
    }
    @Override
    public void generatePdf(Long id, HttpServletResponse response) throws IOException, DocumentException {
        TransferProtocol transferProtocol = protocolRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found!"));
        
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=transfer_protocol.pdf");
        
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        
        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 12, Font.NORMAL, BaseColor.BLACK);
        Font checkboxFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
        
        document.add(new Paragraph("                              Vehicle Transfer Protocol", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Today - " + transferProtocol.getDate().toString() + " in Sofia", font));
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("between 'Exclusive Service' OOD and " + transferProtocol.getCustomerName(), font));
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("was signed this protocol in relation with an appointment for vehicle - ", font));
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("                    License plate - " + transferProtocol.getLicensePlate(), font));
        document.add(new Paragraph("                             Make - " + transferProtocol.getMake(), font));
        document.add(new Paragraph("                            Model - " + transferProtocol.getModel(), font));
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        
        if (transferProtocol.isFinished()) {
            document.add(new Paragraph("\u25A1 The repair was carried out to a high standard and the car was accepted without objection", font));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("\u25A1 There are the following objections regarding the repair carried out: ................................................................................................................................", font));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Accepted by :" + "                          Delivered by : ", font));
            document.add(new Paragraph("              " + transferProtocol.getCustomerName() + "                       Exclusive Service", font));
        } else {
            document.add(new Paragraph("Accepted by :" + "                          Delivered by : ", font));
            document.add(new Paragraph("              Exclusive Service                       " + transferProtocol.getCustomerName(), font));
        }
        document.close();
    }
    
    @Override
    public void delete(Long id, Long appointmentId) {
        
        Optional<TransferProtocol> protocolOpt = protocolRepository.findById(id);
        if (protocolOpt.isPresent()) {
            TransferProtocol protocol = protocolOpt.get();
            Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
            if (appointmentOpt.isPresent()) {
                Appointment appointment = appointmentOpt.get();
               
                appointment.getProtocols().remove(protocol);
                appointmentRepository.save(appointment);
            }
            protocolRepository.delete(protocol);
        }
    }
    private ProtocolDTO map(TransferProtocol data){
        return this.modelMapper.map(data, ProtocolDTO.class);
    }
}
