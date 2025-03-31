package User.User.controller;

import User.User.dto.UserDTO;
import User.User.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 모든 유저 조회
    @GetMapping
    public String findAllUsers() {
        return userService.getUsers();
    }

    // 특정 유저 조회(id)
    @GetMapping("/id/{id}")
    public String findUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    // 특정 유저 조회 (email)
    @GetMapping("/email/{email}")
    public String findUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    // 회원 생성
    @PostMapping("/save")
    public String saveUser(@RequestBody UserDTO userDTO) {
        return userService.setUsers(userDTO);
    }

    // 회원 삭제
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    // 관리자로 변경
    @PutMapping("/updateRoleToAdmin/{email}")
    public String updateUserRoleToAdmin(@PathVariable String email) {
        return userService.updateUserRoleToAdmin(email);
    }
}
