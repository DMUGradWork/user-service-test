package User.User.dto;

import User.User.enums.Role;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDateTime createdAt;
    private Role role;
}
