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
public class AuthenticationRequest {

    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password should have at least 8 characters")
    @ValidPassword(message = "Invalid Password")
    private String password;
}
