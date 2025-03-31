package User.User.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    private String email;
    private String password;
    private String phone;


}
