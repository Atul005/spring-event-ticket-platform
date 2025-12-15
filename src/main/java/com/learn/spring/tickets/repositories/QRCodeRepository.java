package com.learn.spring.tickets.repositories;

import com.learn.spring.tickets.domain.entities.QRCode;
import com.learn.spring.tickets.domain.entities.QRCodeStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QRCodeRepository extends JpaRepository<QRCode, UUID> {

    Optional<QRCode> getByTicketIdAndTicketPurchaserId(UUID ticketId, UUID purchaserId);
    Optional<QRCode> findByIdAndStatus(UUID qrCodeId, QRCodeStatusEnum qrCodeStatusEnum);

}
