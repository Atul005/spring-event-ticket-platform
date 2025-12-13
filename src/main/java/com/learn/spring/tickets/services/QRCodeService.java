package com.learn.spring.tickets.services;

import com.learn.spring.tickets.domain.entities.QRCode;
import com.learn.spring.tickets.domain.entities.Ticket;

public interface QRCodeService {

    QRCode generateQRCode(Ticket ticket);


}
