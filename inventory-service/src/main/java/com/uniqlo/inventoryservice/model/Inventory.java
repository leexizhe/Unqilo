package com.uniqlo.inventoryservice.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skuCode;
    private Integer quantity;
}
