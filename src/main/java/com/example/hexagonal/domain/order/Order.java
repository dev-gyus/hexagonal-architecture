package com.example.hexagonal.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//import javax.persistence.*;

@Getter
//@Entity(name = "orders")
@Document
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private Long itemId;
    private Integer quantity;
    private Integer itemPrice;
    private Integer totalPrice;

    public Order(Long itemId, Integer itemPrice, Integer quantity) {
        this.itemId = itemId;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        calculateTotalPrice(itemPrice, quantity);
    }

    public void calculateTotalPrice(Integer itemPrice, Integer quantity) {
        this.totalPrice = itemPrice * quantity;
    }
}
