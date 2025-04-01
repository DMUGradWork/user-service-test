package growmeet.userservice.service;

import growmeet.userservice.domain.User;
import growmeet.userservice.dto.UserRegistrationRequestDTO;
import growmeet.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(UserRegistrationRequestDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = User.create(
                dto.name(),
                dto.email(),
                dto.phone(),
                passwordEncoder.encode(dto.password())
        );

        return userRepository.save(user);
    }
}
