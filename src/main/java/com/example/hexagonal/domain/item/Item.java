package com.example.hexagonal.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Getter
//@Entity
@Document
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(length = 30, unique = true, nullable = false)
    //;;;
    @Indexed(unique = true)
    private String name;
//    @Column(nullable = false)
    private Integer price;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * using mysql
     */
    public Item(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    /**
     * unsing mongodb
     */
    public Item(Long id, String name, Integer price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void changeName(String name) {
        if(!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("잘못된 name 입력입니다");
        }
        this.name = name;
    }

    public void changePrice(Integer price) {
        this.price = price;
    }
}
