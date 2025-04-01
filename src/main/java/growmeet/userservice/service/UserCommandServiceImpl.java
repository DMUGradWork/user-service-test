package growmeet.userservice.service;

import growmeet.userservice.domain.User;
import growmeet.userservice.dto.UserUpdateRequestDTO;
import growmeet.userservice.exception.UserNotFoundException;
import growmeet.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User update(Long id, UserUpdateRequestDTO dto) {
        User user = getUserById(id);
        String encryptedPassword = encodePasswordIfPresent(dto.password());
        updateUserContact(user, dto, encryptedPassword);
        return userRepository.save(user);
    }

    // 추후 CQRS 패턴 도입 시 QueryService로 이동하고 CommandService는 의존함
    // 신경쓰지 말 것
    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    private String encodePasswordIfPresent(String password) {
        if (password != null && !password.isBlank()) {
            return passwordEncoder.encode(password);
        }
        return null;
    }

    private void updateUserContact(User user, UserUpdateRequestDTO dto, String encryptedPassword) {
        user.updateContact(dto.name(), dto.phone(), encryptedPassword);
    }
}
