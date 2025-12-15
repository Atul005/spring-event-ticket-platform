package com.learn.spring.tickets.services.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.learn.spring.tickets.domain.entities.QRCode;
import com.learn.spring.tickets.domain.entities.QRCodeStatusEnum;
import com.learn.spring.tickets.domain.entities.Ticket;
import com.learn.spring.tickets.exceptions.QRCodeGenerationException;
import com.learn.spring.tickets.exceptions.QRCodeNotFoundException;
import com.learn.spring.tickets.repositories.QRCodeRepository;
import com.learn.spring.tickets.services.QRCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class QRCodeServiceImpl implements QRCodeService {

    private static final int QRCode_HEIGHT = 300;
    private static final int QRCode_WIDTH = 300;

    private final QRCodeWriter qrCodeWriter;
    private final QRCodeRepository qrCodeRepository;

    @Override
    public QRCode generateQRCode(Ticket ticket) {
        try{
            UUID id = UUID.randomUUID();
            String qRCodeImage = generateQrCodeImage(id);

            QRCode qrCode = new QRCode();
            qrCode.setId(id);
            qrCode.setStatus(QRCodeStatusEnum.ACTIVE);
            qrCode.setValue(qRCodeImage);
            qrCode.setTicket(ticket);

            return qrCodeRepository.saveAndFlush(qrCode);


        } catch (WriterException |IOException ex){
            throw new QRCodeGenerationException("Failed to Generate QR Code "+ex);
        }
    }

    @Override
    public byte[] getQRCodeImageForUserAndTicket(UUID ticketId, UUID userId) {
        QRCode qrCode = qrCodeRepository.getByTicketIdAndTicketPurchaserId(ticketId, userId)
                .orElseThrow(QRCodeNotFoundException::new);

        try{
            return Base64.getDecoder().decode(qrCode.getValue());
        } catch(IllegalArgumentException exception){
            log.error("Invalid base64 QR Code for ticket id : {}", ticketId, exception);
            throw new QRCodeNotFoundException();
        }
    }

    private String generateQrCodeImage(UUID id) throws WriterException, IOException {
        BitMatrix bitMatrix = qrCodeWriter.encode(id.toString(), BarcodeFormat.QR_CODE, QRCode_WIDTH, QRCode_HEIGHT);
        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(qrCodeImage, "PNG", baos);
            byte[] byteArray = baos.toByteArray();
            return Base64.getEncoder().encodeToString(byteArray);
        }

    }
}
