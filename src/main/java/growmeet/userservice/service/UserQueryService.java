package growmeet.userservice.service;

import growmeet.userservice.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> findAll();
    Optional<User> findById(Long id);
}
