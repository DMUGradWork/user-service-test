package growmeet.userservice.service;

import growmeet.userservice.domain.User;
import growmeet.userservice.dto.UserUpdateRequestDTO;

public interface UserCommandService {
    void deleteById(Long id);
    User update(Long id, UserUpdateRequestDTO dto);
}
