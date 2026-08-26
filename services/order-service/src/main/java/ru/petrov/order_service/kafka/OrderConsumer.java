package ru.petrov.order_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {

    @KafkaListener(
            topics = "orders",
            groupId = "orders-group"
    )
    public void consume(String message,
                        Acknowledgment ack) {

        try {

            log.info("Received: {}", message);

            Thread.sleep(2000);

            ack.acknowledge();

            log.info("Committed: {}", message);

        } catch (Exception e) {
            log.error("Error", e);
        }
    }
}
