package com.learn.spring.tickets.repositories;
import com.learn.spring.tickets.domain.entities.Event;
import com.learn.spring.tickets.domain.entities.EventStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event , UUID> {

    Page<Event> findByOrganizerId(UUID organizerId, Pageable pageable);
    Optional<Event> findByIdAndOrganizerId(UUID eventId, UUID organizerId);
    Page<Event> findByEventStatus(EventStatusEnum eventStatusEnum, Pageable pageable);

//    @Query(
//            value = """
//        SELECT e FROM Event e
//        WHERE e.eventStatus = com.learn.spring.tickets.enums.EventStatusEnum.PUBLISHED
//          AND (
//                LOWER(e.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
//             OR LOWER(e.venue) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
//          )
//        """,
//            countQuery = """
//        SELECT COUNT(e) FROM Event e
//        WHERE e.eventStatus = com.learn.spring.tickets.enums.EventStatusEnum.PUBLISHED
//          AND (
//                LOWER(e.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
//             OR LOWER(e.venue) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
//          )
//        """
//    )
//    Page<Event> searchPublishedEvents(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query(value = """
        SELECT e FROM Event e
        WHERE e.eventStatus = :status
          AND (
                LOWER(e.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
             OR LOWER(e.venue) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
          )
        """,
            countQuery = """
        SELECT COUNT(e) FROM Event e
        WHERE e.eventStatus = :status
          AND (
                LOWER(e.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
             OR LOWER(e.venue) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
          )
        """
    )
    Page<Event> searchPublishedEvents(
            @Param("searchTerm") String searchTerm,
            @Param("status") EventStatusEnum status,
            Pageable pageable
    );

    Optional<Event> findByIdAndEventStatus(UUID eventId, EventStatusEnum eventStatusEnum);
}
