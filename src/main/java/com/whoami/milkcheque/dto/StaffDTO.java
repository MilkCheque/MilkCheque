package dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
public class StaffDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int   age;
    private String phoneNumber;
    private String password;
}
