package models;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private String productName;
    private String measurement;
    private Integer quantityAvailable;
    private Double cost;
    private Double sellingPrice;
}
