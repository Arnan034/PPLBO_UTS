package com.b8.repository;

import com.b8.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface OutboxRepository extends JpaRepository<Outbox,Long> {

    List<Outbox>  findByProcessedFalse();
}