package com.jlim.store.webapp.web.clients.orders;

public record OrderConfirmationDTO(String orderNumber, OrderStatus status) {}