package com.b8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.b8.entity.Outbox;
import com.b8.publisher.MessagePublisher;
import com.b8.repository.OutboxRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@EnableScheduling
@Slf4j
public class OrderPollerService {

    @Autowired
    private OutboxRepository repository;

    @Autowired
    private MessagePublisher messagePublisher;

    @Scheduled(fixedRate = 60000) // every 60 seconds
    public void pollOutboxMessagesAndPublish(){
        //1. fetch unprocessed record
        List<Outbox> unprocessedRecords = repository.findByProcessedFalse();
        
        log.info("unprocessed record count : {}", unprocessedRecords.size());
        //2. publish to kafka topic
        unprocessedRecords.forEach(outbox -> {
            try {
                messagePublisher.publish(outbox.getPayload());
                //update the message status to processed=true to avoid duplicate message processing
                outbox.setProcessed(true);
                repository.save(outbox);
            } catch (Exception ignored) {
                log.error(ignored.getMessage());
            }
        });
    }
}
