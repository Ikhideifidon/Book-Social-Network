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

    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is not valid")
    // todo This needs work
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should have at least 8 characters")
    @ValidPassword(message = "Invalid Password")
    private String password;
}
