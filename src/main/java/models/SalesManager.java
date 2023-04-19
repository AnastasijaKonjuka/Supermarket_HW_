package models;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SalesManager {
    private Integer id;
    private String salesName;
    private String email;
    private String password;

    public SalesManager(String managerName, String managerEmail, String managerPassword) {
    }
}
