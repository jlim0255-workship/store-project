package com.jlim.store.orderservice.domain;

import com.jlim.store.orderservice.domain.models.OrderStatus;
import com.jlim.store.orderservice.domain.models.OrderSummary;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// when we making changes to an order, always through order then access the order item, never directly to order item
interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    // these 2 are the Spring Data Query Method (Spring can understand it based on prefix and keywords in the entity table field)
    List<OrderEntity> findByStatus(OrderStatus status);
    Optional<OrderEntity> findByOrderNumber(String orderNumber);


    default void updateOrderStatus(String orderNumber, OrderStatus status) {
        OrderEntity order = this.findByOrderNumber(orderNumber).orElseThrow();
        order.setStatus(status);
        this.save(order);
    }

    /**
     * Using JPQL to select only the fields from Java Object Entity OrderEntity
     * that we need for the OrderSummary projection, instead of fetching the entire OrderEntity.
     *
     * (avoid memory wastage: if we have a lot of fields in OrderEntity)
     *
     * select new will create a new small OrderSummary instance through the DB query here, instead of returning back the heavy big OrderEntity
     * */
    @Query("""
        select new com.jlim.store.orderservice.domain.models.OrderSummary(o.orderNumber, o.status)
        from OrderEntity o
        where o.userName = :userName
        """)
    List<OrderSummary> findByUserName(String userName);

    /**
     * Left join OrderEntity and OrderItemEntity based on username and ordernumber
     * then project the OrderEntity
     *
     * fetch join will load the data of username and ordernumber in one query instead of multiple queries (avoids N+1 problem)
     *
     * */
    @Query("""
        select distinct o
        from OrderEntity o left join fetch o.items
        where o.userName = :userName and o.orderNumber = :orderNumber
        """)
    Optional<OrderEntity> findByUserNameAndOrderNumber(String userName, String orderNumber);
}
