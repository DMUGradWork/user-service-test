package growmeet.userservice.repository;

import growmeet.userservice.domain.User;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsUserByName(@Size(min = 2, message = "Name은 2글자 이상 입력해주세요.") String name);
    boolean existsByPhone(String phoneNumber);
}
