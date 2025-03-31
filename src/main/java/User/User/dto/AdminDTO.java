package User.User.dto;

import User.User.entity.Admin;
import User.User.entity.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDTO {
    private String email;
    private String password;
    private String phone;

    public AdminDTO(Users user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
    }

    public Admin toEntity(Users user) {
        Admin admin = new Admin();
        admin.setUser(user);
        admin.setEmail(this.email);
        admin.setPassword(this.password);
        admin.setPhone(this.phone);
        return admin;
    }

    public void updateEntity(Admin admin) {
        admin.setEmail(this.email);
        admin.setPassword(this.password);
        admin.setPhone(this.phone);
    }
}
