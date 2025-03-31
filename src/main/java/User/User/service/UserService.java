package User.User.service;

import User.User.dto.AdminDTO;
import User.User.dto.UserDTO;
import User.User.entity.Admin;
import User.User.entity.Users;
import User.User.enums.Role;
import User.User.exception.UserNotFoundException;
import User.User.message.ErrorMessage;
import User.User.message.SuccessMessage;
import User.User.repository.AdminRepository;
import User.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    public AdminRepository adminRepository;

    @Autowired
    private ErrorMessage errorMessage;

    @Autowired
    private SuccessMessage successMessage;

    // 모든 회원 조회
    public String getUsers() {
        List<Users> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new UserNotFoundException(errorMessage.showNoUserMessage());
        }

        StringBuilder result = new StringBuilder();
        for (Users user : users) {
            result.append(user.toString()).append("\n");
        }

        return result.toString();
    }

    // 특정 회원 조회
    public String getUser(Long id) {
        return userRepository.findById(id)
                .map(user -> user.toString())
                .orElse(errorMessage.showNoUserMessage());
    }

    // 특정 회원 조회(이메일)
    public String getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(Users::toString)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserEmailMessage()));
    }

    // 회원 저장
    public String setUsers(UserDTO userDTO) {
        Users users = new Users();
        users.setEmail(userDTO.getEmail());
        users.setPassword(userDTO.getPassword());
        users.setName(userDTO.getName());
        users.setPhone(userDTO.getPhone());
        users.setRole(userDTO.getRole());
        users.setCreated_at(userDTO.getCreatedAt());

        users.setRole(userDTO.getRole() != null ? userDTO.getRole() : Role.user);
        users.setCreated_at(LocalDateTime.now());

        Users savedUser = userRepository.save(users);

        if (savedUser.getRole() == Role.admin) {
            Admin admin = new Admin();
            admin.setUser(savedUser);
            admin.setEmail(savedUser.getEmail());
            admin.setPassword(savedUser.getPassword());
            admin.setPhone(savedUser.getPhone());

            adminRepository.save(admin);
        }

        return savedUser.toString();
    }

    // 특정 회원 삭제
    public String deleteUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.deleteById(id);
                    return successMessage.showDelSuccessUser();
                })
                .orElse(errorMessage.showNoUserMessage());
    }

    // 관리자로 변경
    public String updateUserRoleToAdmin(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserEmailMessage()));

        user.setRole(Role.admin);
        userRepository.save(user);

        AdminDTO adminDTO = new AdminDTO(user);
        Admin admin = adminRepository.findByEmail(email)
                .orElseGet(() -> adminDTO.toEntity(user));

        adminDTO.updateEntity(admin);
        adminRepository.save(admin);

        return successMessage.showChangeSuccessRole();
    }
}
