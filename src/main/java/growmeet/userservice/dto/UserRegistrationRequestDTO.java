package growmeet.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequestDTO(
        @NotBlank
        @Size(min = 2, message = "이름은 2자 이상이어야 합니다.")
        String name,

        @NotBlank
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        @NotBlank
        String phone,

        @NotBlank
        @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
        String password) {
}
