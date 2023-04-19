package models;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Integer id;
    private String customerName;
    private String customerEmail;
    private String customerPassword;
    private Double customerBalance;

    public Customer(String customerName, String customerEmail, String customerPassword, Double customerBalance) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
        this.customerBalance = customerBalance;
    }
}
