package com.jlim.store.orderservice.jobs;

import java.time.Instant;

import com.jlim.store.orderservice.domain.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessingJob {
    private static final Logger log = LoggerFactory.getLogger(OrderProcessingJob.class);

    // inject Order Service
    private final OrderService orderService;

    OrderProcessingJob(OrderService orderService){
        this.orderService = orderService;
    }

    @Scheduled(cron = "${orders.new-orders-job-cron}")
    public void processNewOrders(){
        log.info("Processing New Orders at {}", Instant.now());
        orderService.processNewOrders();
    }

}
