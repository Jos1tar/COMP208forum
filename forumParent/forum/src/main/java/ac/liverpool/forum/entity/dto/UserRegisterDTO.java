package ac.liverpool.forum.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotBlank(message = "The username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 20, message = "The password length must be between 6-20 digits")
    private String password;

    @NotBlank(message = "Confirm password cannot be empty")
    private String confirmPassword;


    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email format incorrect")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "Email format incorrect")
    private String email;

    @NotBlank(message = "The verification code cannot be empty")
    private String verificationCode;
} 