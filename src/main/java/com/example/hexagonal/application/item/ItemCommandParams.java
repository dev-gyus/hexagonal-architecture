package com.example.hexagonal.application.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemCommandParams {
    private Long id;
    private String name;
    private Integer price;
}
