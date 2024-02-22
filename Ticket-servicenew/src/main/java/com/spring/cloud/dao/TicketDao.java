package com.spring.cloud.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.cloud.model.Ticket;

@Repository
public interface TicketDao extends JpaRepository<Ticket, Long> {
	public Optional<Ticket> findById(Long ticketId);
}
