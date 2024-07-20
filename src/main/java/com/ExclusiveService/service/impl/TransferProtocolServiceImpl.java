package com.ExclusiveService.service.impl;

import com.ExclusiveService.model.entity.TransferProtocol;
import com.ExclusiveService.repo.TransferProtocolRepository;
import com.ExclusiveService.service.TransferProtocolService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Service
public class TransferProtocolServiceImpl implements TransferProtocolService {
    private final TransferProtocolRepository transferProtocolRepository;
    
    public TransferProtocolServiceImpl(TransferProtocolRepository transferProtocolRepository) {
        this.transferProtocolRepository = transferProtocolRepository;
    }
    @Override
    public TransferProtocol createTransferProtocol() {
        TransferProtocol protocol = new TransferProtocol();
        protocol.setDate(LocalDate.now());
        transferProtocolRepository.save(protocol);
        generatePdf(protocol);
        return protocol;
    }
    
    @Override
    public TransferProtocol getTransferProtocolById(Long id) {
        return transferProtocolRepository.findById(id).orElse(null);
    }
    
    @Override
    public List<TransferProtocol> getAllTransferProtocols() {
        return transferProtocolRepository.findAll();
    }
    
    @Override
    public void generatePdf(TransferProtocol protocol) {
        com.itextpdf.text.Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("transfer_protocol_" + protocol.getId() + ".pdf"));
            document.open();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String formattedDate = protocol.getDate().format(formatter);
            
            document.add(new Paragraph("ПРИЕМО-ПРЕДАВАТЕЛЕН ПРОТОКОЛ"));
            document.add(new Paragraph("Днес, " + formattedDate + ", между Ексклузив Сервиз и " +
                    protocol.getAppointment().getUser().getName() + " се създаде следният протокол за предаване на " +
                    "л.а. с регистрационен номер " + protocol.getAppointment().getCar().getLicensePlate() +
                    ", марка " + protocol.getAppointment().getCar().getMake() +
                    ", модел " + protocol.getAppointment().getCar().getModel() + ", за извършване на ремонтна услуга."));
            document.add(new Paragraph("Гр.София, дата: " + formattedDate));
            document.add(new Paragraph("____________________"));
            document.add(new Paragraph("Ексклузив Сервиз"));
            document.add(new Paragraph("____________________"));
            document.add(new Paragraph(protocol.getAppointment().getUser().getName()));
            
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
