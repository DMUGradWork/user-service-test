package User.User.entity;

import User.User.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDateTime created_at;

    @Enumerated(EnumType.ORDINAL)
    private Role role = Role.user;

    @Override
    public String toString() {
        return "{" +
                "\nid=" + id +
                "\nemail='" + email + '\'' +
                "\npassword='" + password + '\'' +
                "\nname='" + name + '\'' +
                "\nphone='" + phone + '\'' +
                "\ncreated_at=" + (created_at != null ? created_at : "null") +
                "\nrole=" + (role != null ? role : "null") +
                "\n}";
    }
}
