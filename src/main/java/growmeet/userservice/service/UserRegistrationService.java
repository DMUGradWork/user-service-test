package growmeet.userservice.service;

import growmeet.userservice.domain.User;
import growmeet.userservice.dto.UserRegistrationRequestDTO;

@FunctionalInterface
public interface UserRegistrationService {
    User register(UserRegistrationRequestDTO dto);
}
