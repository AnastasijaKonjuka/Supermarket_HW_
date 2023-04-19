package models;

import lombok.*;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public class Sales {
        private Integer id;
        private String customerEmail;
        private String productName;
        private Integer quantitySold;
        private Double productCost;
        private Double productPrice;
        private Double totalSales;

        public Sales(String customerEmail, String productName, Integer quantitySold, Double productCost, Double productPrice, Double totalSales) {
        }
    }

