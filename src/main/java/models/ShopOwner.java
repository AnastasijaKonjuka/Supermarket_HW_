package models;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShopOwner {
    private Integer id;
    private String ownerName;
    private String email;
    private String password;
}
