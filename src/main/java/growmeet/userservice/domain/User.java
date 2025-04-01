package growmeet.userservice.domain;

import static growmeet.userservice.domain.Role.USER;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties(value = {"password"})
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, message = "Name은 2글자 이상 입력해주세요.")
    private String name;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @JsonProperty("email_address")
    private String email;
    private String phone;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다.")
    @Column(updatable = false, nullable = false)
    private LocalDateTime joinDate;

    // 수정 충돌 방지를 위한 필드 추가
    @Version
    private Long version;

    private User(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = Role.USER;
        this.joinDate = LocalDateTime.now();
    }

    public static User create(String name, String email, String phone, String password) {
        return new User(name, email, phone, password);
    }

    public void updateContact(String name, String phone, String encryptedPassword) {
        this.name = name;
        this.phone = phone;
        if (encryptedPassword != null && !encryptedPassword.isBlank()) {
            this.password = encryptedPassword;
        }
    }

    public void changePassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

    public boolean isSameEmail(String email) {
        return this.email != null && this.email.equalsIgnoreCase(email);
    }
}
