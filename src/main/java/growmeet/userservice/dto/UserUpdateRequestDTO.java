package growmeet.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequestDTO(
        @NotBlank(message = "이름은 필수입니다.")
        @Size(min = 2, message = "이름은 2자 이상이어야 합니다.")
        String name,

        @NotBlank(message = "전화번호는 필수입니다.")
        String phone,

        @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
        String password
) {}