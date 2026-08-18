package com.jlim.store.orderservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_id_generator")
    @SequenceGenerator(name = "order_item_id_generator", sequenceName = "order_item_id_seq")
    private Long id;

    @Column(nullable = false)
    private String code;

    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    /**
     * MEAT:
     *
     * @ManyToOne: Many OrderItemEntity can link to one OrderEntity
     * (There should be one to many at the items field inside OrderEntity)
     *
     * DB LEVEL:
     * @JoinColumn(name = "order_id"): foreign key representation,
     * order_items table references order table through newly created order_id column based on the PrimaryKey of OrderEntity, which is id (in Orders table)
     *
     * JAVA Object LEVEL:
     * A newly created field order type = OrderEntity is referencing the id (PK) inside OrderEntity
     *
     * */

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id") // DB level referencing field is called order_id in Postgres
    private OrderEntity order; // Java Object level referencing field is called order type = OrderEntity

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
