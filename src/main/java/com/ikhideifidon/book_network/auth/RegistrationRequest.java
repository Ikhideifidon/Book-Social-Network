package com.ikhideifidon.book_network.auth;

import com.ikhideifidon.book_network.utils.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotEmpty(message = "First Name is mandatory")
    @NotBlank(message = "First Name is mandatory")
    private String firstname;

    @NotEmpty(message = "Last Name is mandatory")
    @NotBlank(message = "Last Name is mandatory")
    private String lastname;

    @NotEmpty(message = "Invalid Email")
    @NotBlank(message = "Invalid Email")
    @Email(message = "Invalid Email")
    // todo This needs work
    private String email;

    @NotEmpty(message = "Password should be at least 8 characters long and include one uppercase letter, one lowercase letter, and one number.")
    @NotBlank(message = "Password should be at least 8 characters long and include one uppercase letter, one lowercase letter, and one number.")
    @Size(min = 8, message = "Password should be at least 8 characters long and include one uppercase letter, one lowercase letter, and one number.")
    @ValidPassword(message = "Password should be at least 8 characters long and include one uppercase letter, one lowercase letter, and one number.")
    private String password;
}
